package com.br.fiap.oficina.model.dto.estoque;

import com.br.fiap.oficina.model.entity.Estoque;
import com.br.fiap.oficina.model.enums.Insumo;
import lombok.Builder;

@Builder
public record EstoqueRequest(
        Long id,
        String nome,
        String descricao,
        Double custo,
        Double preco,
        Integer quantidade,
        Integer minimo,
        Insumo tipo) {

    public Estoque toEntity() {
        return Estoque.builder()
                .id(id)
                .nome(nome)
                .descricao(descricao)
                .custo(custo)
                .preco(preco)
                .quantidade(quantidade)
                .minimo(minimo)
                .tipo(tipo)
                .build();
    }
}