package com.br.fiap.oficina.service;

import com.br.fiap.oficina.model.entity.Caixa;
import com.br.fiap.oficina.model.enums.Fluxo;
import com.br.fiap.oficina.model.enums.Origem;
import com.br.fiap.oficina.model.repository.CaixaRepository;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

import static java.time.LocalDateTime.now;

public class CaixaService {

    CaixaRepository repository;

    public boolean registrar(String descricao, Double valor, Fluxo fluxo, Origem origem) {
        repository.save(Caixa.builder()
                .descricao(descricao)
                .valor(valor)
                .fluxo(fluxo)
                .origem(origem)
                .data(now(ZoneId.of("America/Sao_Paulo")))
                .build());
        return true;
    }

    public Double calcularSaldoTotal() {
        return repository.findByFluxo(Fluxo.ENTRADA).stream().mapToDouble(Caixa::getValor).sum() -
                repository.findByFluxo(Fluxo.SAIDA).stream().mapToDouble(Caixa::getValor).sum();
    }

    public Double calcularSaldoPorOrigem(Origem origem) {
        return repository.findByOrigem(origem).stream().mapToDouble(Caixa::getValor).sum();
    }

    public Double calcularSaldoPorData(@NotNull LocalDateTime dataInicio, @NotNull LocalDateTime dataFim) {
        return repository.findByFluxoAndDataBetween(Fluxo.ENTRADA, dataInicio, dataFim).stream().mapToDouble(Caixa::getValor).sum() -
                repository.findByFluxoAndDataBetween(Fluxo.SAIDA, dataInicio, dataFim).stream().mapToDouble(Caixa::getValor).sum();
    }

    public Map<Origem, Double> calcularSaldoPorOrigem(@NotNull LocalDateTime dataInicio, @NotNull LocalDateTime dataFim) {
        Map<Origem, Double> saldoPorOrigem = new HashMap<>();
        for (Origem origem : Origem.values()) {
            double saldo = repository.findByOrigemAndDataBetween(origem, dataInicio, dataFim).stream().mapToDouble(Caixa::getValor).sum();
            saldoPorOrigem.put(origem, saldo);
        }
        return saldoPorOrigem;
    }
}
