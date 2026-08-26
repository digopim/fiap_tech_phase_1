package com.br.fiap.oficina.model.dto.usuario;

public record UsuarioResponse(Long id, String nome, String sobrenome, String email, String cpfCNPJ, String telefone, String perfil) {
}
