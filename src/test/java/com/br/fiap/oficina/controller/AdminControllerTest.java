package com.br.fiap.oficina.controller;

import com.br.fiap.oficina.model.dto.admin.Panorama;
import com.br.fiap.oficina.model.dto.estoque.EstoqueResponse;
import com.br.fiap.oficina.model.dto.ordem.OrdemResponse;
import com.br.fiap.oficina.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    private AdminService adminService;

    private AdminController controller;

    @BeforeEach
    void setup() {
        controller = new AdminController(adminService);
    }

    @Test
    void listarEstoqueAtual_delegates() {
        when(adminService.obterEstoqueAtual()).thenReturn(List.of(EstoqueResponse.builder().preco(BigDecimal.ZERO).quantidade(0).build()));
        ResponseEntity<List<EstoqueResponse>> resp = controller.listarEstoqueAtual();
        assertEquals(200, resp.getStatusCode().value());
    }

    @Test
    void obterPanorama_delegates() {
        when(adminService.obterPanorama(any(), any())).thenReturn(Panorama.builder().build());
        ResponseEntity<Panorama> resp = controller.obterPanorama(LocalDateTime.now(), LocalDateTime.now());
        assertEquals(200, resp.getStatusCode().value());
    }

    @Test
    void obterOrdens_delegates() {
        when(adminService.obterOrdensPorCpfCNPJ("c")).thenReturn(List.of(OrdemResponse.builder().build()));
        ResponseEntity<List<OrdemResponse>> resp = controller.obterOrdensPorCpfCNPJ("c");
        assertEquals(200, resp.getStatusCode().value());
    }
}
