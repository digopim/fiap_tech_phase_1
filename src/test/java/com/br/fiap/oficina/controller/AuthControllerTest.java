package com.br.fiap.oficina.controller;

import com.br.fiap.oficina.model.dto.TokenResponse;
import com.br.fiap.oficina.model.entity.Credencial;
import com.br.fiap.oficina.security.JwtUtil;
import com.br.fiap.oficina.service.CredencialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private CredencialService credencialService;
    @Mock
    private JwtUtil jwtUtil;

    private AuthController controller;

    @BeforeEach
    void setup() {
        controller = new AuthController(credencialService, jwtUtil);
    }

    @Test
    void login_whenInvalid_returnsUnauthorized() {
        when(credencialService.validar("u","p")).thenReturn(null);
        ResponseEntity<TokenResponse> resp = controller.login("u","p");
        assertEquals(HttpStatus.UNAUTHORIZED, resp.getStatusCode());
    }

    @Test
    void login_whenValid_returnsToken() {
        var cred = Credencial.builder().id(1L).login("u").senha("s").build();
        when(credencialService.validar("u","p")).thenReturn(cred);
        when(jwtUtil.generateToken(cred)).thenReturn("tok");
        var resp = controller.login("u","p");
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertNotNull(resp.getBody());
        assertEquals("tok", resp.getBody().token());
    }
}
