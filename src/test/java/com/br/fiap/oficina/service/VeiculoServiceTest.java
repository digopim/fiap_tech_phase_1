package com.br.fiap.oficina.service;

import com.br.fiap.oficina.model.dto.veiculo.VeiculoRequest;
import com.br.fiap.oficina.model.entity.Veiculo;
import com.br.fiap.oficina.model.repository.VeiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VeiculoServiceTest {

    @Mock
    private VeiculoRepository repository;

    private VeiculoService service;

    @BeforeEach
    void setup() {
        service = new VeiculoService(repository);
    }

    @Test
    void listarVeiculos_delegatesToRepository() {
        when(repository.findAll()).thenReturn(List.of(Veiculo.builder().id(1L).placa("X").build()));
        var list = service.listarVeiculos();
        assertEquals(1, list.size());
    }

    @Test
    void salvarVeiculo_savesEntity() {
        var req = new VeiculoRequest(null, "ABC", "M", "Mo", "C", null, null, 2020, 10000);
        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));
        var saved = service.salvarVeiculo(req);
        assertEquals("ABC", saved.getPlaca());
    }

    @Test
    void atualizarVeiculo_updatesFieldsAndSaves() {
        var existente = Veiculo.builder().id(2L).placa("OLD").build();
        var req = new VeiculoRequest(null, "NEW", "M", "Mo", "C", null, null, 2021, 20000);
        when(repository.findById(2L)).thenReturn(Optional.of(existente));
        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));
        var updated = service.atualizarVeiculo(2L, req);
        assertEquals("NEW", updated.getPlaca());
    }

    @Test
    void deletarVeiculo_deletesWhenFound() {
        var existente = Veiculo.builder().id(3L).placa("DEL").build();
        when(repository.findById(3L)).thenReturn(Optional.of(existente));
        service.deletarVeiculo(3L);
        verify(repository, times(1)).delete(existente);
    }
}
