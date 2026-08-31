package com.br.fiap.oficina.model.dto.servico;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ServicoRequest(Long id, String nome, String descricao, BigDecimal custo, BigDecimal valor, Integer duracao) {
}
