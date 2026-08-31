package com.br.fiap.oficina.model.dto.estoque;

import lombok.Builder;

@Builder
public record EstoqueRequest(
        Long materialId,
        Integer quantidade) {
}