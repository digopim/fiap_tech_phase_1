package com.br.fiap.oficina.model.dto.credencial;

import com.br.fiap.oficina.model.enums.Perfil;

public record CredencialResponse(Long id, String login, Perfil perfil) {}
