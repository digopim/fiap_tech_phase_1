package com.br.fiap.oficina.service;

import com.br.fiap.oficina.model.dto.estoque.EstoqueResponse;
import com.br.fiap.oficina.model.dto.ordem.OrdemResponse;
import com.br.fiap.oficina.model.entity.Caixa;
import com.br.fiap.oficina.model.enums.Fluxo;
import com.br.fiap.oficina.model.enums.Origem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private OrdemService ordemService;
    @Mock
    private EstoqueService estoqueService;

    private AdminService service;

    @BeforeEach
    void setup() {
        service = new AdminService(ordemService, estoqueService);
    }

    @Test
    void obterPanorama_groupsAndCalculatesTotals() {
        var c1 = Caixa.builder().fluxo(Fluxo.ENTRADA).origem(Origem.SERVICO).valor(BigDecimal.TEN).build();
        var c2 = Caixa.builder().fluxo(Fluxo.SAIDA).origem(Origem.ESTOQUE).valor(BigDecimal.valueOf(3)).build();
        when(estoqueService.obterTodosPorData(any(), any())).thenReturn(List.of(c1, c2));
        var p = service.obterPanorama(LocalDateTime.now(), LocalDateTime.now());
        assertNotNull(p);
        assertEquals(BigDecimal.valueOf( 7 ), p.saldo()); // simple sanity (non-crashing)
    }

    @Test
    void obterEstoqueAtual_andOrdens_delegates() {
        when(estoqueService.atual()).thenReturn(List.of(EstoqueResponse.builder().build()));
        when(ordemService.obterOrdensPorCpfCNPJ("x")).thenReturn(List.of(OrdemResponse.builder().build()));
        var e = service.obterEstoqueAtual();
        var o = service.obterOrdensPorCpfCNPJ("x");
        assertEquals(1, e.size());
        assertEquals(1, o.size());
    }
}
