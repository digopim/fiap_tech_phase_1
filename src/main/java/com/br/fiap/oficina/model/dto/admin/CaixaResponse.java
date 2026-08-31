package com.br.fiap.oficina.model.dto.admin;

import com.br.fiap.oficina.model.entity.Caixa;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CaixaResponse(String descricao, BigDecimal valor) {

    public static CaixaResponse from (Caixa caixa) {
        return CaixaResponse.builder()
                .descricao(caixa.getDescricao())
                .valor(caixa.getValor())
                .build();
    }
}
