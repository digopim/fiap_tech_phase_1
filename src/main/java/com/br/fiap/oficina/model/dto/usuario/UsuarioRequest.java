package com.br.fiap.oficina.model.dto.usuario;

import com.br.fiap.oficina.model.enums.Perfil;
import lombok.Builder;

@Builder
public record UsuarioRequest(Long id, String nome, String sobrenome, String email, String cpfCNPJ, String telefone, String perfil) {

    public static UsuarioRequest from(UsuarioRequest usuario) {
        return UsuarioRequest.builder()
                .id(usuario.id())
                .nome(usuario.nome())
                .sobrenome(usuario.sobrenome())
                .email(usuario.email())
                .cpfCNPJ(usuario.cpfCNPJ())
                .telefone(usuario.telefone())
                .perfil(Perfil.CLIENTE.name())
                .build();
    }
}
