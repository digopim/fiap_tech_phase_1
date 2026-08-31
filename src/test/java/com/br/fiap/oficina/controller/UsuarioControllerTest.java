package com.br.fiap.oficina.controller;

import com.br.fiap.oficina.model.dto.usuario.UsuarioRequest;
import com.br.fiap.oficina.model.dto.usuario.UsuarioResponse;
import com.br.fiap.oficina.model.entity.Usuario;
import com.br.fiap.oficina.service.UsuarioService;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    private UsuarioService service;

    private UsuarioController controller;

    @BeforeEach
    void setup() {
        controller = new UsuarioController(service);
    }

    @Test
    void listar_returnsMapped() {
        when(service.listarUsuarios()).thenReturn(List.of(Usuario.builder().id(1L).nome("U").build()));
        ResponseEntity<List<UsuarioResponse>> resp = controller.listar();
        assertEquals(200, resp.getStatusCode().value());
    }

    @Test
    void buscarPorCpf_notFound_returns404() {
        when(service.buscarUsuarioPorCpfCNPJ("x")).thenReturn(Optional.empty());
        ResponseEntity<UsuarioResponse> resp = controller.buscarPorCpf("x");
        assertEquals(404, resp.getStatusCode().value());
    }

    @Test
    void atualizar_delegatesAndReturns() {
        var req = UsuarioRequest.builder().id(2L).nome("N").sobrenome(null).email(null).cpfCNPJ(null).telefone(null).perfil(null).build();
        when(service.atualizarUsuario(eq(2L), any())).thenReturn(Usuario.builder().id(2L).nome("N").build());
        ResponseEntity<UsuarioResponse> resp = controller.atualizar(2L, req);
        assertEquals(200, resp.getStatusCode().value());
        assertNotNull(resp.getBody());
        assertEquals("N", resp.getBody().nome());
    }
}
