package com.br.fiap.oficina.model.dto.admin;

import com.br.fiap.oficina.model.enums.Origem;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Builder
public record Panorama(Map<Origem, List<CaixaResponse>> custos, Map<Origem, List<CaixaResponse>> receitas, PanoramaOrcamento orcamentos, BigDecimal totalCustos, BigDecimal totalReceitas, BigDecimal saldo) {
    public record PanoramaOrcamento(Integer aprovados, Integer rejeitados, Integer abertos){}
}
