package com.br.fiap.oficina.model.dto.usuario;

import lombok.Builder;

@Builder
public record UsuarioRequest(Long id, String nome, String sobrenome, String email, String cpfCNPJ, String telefone, String perfil) {
}
