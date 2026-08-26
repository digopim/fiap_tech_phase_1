package com.br.fiap.oficina.model.dto.ordem;

import com.br.fiap.oficina.model.enums.CheckList;

import java.util.Map;
import java.util.Objects;

public record Formulario(Map<CheckList, Boolean> respostas) {
    public boolean inspecao() {
        return
                Boolean.TRUE.equals(respostas.computeIfPresent(CheckList.CLI, (key, value) -> value)) &&
                Boolean.TRUE.equals(respostas.computeIfPresent(CheckList.VEI, (key, value) -> value)) &&
                respostas.values().stream().allMatch(Objects::nonNull)
                ;
    }
}
