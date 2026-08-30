package com.br.fiap.oficina.model.dto.servico;

import lombok.Builder;

@Builder
public record ItemServicoRequest(Long id, Long executor) {
}
