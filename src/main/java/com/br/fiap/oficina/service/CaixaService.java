package com.br.fiap.oficina.service;

import com.br.fiap.oficina.model.entity.Caixa;
import com.br.fiap.oficina.model.enums.Fluxo;
import com.br.fiap.oficina.model.enums.Origem;
import com.br.fiap.oficina.model.repository.CaixaRepository;

import java.time.ZoneId;

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
}
