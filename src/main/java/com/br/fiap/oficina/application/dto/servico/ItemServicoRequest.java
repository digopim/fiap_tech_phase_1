package com.br.fiap.oficina.application.dto.servico;

import lombok.Builder;

@Builder
public record ItemServicoRequest(Long id, Long executor) {
}
