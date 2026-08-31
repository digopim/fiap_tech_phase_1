package com.br.fiap.oficina.model.dto.material;

import java.math.BigDecimal;

public record MaterialRequest(Long id, String nome, String descricao, BigDecimal valor, BigDecimal custo, String tipo) {
}
