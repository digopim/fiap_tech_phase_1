package com.br.fiap.oficina.model.dto.ordem;

import com.br.fiap.oficina.model.dto.usuario.UsuarioRequest;
import com.br.fiap.oficina.model.dto.veiculo.VeiculoRequest;
import lombok.Builder;

@Builder
public record OrdemRequest(VeiculoRequest veiculo, Long responsavel, UsuarioRequest cliente, Formulario formulario) {
}
