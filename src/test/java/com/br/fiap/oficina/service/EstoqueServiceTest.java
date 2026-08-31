package com.br.fiap.oficina.service;

import com.br.fiap.oficina.model.entity.Caixa;
import com.br.fiap.oficina.model.entity.Estoque;
import com.br.fiap.oficina.model.entity.Material;
import com.br.fiap.oficina.model.enums.Insumo;
import com.br.fiap.oficina.model.enums.Origem;
import com.br.fiap.oficina.model.enums.Fluxo;
import com.br.fiap.oficina.model.repository.EstoqueRepository;
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
class EstoqueServiceTest {

    @Mock
    private EstoqueRepository repository;
    @Mock
    private CaixaService caixaService;

    private EstoqueService service;

    @BeforeEach
    void setup() {
        service = new EstoqueService(repository, caixaService);
    }

    @Test
    void salvar_delegatesToRepository() {
        var e = new Estoque();
        service.salvar(e);
        verify(repository).save(e);
    }

    @Test
    void debitar_decreasesQuantityAndSaves() {
        var m = Material.builder().id(1L).tipo(Insumo.PECA).build();
        var estoque = Estoque.builder().material(m).quantidade(10).minimo(5).build();
        when(repository.findByMaterial_Id(1L)).thenReturn(estoque);
        service.debitar(1L, 3);
        assertEquals(7, estoque.getQuantidade());
        verify(repository).save(estoque);
    }

    @Test
    void atualizar_increasesWhenBelowMin_andRegisters() {
        var m = Material.builder().id(2L).nome("Mat").custo(BigDecimal.TEN).tipo(Insumo.PECA).build();
        var estoque = Estoque.builder().material(m).quantidade(1).minimo(5).build();
        when(repository.findAll()).thenReturn(List.of(estoque));
        when(caixaService.registrar(anyString(), any(), any(), any())).thenReturn(true);
        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));
        service.atualizar();
        assertTrue(estoque.getQuantidade() > 1);
    }

    @Test
    void obterTodosPorData_delegatesToCaixaService() {
        when(caixaService.obterTodosPorData(any(), any())).thenReturn(List.of(new Caixa()));
        var res = service.obterTodosPorData(LocalDateTime.now(), LocalDateTime.now());
        assertEquals(1, res.size());
    }
}
