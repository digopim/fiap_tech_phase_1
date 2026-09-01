package com.br.fiap.oficina.service;

import com.br.fiap.oficina.model.dto.credencial.CredencialRequest;
import com.br.fiap.oficina.model.dto.orcamento.OrcamentoRequest;
import com.br.fiap.oficina.model.dto.ordem.OrdemRequest;
import com.br.fiap.oficina.model.dto.ordem.OrdemResponse;
import com.br.fiap.oficina.model.dto.usuario.UsuarioRequest;
import com.br.fiap.oficina.model.entity.Orcamento;
import com.br.fiap.oficina.model.entity.Ordem;
import com.br.fiap.oficina.model.entity.Usuario;
import com.br.fiap.oficina.model.entity.Veiculo;
import com.br.fiap.oficina.model.enums.Fluxo;
import com.br.fiap.oficina.model.enums.Origem;
import com.br.fiap.oficina.model.enums.Status;
import com.br.fiap.oficina.model.repository.OrdemRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class OrdemService {

    private OrdemRepository repository;
    private OrcamentoService orcamentoService;
    private VeiculoService veiculoService;
    private UsuarioService usuarioService;
    private CredencialService credencialService;
    private EstoqueService estoqueService;

    private static final String MSG_NAO_ENCONTRADO = "Ordem não encontrada";
    private static final ZoneId ZONE_ID = ZoneId.of("America/Sao_Paulo");

    @Transactional
    public OrdemResponse criarOrdem(OrdemRequest request) {
        if(!request.formulario().inspecao()) {
            throw new IllegalArgumentException("CheckList Não Concluido");
        }
        Veiculo veiculo = veiculoService.buscarVeiculoPorPlaca(request.veiculo().placa()).orElse(veiculoService.salvarVeiculo(request.veiculo()));
        Usuario responsavel = usuarioService.buscarUsuarioPorId(request.responsavel());
        Usuario cliente = usuarioService.buscarUsuarioPorCpfCNPJ(request.cliente().cpfCNPJ()).orElse(null);

        if(cliente == null) {
            Usuario novo = usuarioService.salvarUsuario(UsuarioRequest.from(request.cliente()));
            credencialService.cadastrar(CredencialRequest.builder().login(request.cliente().cpfCNPJ()).senha("primeiroacesso").build(), novo);
            cliente = novo;
        }

        Ordem ordem = repository.save(Ordem.builder()
                .responsavel(responsavel)
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
        Ordem os = repository.findById(ordemId).orElseThrow(() -> new NoSuchElementException(MSG_NAO_ENCONTRADO));

        if(!os.getStatus().equals(Status.RECEBIDA) && !os.getStatus().equals(Status.EM_DIAGNOSTICO) ) {
            throw new IllegalArgumentException("Não é possível incluir orçamento na ordem.");
        }

        if(orcamentoRequest != null) {
            Orcamento novoOrcamento = orcamentoService.criarOrcamento(orcamentoRequest, os);
            if(os.getOrcamentos().stream().noneMatch(orc -> orc.getId().equals(novoOrcamento.getId()))){
                os.getOrcamentos().add(novoOrcamento);
            }
            os.setStatus(Status.EM_DIAGNOSTICO);
            if(Boolean.TRUE.equals(orcamentoRequest.concluirDiagnostico())){
                os.setStatus(Status.AGUARDANDO_APROVACAO);
                // Chama metodo de Notificação do cliente
            }
        }

        return OrdemResponse.from(repository.save(os));
    }

    @Transactional
    public OrdemResponse aprovarOrcamento(String cpfCNPJ, String placa, boolean aprovado) {
        Ordem os = repository.findFirstByCliente_CpfCNPJAndVeiculo_PlacaAndDataConclusaoNullOrderByDataCriacaoAsc(cpfCNPJ, placa).orElseThrow();
        var orcamento = os.getOrcamentos().stream().filter(o -> o.getDataAprovacao() == null && o.getDataConclusao() == null).findFirst();
        orcamento.ifPresent(value -> orcamentoService.aprovarOrcamento(value.getId(), aprovado));

        if(Status.AGUARDANDO_APROVACAO.equals(os.getStatus()) && os.getOrcamentos().stream().allMatch(o -> o.getDataAprovacao() != null || o.getDataConclusao() != null)){
            os.setStatus(Status.EM_EXECUCAO);
            os.setDataInicio(LocalDateTime.now(ZONE_ID));
            os = repository.save(os);
        }
        return OrdemResponse.from(os);
    }

    public List<OrdemResponse> obterOrdens(String placa, String cpfCNPJ) {
        List<Ordem> ordens = repository.findByCliente_CpfCNPJIgnoreCaseAndVeiculo_PlacaIgnoreCaseOrderByDataCriacaoDesc(cpfCNPJ, placa);
        return ordens.stream().map(OrdemResponse::from).toList();
    }

    public List<OrdemResponse> obterOrdensPorCpfCNPJ(String cpfCNPJ) {
        List<Ordem> ordens = repository.findByCliente_CpfCNPJOrderByDataCriacaoDesc(cpfCNPJ);
        return ordens.stream().map(OrdemResponse::from).toList();
    }

    public List<OrdemResponse> obterOrdensPorPlaca(String placa) {
        List<Ordem> ordens = repository.findByVeiculo_PlacaIgnoreCaseOrderByDataCriacaoAsc(placa);
        return ordens.stream().map(OrdemResponse::from).toList();
    }

    // Consultar OS por status
    public List<OrdemResponse> obterOrdensPorStatus(Status status) {
        List<Ordem> ordens = repository.findByStatus(status);
        return ordens.stream().map(OrdemResponse::from).toList();
    }

    // Concluir OS
    public OrdemResponse concluirOrcamento(Long ordemId,  Long orcamentoId) {
        Ordem os = repository.findFirstById(ordemId).orElseThrow(() -> new NoSuchElementException(MSG_NAO_ENCONTRADO));

        if(orcamentoId != null) {
            var orcamento = os.getOrcamentos().stream().filter(o -> o.getId().equals(orcamentoId)).findFirst();
            orcamento.ifPresent(value -> orcamentoService.concluirOrcamento(value.getId()));
        }

        if(Status.EM_EXECUCAO.equals(os.getStatus()) && os.getOrcamentos().stream().allMatch(o -> o.getDataConclusao() != null)){
            repository.save(finalizarOrcamento(os));
        }
        return OrdemResponse.from(os);
    }

    private Ordem finalizarOrcamento(Ordem os) {
        if(os.getOrcamentos().stream().allMatch(o -> o.getDataConclusao() != null)){
            os.setStatus(Status.FINALIZADA);
            os.setValorTotal(calcularValorTotal(os));
            os.setDataConclusao(LocalDateTime.now(ZONE_ID));
        }
        return os;
    }

    // Pagar OS
    @Transactional
    public OrdemResponse registrarPagamento(Long ordemId) {
        Ordem os = repository.findById(ordemId).orElseThrow(() -> new NoSuchElementException(MSG_NAO_ENCONTRADO));
        if(!Status.FINALIZADA.equals(os.getStatus()) && os.getDataPagamento() == null){
            throw new IllegalArgumentException("Apenas ordens finalizadas em aberto podem ser pagas");
        }
        os.setStatus(Status.LIBERADA);
        os.setDataPagamento(LocalDateTime.now(ZONE_ID));
        estoqueService.registrar("Pagamento Ordem" + os.getId(), os.getValorTotal(), Fluxo.ENTRADA, Origem.SERVICO);
        return OrdemResponse.from(repository.save(os));
    }

    // Retirar Veiculo
    public List<OrdemResponse> efetuarRetirada(String placa) {
        List<Ordem> osAtivas = repository.findByVeiculo_PlacaIgnoreCaseOrderByDataCriacaoAsc(placa).stream().filter(o -> !o.getStatus().equals(Status.CANCELADA) && !o.getStatus().equals(Status.ENTREGUE)).toList();

        if(osAtivas.stream().anyMatch(o -> !o.getStatus().equals(Status.LIBERADA))){
            throw new IllegalArgumentException("Apenas veiculos liberados podem ser retirados");
        }

        osAtivas.forEach(o -> {o.setStatus(Status.ENTREGUE); repository.save(o);});

        return osAtivas.stream().map(OrdemResponse::from).toList();
    }

    // Cancelar OS
    public OrdemResponse cancelarOrdem(Long ordemId) {
        Ordem os = repository.findById(ordemId).orElseThrow(() -> new NoSuchElementException(MSG_NAO_ENCONTRADO));
        os.setStatus(Status.CANCELADA);
        os.setDataConclusao(LocalDateTime.now(ZONE_ID));
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

    // criar metodo que usa o scheduler para verificar se tem ordens finalizadas e concluir elas
    @Scheduled(cron = "0 0 * * * *")
    public void verificarOrdensFinalizadas() {
        List<Ordem> ordensFinalizadas = repository.findByStatus(Status.EM_EXECUCAO);
        for (Ordem ordem : ordensFinalizadas) {
            if (ordem.getOrcamentos().stream().allMatch(o -> o.getDataConclusao() != null)) {
                repository.save(finalizarOrcamento(ordem));
            }
        }
    }

}
