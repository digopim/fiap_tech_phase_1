package com.br.fiap.oficina.model.dto.ordem;

import com.br.fiap.oficina.model.enums.CheckList;
import lombok.Builder;

import java.util.Map;
import java.util.Objects;

@Builder
public record Formulario(Map<CheckList, Boolean> respostas) {
    public boolean inspecao() {
        return !respostas.isEmpty() && respostas.values().stream().allMatch(Objects::nonNull);
    }
}
