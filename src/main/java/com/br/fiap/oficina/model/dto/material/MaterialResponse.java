package com.br.fiap.oficina.model.dto.material;

import com.br.fiap.oficina.model.entity.Material;

public record MaterialResponse(Long id, String nome, String descricao, Double valor, Double custo, String tipo) {


    public static MaterialResponse fromEntity(Material material) {
        return new MaterialResponse(
                material.getId(),
                material.getNome(),
                material.getDescricao(),
                material.getValor(),
                material.getCusto(),
                material.getTipo().name()
        );
    }
}
