package com.br.fiap.oficina.controller;

import com.br.fiap.oficina.model.dto.material.MaterialRequest;
import com.br.fiap.oficina.model.dto.material.MaterialResponse;
import com.br.fiap.oficina.model.entity.Material;
import com.br.fiap.oficina.model.enums.Insumo;
import com.br.fiap.oficina.service.MaterialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MaterialControllerTest {

    @Mock
    private MaterialService service;

    private MaterialController controller;

    @BeforeEach
    void setup() {
        controller = new MaterialController(service);
    }

    @Test
    void listar_delegatesToService() {
        when(service.listarMateriais()).thenReturn(List.of(MaterialResponse.fromEntity(Material.builder().id(1L).nome("A").valor(BigDecimal.ONE).tipo(Insumo.MATERIAL).build())));
        ResponseEntity<List<MaterialResponse>> resp = controller.listar();
        assertEquals(200, resp.getStatusCode().value());
        assertNotNull(resp.getBody());
        assertEquals(1, resp.getBody().size());
    }

    @Test
    void criar_returnsCreated() {
        var req = MaterialRequest.builder().id(null).nome("M").descricao(null).valor(BigDecimal.TEN).custo(BigDecimal.ONE).tipo(Insumo.ALIMENTO.name()).build();
        var saved = Material.builder().id(2L).nome("M").valor(BigDecimal.TEN).tipo(Insumo.ALIMENTO).build();
        when(service.cadastrarMaterial(req)).thenReturn(saved);
        var resp = controller.criar(req);
        assertEquals(201, resp.getStatusCode().value());
        assertNotNull(resp.getHeaders().getLocation());
        assertEquals("/material/2", resp.getHeaders().getLocation().toString());
    }
}
