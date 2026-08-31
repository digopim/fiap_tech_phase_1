package com.br.fiap.oficina.service;

import com.br.fiap.oficina.model.entity.Caixa;
import com.br.fiap.oficina.model.enums.Fluxo;
import com.br.fiap.oficina.model.enums.Origem;
import com.br.fiap.oficina.model.repository.CaixaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CaixaServiceTest {

    @Mock
    private CaixaRepository repository;

    private CaixaService service;

    @BeforeEach
    void setup() {
        service = new CaixaService(repository);
    }

    @Test
    void registrar_savesAndReturnsTrue() {
        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));
        assertTrue(service.registrar("d", BigDecimal.TEN, Fluxo.ENTRADA, Origem.SERVICO));
    }

    @Test
    void calcularSaldoTotal_andPorOrigem() {
        var c1 = Caixa.builder().valor(BigDecimal.TEN).fluxo(Fluxo.ENTRADA).build();
        var c2 = Caixa.builder().valor(BigDecimal.valueOf(3)).fluxo(Fluxo.SAIDA).build();
        when(repository.findByFluxo(Fluxo.ENTRADA)).thenReturn(List.of(c1));
        when(repository.findByFluxo(Fluxo.SAIDA)).thenReturn(List.of(c2));
        var total = service.calcularSaldoTotal();
        assertEquals(BigDecimal.valueOf(7), total);

        when(repository.findByOrigem(Origem.SERVICO)).thenReturn(List.of(c1));
        var porOrigem = service.calcularSaldoPorOrigem(Origem.SERVICO);
        assertEquals(BigDecimal.TEN, porOrigem);
    }

    @Test
    void calcularSaldoPorData_delegatesToRepository() {
        LocalDateTime inicio = LocalDateTime.now();
        LocalDateTime fim = LocalDateTime.now();
        when(repository.findByFluxoAndDataBetween(Fluxo.ENTRADA, inicio, fim)).thenReturn(List.of(Caixa.builder().valor(BigDecimal.TEN).build()));
        when(repository.findByFluxoAndDataBetween(Fluxo.SAIDA, inicio, fim)).thenReturn(List.of());
        var res = service.calcularSaldoPorData(inicio, fim);
        assertEquals(BigDecimal.TEN, res);
    }
}
