package com.br.fiap.oficina.model.dto.usuario;

public record UsuarioRequest(Long id, String nome, String sobrenome, String email, String cpfCNPJ, String telefone, String perfil) {
}
