package com.br.fiap.oficina.service;

import com.br.fiap.oficina.model.dto.material.MaterialRequest;
import com.br.fiap.oficina.model.entity.Estoque;
import com.br.fiap.oficina.model.entity.Material;
import com.br.fiap.oficina.model.enums.Insumo;
import com.br.fiap.oficina.model.repository.MaterialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MaterialServiceTest {

    @Mock
    private MaterialRepository repository;
    @Mock
    private EstoqueService estoqueService;

    private MaterialService service;

    @BeforeEach
    void setup() {
        service = new MaterialService(repository, estoqueService);
    }

    @Test
    void cadastrarMaterial_savesAndCreatesEstoque() {
        var req = MaterialRequest.builder().id(null).nome("M").descricao("D").valor(BigDecimal.TEN).custo(BigDecimal.ONE).tipo(null).build();
        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));
        when(estoqueService.salvar(any())).thenReturn(null);
        var saved = service.cadastrarMaterial(req);
        assertEquals("M", saved.getNome());
        verify(estoqueService, times(1)).salvar(any(Estoque.class));
    }

    @Test
    void debitarMaterial_delegatesToEstoque() {
        service.debitarMaterial(1L, 2);
        verify(estoqueService).debitar(1L, 2);
    }

    @Test
    void listarMateriais_mapsResponses() {
        when(repository.findAll()).thenReturn(List.of(Material.builder().id(1L).nome("A").valor(BigDecimal.ONE).tipo(Insumo.MATERIAL).build()));
        var res = service.listarMateriais();
        assertEquals(1, res.size());
    }

    @Test
    void buscarMaterialPorId_returnsEntity() {
        when(repository.findById(2L)).thenReturn(Optional.of(Material.builder().id(2L).nome("B").build()));
        var m = service.buscarMaterialPorId(2L);
        assertEquals(2L, m.getId());
    }
}
