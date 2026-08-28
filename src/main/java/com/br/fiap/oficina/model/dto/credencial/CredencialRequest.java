package com.br.fiap.oficina.model.dto.credencial;

import jakarta.validation.constraints.NotBlank;

public record CredencialRequest(
//        @CpfouCnpj
        @NotBlank(message = "Login não pode ser nulo ou vazio")
        String login,
        @NotBlank(message = "Senha não pode ser nula ou vazia")
        String senha
) {}
