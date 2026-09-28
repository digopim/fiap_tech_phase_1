package com.br.fiap.oficina.application.dto.estoque;

import com.br.fiap.oficina.domain.entity.Estoque;
import com.br.fiap.oficina.domain.enums.Insumo;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record EstoqueResponse(
        String nome,
        String descricao,
        BigDecimal preco,
        Integer quantidade,
        Insumo tipo) {

    public static EstoqueResponse from(Estoque estoque) {
        return EstoqueResponse.builder()
                .nome(estoque.getMaterial().getNome())
                .descricao(estoque.getMaterial().getDescricao())
                .preco(estoque.getMaterial().getValor())
                .quantidade(estoque.getQuantidade())
                .tipo(estoque.getMaterial().getTipo())
                .build();
    }

}