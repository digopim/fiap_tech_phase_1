package com.br.fiap.oficina.model.dto.estoque;

import com.br.fiap.oficina.model.entity.Estoque;
import com.br.fiap.oficina.model.enums.Insumo;
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