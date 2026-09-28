package com.br.fiap.oficina.application.dto.ordem;

import com.br.fiap.oficina.application.dto.usuario.UsuarioRequest;
import com.br.fiap.oficina.application.dto.veiculo.VeiculoRequest;
import lombok.Builder;

@Builder
public record OrdemRequest(VeiculoRequest veiculo, Long responsavel, UsuarioRequest cliente, Formulario formulario) {
}
