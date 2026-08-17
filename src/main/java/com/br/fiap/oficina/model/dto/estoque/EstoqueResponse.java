package com.br.fiap.oficina.model.dto.estoque;

import com.br.fiap.oficina.model.entity.Material;
import com.br.fiap.oficina.model.enums.Insumo;
import lombok.Builder;

@Builder
public record EstoqueResponse(
        String nome,
        String descricao,
        Double preco,
        Integer quantidade,
        Insumo tipo) {

    public static EstoqueResponse from(Material material) {
        return EstoqueResponse.builder()
                .nome(material.getNome())
                .descricao(material.getDescricao())
                .preco(material.getPreco())
                .quantidade(material.getQuantidade())
                .tipo(material.getTipo())
                .build();
    }

}