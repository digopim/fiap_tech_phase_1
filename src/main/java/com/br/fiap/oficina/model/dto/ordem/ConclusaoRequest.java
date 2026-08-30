package com.br.fiap.oficina.model.dto.ordem;

import lombok.Builder;

@Builder
public record ConclusaoRequest(Long ordem, Long orcamento) {
}
