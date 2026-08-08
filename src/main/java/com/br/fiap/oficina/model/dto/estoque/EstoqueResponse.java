package com.br.fiap.oficina.model.dto.estoque;

import com.br.fiap.oficina.model.entity.Estoque;
import com.br.fiap.oficina.model.enums.Insumo;
import lombok.Builder;

@Builder
public record EstoqueResponse(
        String nome,
        String descricao,
        Double preco,
        Integer quantidade,
        Insumo tipo) {

    public static EstoqueResponse from(Estoque estoque) {
        return EstoqueResponse.builder()
                .nome(estoque.getNome())
                .descricao(estoque.getDescricao())
                .preco(estoque.getPreco())
                .quantidade(estoque.getQuantidade())
                .tipo(estoque.getTipo())
                .build();
    }

}