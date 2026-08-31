package com.br.fiap.oficina.model.dto.credencial;

import lombok.Builder;

@Builder
public record CredencialResponse(Long id, String login) { }
