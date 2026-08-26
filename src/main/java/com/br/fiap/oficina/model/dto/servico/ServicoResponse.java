package com.br.fiap.oficina.model.dto.servico;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ServicoResponse(Long id, String nome, String descricao, BigDecimal custo, BigDecimal valor, Integer duracao) {
}
