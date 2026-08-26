package com.br.fiap.oficina.model.dto.ordem;

import com.br.fiap.oficina.model.entity.Usuario;

public record OrdemRequest(String placa, Usuario responsavel, String cliente, Formulario formulario) {
}
