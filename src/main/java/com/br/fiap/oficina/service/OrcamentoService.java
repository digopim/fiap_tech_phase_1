package com.br.fiap.oficina.service;

import com.br.fiap.oficina.model.dto.orcamento.OrcamentoRequest;
import com.br.fiap.oficina.model.entity.*;
import com.br.fiap.oficina.model.repository.OrcamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrcamentoService {

    private static final ZoneId ZONE_ID = ZoneId.of("America/Sao_Paulo");
    private OrcamentoRepository repository;
    private ServicoService servicoService;
    private MaterialService materialService;
    private final EstoqueService estoqueService;

    // Criar orçamento de serviço
    public Orcamento criarOrcamento(OrcamentoRequest request, Ordem ordem) {
        Orcamento orcamento = ordem.getOrcamentos().stream().filter(o -> o.getDataConclusao() == null && o.getDataAprovacao() == null).findFirst().orElse(Orcamento.builder()
                .ordem(ordem)
                .build());

        request.servicos().forEach((servicoId, quantidade) -> {
            Servico servico = servicoService.buscarServicoPorId(servicoId);
            Optional<ItemServico> item = orcamento.getServicos().stream().filter(i -> i.getServico().getId().equals(servicoId)).findFirst();

            if(item.isPresent()) {
                item.get().setQuantidade(quantidade);
            } else {
                orcamento.getServicos().add(ItemServico.builder()
                        .orcamento(orcamento)
                        .servico(servico)
                        .quantidade(quantidade)
                        .precoUnitario(servico.getValor())
                        .build());
            }
        });

        request.materiais().forEach((materialId, quantidade) -> {
            Material material = materialService.buscarMaterialPorId(materialId);
            Optional<ItemMaterial> item = orcamento.getMateriais().stream().filter(i -> i.getMaterial().getId().equals(materialId)).findFirst();
            if(item.isPresent()) {
                item.get().setQuantidade(quantidade);
            } else {
                orcamento.getMateriais().add(ItemMaterial.builder()
                        .orcamento(orcamento)
                        .material(material)
                        .quantidade(quantidade)
                        .precoUnitario(material.getValor())
                        .build());
            }
        });

        calcularValorTotal(orcamento);
        return repository.save(orcamento);
    }

    @Transactional
    public void aprovarOrcamento(Long orcamentoId, boolean aprovado) {
        repository.findById(orcamentoId).ifPresent(orcamento -> {
            if (aprovado) {
                orcamento.setDataAprovacao(LocalDateTime.now(ZONE_ID));
                orcamento.getMateriais().forEach(m -> {
                    m.setUtilizado(true);
                    materialService.debitarMaterial(m.getMaterial().getId(), m.getQuantidade());
                });

            } else {
                orcamento.setDataConclusao(LocalDateTime.now(ZONE_ID));
            }
            repository.save(orcamento);
        });
    }

    public void concluirOrcamento(Long orcamentoId) {
        repository.findById(orcamentoId).ifPresent(orcamento -> {
            validarOrcamentoConcluido(orcamento);
            orcamento.setDataConclusao(LocalDateTime.now(ZONE_ID));
            repository.save(orcamento);
        });
    }

    private void validarOrcamentoConcluido(Orcamento orcamento) {
        orcamento.getServicos().stream().filter(itemServico -> !itemServico.isExecutado()).forEach(itemServico -> {
            if (itemServico.getDataExecucao() == null && itemServico.getExecutor() == null) {
                throw new IllegalArgumentException("Todos os serviços devem estar executados para concluir o orçamento.");
            }
        });
    }

    private void calcularValorTotal(Orcamento orcamento) {
        BigDecimal valorServicos = orcamento.getServicos().stream()
                .map(i -> i.getPrecoUnitario().multiply(BigDecimal.valueOf(i.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal valorMateriais = orcamento.getMateriais().stream()
                .map(i -> i.getPrecoUnitario().multiply(BigDecimal.valueOf(i.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        orcamento.setValor(valorServicos.add(valorMateriais));
    }

}
