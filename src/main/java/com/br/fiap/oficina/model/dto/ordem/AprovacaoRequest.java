package com.br.fiap.oficina.model.dto.ordem;

import lombok.Builder;

@Builder
public record AprovacaoRequest(boolean aprovado) {
}
