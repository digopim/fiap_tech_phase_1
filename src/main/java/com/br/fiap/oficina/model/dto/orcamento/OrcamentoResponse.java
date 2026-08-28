package com.br.fiap.oficina.model.dto.orcamento;

import com.br.fiap.oficina.model.entity.Orcamento;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Builder
public record OrcamentoResponse(
        Long id,
        LocalDateTime dataCriacao,
        LocalDateTime dataAprovacao,
        LocalDateTime dataConclusao,
        BigDecimal valor,
        String estimativa,
        Map<String, BigDecimal> servicos,
        Map<String, BigDecimal> materiais,
        String status
) {

    public static OrcamentoResponse from(Orcamento orcamento) {
        Map<String, BigDecimal> servicos = new HashMap<>();
        Map<String, BigDecimal> materiais = new HashMap<>();
        orcamento.getServicos().forEach(servico -> servicos.put(servico.getServico().getNome(), servico.getServico().getValor()));
        orcamento.getMateriais().forEach(material -> materiais.put(material.getMaterial().getNome(), material.getMaterial().getValor()));
        Integer estimativa = orcamento.getServicos().stream().mapToInt(s -> s.getServico().getDuracao()).sum();

        return OrcamentoResponse.builder()
                .id(orcamento.getId())
                .dataCriacao(orcamento.getDataCriacao())
                .dataAprovacao(orcamento.getDataAprovacao())
                .dataConclusao(orcamento.getDataConclusao())
                .valor(orcamento.getValor())
                .servicos(servicos)
                .materiais(materiais)
                .estimativa("Estimativa de duração do serviço: " + estimativa + " horas")
                .status(obterStatus(orcamento))
                .build();
    }

    private static String obterStatus(Orcamento orcamento) {
        if (orcamento.getDataAprovacao() != null && orcamento.getDataConclusao() != null) {
            return "Concluído";
        } else if(orcamento.getDataAprovacao() == null && orcamento.getDataConclusao() == null) {
            return "Aguardando Aprovação";
        } else if(orcamento.getDataAprovacao() != null) {
            return "Aprovado";
        } else {
            return "Reprovado";
        }
    }
}
