package com.br.fiap.oficina.application.dto.material;

import com.br.fiap.oficina.domain.entity.Material;

import java.math.BigDecimal;

public record MaterialResponse(Long id, String nome, String descricao, BigDecimal valor, BigDecimal custo, String tipo) {


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
