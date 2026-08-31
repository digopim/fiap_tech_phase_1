package com.br.fiap.oficina.model.dto.material;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record MaterialRequest(Long id, String nome, String descricao, BigDecimal valor, BigDecimal custo, String tipo) {
}
