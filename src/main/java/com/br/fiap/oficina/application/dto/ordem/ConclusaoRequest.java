package com.br.fiap.oficina.application.dto.ordem;

import lombok.Builder;

@Builder
public record ConclusaoRequest(Long ordem, Long orcamento) {
}
