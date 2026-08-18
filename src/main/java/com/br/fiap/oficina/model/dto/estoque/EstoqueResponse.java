package com.br.fiap.oficina.model.dto.estoque;

import com.br.fiap.oficina.model.enums.Insumo;
import lombok.Builder;

@Builder
public record EstoqueResponse(
        String nome,
        String descricao,
        Double preco,
        Integer quantidade,
        Insumo tipo) {

}