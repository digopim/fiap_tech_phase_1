package com.br.fiap.oficina.service;

import com.br.fiap.oficina.model.entity.Caixa;
import com.br.fiap.oficina.model.enums.Fluxo;
import com.br.fiap.oficina.model.enums.Origem;
import com.br.fiap.oficina.model.repository.CaixaRepository;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static java.time.LocalDateTime.now;

@Service
@AllArgsConstructor
public class CaixaService {

    private CaixaRepository repository;

    public boolean registrar(String descricao, BigDecimal valor, Fluxo fluxo, Origem origem) {
        Caixa saved = repository.save(Caixa.builder()
                .descricao(descricao)
                .valor(valor)
                .fluxo(fluxo)
                .origem(origem)
                .data(now(ZoneId.of("America/Sao_Paulo")))
                .build());
        return saved.getId() != null;
    }

    public BigDecimal calcularSaldoTotal() {
        return repository.findByFluxo(Fluxo.ENTRADA).stream().map(Caixa::getValor).reduce(BigDecimal.ZERO, BigDecimal::add)
                .subtract(repository.findByFluxo(Fluxo.SAIDA).stream().map(Caixa::getValor).reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    public BigDecimal calcularSaldoPorOrigem(Origem origem) {
        return repository.findByOrigem(origem).stream().map(Caixa::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calcularSaldoPorData(@NotNull LocalDateTime dataInicio, @NotNull LocalDateTime dataFim) {
        return repository.findByFluxoAndDataBetween(Fluxo.ENTRADA, dataInicio, dataFim).stream()
                .map(Caixa::getValor).reduce(BigDecimal.ZERO, BigDecimal::add)
                .subtract(
                repository.findByFluxoAndDataBetween(Fluxo.SAIDA, dataInicio, dataFim).stream()
                .map(Caixa::getValor).reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    public Map<Origem, BigDecimal> calcularSaldoPorOrigem(@NotNull LocalDateTime dataInicio, @NotNull LocalDateTime dataFim) {
        Map<Origem, BigDecimal> saldoPorOrigem = new EnumMap<>(Origem.class);
        for (Origem origem : Origem.values()) {
            BigDecimal saldo = repository.findByOrigemAndDataBetween(origem, dataInicio, dataFim).stream().map(Caixa::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
            saldoPorOrigem.put(origem, saldo);
        }
        return saldoPorOrigem;
    }

    public List<Caixa> obterTodosPorData(@NotNull LocalDateTime dataInicio, @NotNull LocalDateTime dataFim) {
        return repository.findByDataBetweenOrderByDataAsc(dataInicio,  dataFim);
    }
}
