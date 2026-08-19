package com.br.fiap.oficina.service;

import com.br.fiap.oficina.model.dto.orcamento.OrcamentoRequest;
import com.br.fiap.oficina.model.entity.*;
import com.br.fiap.oficina.model.repository.OrcamentoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class OrcamentoService {

    OrcamentoRepository repository;
    VeiculoService veiculoService;
    ServicoService servicoService;
    MaterialService materialService;

    // Criar orçamento de serviço
    public void criarOrcamento(OrcamentoRequest request) {
        Veiculo veiculo = veiculoService.buscarVeiculoPorId(request.veiculoId());
        Orcamento orcamento = Orcamento.builder().veiculo(veiculo).build();

        request.servicos().forEach((servicoId, quantidade) -> {
            Servico servico = servicoService.buscarServicoPorId(servicoId);
            orcamento.getServicos().add(ItemServico.builder()
                    .orcamento(orcamento)
                    .servico(servico)
                    .quantidade(quantidade)
                    .precoUnitario(servico.getValor())
                    .build());
        });

        request.materiais().forEach((materialId, quantidade) -> {
            Material material = materialService.buscarMaterialPorId(materialId);
            orcamento.getMateriais().add(ItemMaterial.builder()
                    .orcamento(orcamento)
                    .material(material)
                    .quantidade(quantidade)
                    .precoUnitario(material.getValor())
                    .build());
        });

        calcularValorTotal(orcamento);
        repository.save(orcamento);
    }

    public void aprovarOrcamento(Long orcamentoId, boolean aprovado) {
        repository.findById(orcamentoId).ifPresent(orcamento -> {
            if (aprovado) {
                orcamento.setDataAprovacao(LocalDateTime.now());
            } else {
                orcamento.setDataConclusao(LocalDateTime.now());
            }
            repository.save(orcamento);
        });
    }

    public void concluirOrcamento(Long orcamentoId) {
        repository.findById(orcamentoId).ifPresent(orcamento -> {
            validarOrcamentoConcluido(orcamento);
            orcamento.setDataConclusao(LocalDateTime.now());
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
                .filter(ItemServico::isExecutado)
                .map(ItemServico::getPrecoUnitario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal valorMateriais = orcamento.getMateriais().stream()
                .filter(ItemMaterial::isUtilizado)
                .map(ItemMaterial::getPrecoUnitario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        orcamento.setValor(valorServicos.add(valorMateriais));
    }

}
