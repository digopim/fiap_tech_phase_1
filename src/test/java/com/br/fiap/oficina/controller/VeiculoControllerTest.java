package com.br.fiap.oficina.controller;

import com.br.fiap.oficina.model.dto.veiculo.VeiculoRequest;
import com.br.fiap.oficina.model.dto.veiculo.VeiculoResponse;
import com.br.fiap.oficina.model.entity.Veiculo;
import com.br.fiap.oficina.service.VeiculoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VeiculoControllerTest {

    @Mock
    private VeiculoService service;

    private VeiculoController controller;

    @BeforeEach
    void setup() {
        controller = new VeiculoController(service);
    }

    @Test
    void listar_returnsMappedList() {
        when(service.listarVeiculos()).thenReturn(List.of(Veiculo.builder().id(1L).placa("P").build()));
        ResponseEntity<List<VeiculoResponse>> resp = controller.listar();
        assertEquals(200, resp.getStatusCode().value());
        assertNotNull(resp.getBody());
        assertEquals(1, resp.getBody().size());
    }

    @Test
    void buscarPorPlaca_notFound_returns404() {
        when(service.buscarVeiculoPorPlaca("X")).thenReturn(Optional.empty());
        var resp = controller.buscarPorPlaca("X");
        assertEquals(404, resp.getStatusCode().value());
    }

    @Test
    void criar_returnsCreated() {
        var req = new VeiculoRequest(null, "ABC", null, null, null, null, null, null, null);
        var saved = Veiculo.builder().id(9L).placa("ABC").build();
        when(service.salvarVeiculo(req)).thenReturn(saved);
        var resp = controller.criar(req);
        assertEquals(201, resp.getStatusCode().value());
        assertNotNull(resp.getHeaders().getLocation());
        assertEquals("/veiculo/9", resp.getHeaders().getLocation().toString());
    }
}
