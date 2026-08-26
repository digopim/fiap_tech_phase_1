package com.br.fiap.oficina.model.dto.orcamento;

import java.util.Map;

public record OrcamentoRequest(Long orcamentoId, Map<Long, Integer> servicos, Map<Long, Integer> materiais, Boolean concluirDiagnostico) {
}
