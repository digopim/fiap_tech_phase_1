package com.br.fiap.oficina.service;

import com.br.fiap.oficina.model.dto.orcamento.OrcamentoRequest;
import com.br.fiap.oficina.model.dto.ordem.OrdemRequest;
import com.br.fiap.oficina.model.dto.ordem.OrdemResponse;
import com.br.fiap.oficina.model.entity.Orcamento;
import com.br.fiap.oficina.model.entity.Ordem;
import com.br.fiap.oficina.model.entity.Usuario;
import com.br.fiap.oficina.model.entity.Veiculo;
import com.br.fiap.oficina.model.enums.Status;
import com.br.fiap.oficina.model.repository.OrdemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrdemService {

    private OrdemRepository repository;
    private OrcamentoService orcamentoService;
    private VeiculoService veiculoService;
    private UsuarioService usuarioService;

    public OrdemResponse criarOrdem(OrdemRequest request) {
        if(!request.formulario().inspecao()) {
            throw new IllegalArgumentException("CheckList Não Concluido");
        }
        Veiculo veiculo = veiculoService.buscarVeiculoPorPlaca(request.placa());
        Usuario cliente = usuarioService.buscarUsuarioPorCpfCNPJ(request.cliente());

        Ordem ordem = repository.save(Ordem.builder()
                .responsavel(request.responsavel())
                .cliente(cliente)
                .status(Status.RECEBIDA)
                .veiculo(veiculo)
                .valorTotal(BigDecimal.ZERO)
                .build());
        return OrdemResponse.from(ordem);
    }

    // Incluir Orcamento
    @Transactional
    public OrdemResponse incluirOrcamento(Long ordemId, OrcamentoRequest orcamentoRequest) {
        Ordem os = repository.findById(ordemId).orElseThrow(() -> new NoSuchElementException("Ordem não encontrada"));

        if(!os.getStatus().equals(Status.RECEBIDA) && !os.getStatus().equals(Status.EM_DIAGNOSTICO) ) {
            throw new IllegalArgumentException("Não é possível incluir orçamento na ordem.");
        }

        if(os.getOrcamentos() != null && os.getOrcamentos().stream().anyMatch(o -> o.getDataAprovacao() == null && o.getDataConclusao() == null)) {
            throw new IllegalArgumentException("Não é possível incluir orçamento em ordem com orçamento em aberto.");
        }

        if(os.getOrcamentos() == null) {
            os.setOrcamentos(new ArrayList<>());
            os.setStatus(Status.EM_DIAGNOSTICO);
        }

        if(orcamentoRequest != null){
            os.getOrcamentos().add(orcamentoService.criarOrcamento(orcamentoRequest));
            if(Boolean.TRUE.equals(orcamentoRequest.concluirDiagnostico())){
                os.setStatus(Status.AGUARDANDO_APROVACAO);
                // Chama metodo de Notificação do cliente
            }
        }

        return OrdemResponse.from(repository.save(os));
    }

    public OrdemResponse aprovarOrcamento(String cpfCNPJ, String placa, boolean aprovado) {
        Ordem os = repository.findFirstByCliente_CpfCNPJAndVeiculo_PlacaAndDataConclusaoNullOrderByDataCriacaoAsc(cpfCNPJ, placa).orElseThrow();
        var orcamento = os.getOrcamentos().stream().filter(o -> o.getDataAprovacao() == null && o.getDataConclusao() == null).findFirst();
        orcamento.ifPresent(value -> orcamentoService.aprovarOrcamento(value.getId(), aprovado));

        if(Status.EM_DIAGNOSTICO.equals(os.getStatus()) && os.getOrcamentos().stream().allMatch(o -> o.getDataAprovacao() != null || o.getDataConclusao() != null)){
            os.setStatus(Status.EM_EXECUCAO);
            os.setDataInicio(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
            os = repository.save(os);
        }
        return OrdemResponse.from(os);
    }

    public List<OrdemResponse> obterOrdens(String placa, String cpfCNPJ) {
        List<Ordem> ordens = repository.findByCliente_CpfCNPJIgnoreCaseAndVeiculo_PlacaIgnoreCaseAndDataConclusaoNullOrderByDataCriacaoAsc(cpfCNPJ, placa);
        return ordens.stream().map(OrdemResponse::from).toList();
    }

    // Consultar OS por status
    public List<OrdemResponse> obterOrdensPorStatus(Status status) {
        List<Ordem> ordens = repository.findByStatus(status);
        return ordens.stream().map(OrdemResponse::from).toList();
    }

    // Concluir OS
    public OrdemResponse concluirOrcamento(Long ordemId, Long orcamentoId) {
        Ordem os = repository.findById(ordemId).orElseThrow(() -> new NoSuchElementException("Ordem não encontrada"));
        var orcamento = os.getOrcamentos().stream().filter(o -> o.getId().equals(orcamentoId)).findFirst();
        orcamento.ifPresent(value -> orcamentoService.concluirOrcamento(value.getId()));
        if(Status.EM_EXECUCAO.equals(os.getStatus()) && os.getOrcamentos().stream().allMatch(o -> o.getDataConclusao() != null)){
            os.setStatus(Status.FINALIZADA);
            os.setValorTotal(calcularValorTotal(os));
            os.setDataConclusao(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
            repository.save(os);
        }
        return OrdemResponse.from(os);
    }

    // Pagar OS
    public OrdemResponse registrarPagamento(Long ordemId) {
        Ordem os = repository.findById(ordemId).orElseThrow(() -> new NoSuchElementException("Ordem não encontrada"));
        if(!Status.FINALIZADA.equals(os.getStatus()) && os.getDataPagamento() == null){
            throw new IllegalArgumentException("Apenas ordens finalizadas em aberto podem ser pagas");
        }
        os.setStatus(Status.LIBERADA);
        os.setDataPagamento(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        return OrdemResponse.from(repository.save(os));
    }

    // Retirar Veiculo
    public List<OrdemResponse> autorizarRetirada(String placa) {
        List<Ordem> osAtivas = repository.findByVeiculo_PlacaIgnoreCaseOrderByDataCriacaoAsc(placa).stream().filter(o -> !o.getStatus().equals(Status.CANCELADA) && !o.getStatus().equals(Status.ENTREGUE)).toList();

        if(osAtivas.stream().anyMatch(o -> !o.getStatus().equals(Status.LIBERADA))){
            throw new IllegalArgumentException("Apenas veiculos liberados podem ser retirados");
        }

        osAtivas.forEach(o -> {o.setStatus(Status.ENTREGUE); repository.save(o);});

        return osAtivas.stream().map(OrdemResponse::from).toList();
    }

    // Cancelar OS
    public OrdemResponse cancelarOrdem(Long ordemId) {
        Ordem os = repository.findById(ordemId).orElseThrow(() -> new NoSuchElementException("Ordem não encontrada"));
        os.setStatus(Status.CANCELADA);
        os.setDataConclusao(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        return OrdemResponse.from(repository.save(os));
    }

    // Calcula Valor Total
    private BigDecimal calcularValorTotal(Ordem ordem) {
        BigDecimal valorTotal = BigDecimal.ZERO;
        var orcamentosFiltrados = ordem.getOrcamentos().stream().filter(o -> o.getDataAprovacao() != null && o.getDataConclusao() != null).toList();
        for (Orcamento orcamento : orcamentosFiltrados) {
            valorTotal = valorTotal.add(orcamento.getValor());
        }
        return valorTotal;
    }

    // Monitoramento de tempo medio de execução dos serviços
}
