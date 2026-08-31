package com.br.fiap.oficina.controller;

import com.br.fiap.oficina.model.dto.credencial.CredencialRequest;
import com.br.fiap.oficina.model.dto.credencial.CredencialResponse;
import com.br.fiap.oficina.service.CredencialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CredencialControllerTest {

    @Mock
    private CredencialService service;

    private CredencialController controller;

    @BeforeEach
    void setup() {
        controller = new CredencialController(service);
    }

    @Test
    void cadastrarCredencial_returnsCreatedMessage() {
        var req = CredencialRequest.builder().login("l").senha("s").build();
        when(service.cadastrar(req, null)).thenReturn(CredencialResponse.builder().id(1L).login("l").build());
        ResponseEntity<String> resp = controller.cadastrarCredencial(req);
        assertEquals(201, resp.getStatusCode().value());
        assertNotNull(resp.getBody());
        assertTrue(resp.getBody().contains("Credencial cadastrada"));
    }
}
