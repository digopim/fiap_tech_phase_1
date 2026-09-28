package com.br.fiap.oficina.application.dto.estoque;

import lombok.Builder;

@Builder
public record EstoqueRequest(
        Long materialId,
        Integer quantidade) {
}