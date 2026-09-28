package com.br.fiap.oficina.application.dto.credencial;

import lombok.Builder;

@Builder
public record CredencialResponse(Long id, String login) { }
