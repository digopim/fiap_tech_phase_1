package com.br.fiap.oficina.service;

import com.br.fiap.oficina.model.dto.credencial.CredencialRequest;
import com.br.fiap.oficina.model.entity.Credencial;
import com.br.fiap.oficina.model.entity.Usuario;
import com.br.fiap.oficina.model.repository.CredencialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CredencialServiceTest {

    @Mock
    private PasswordEncoder encoder;
    @Mock
    private CredencialRepository repository;
    @Mock
    private UsuarioService usuarioService;

    private CredencialService service;

    @BeforeEach
    void setup() {
        service = new CredencialService(encoder, repository, usuarioService);
    }

    @Test
    void cadastrar_usesEncoderAndSaves() {
        var user = Usuario.builder().id(1L).nome("U").build();
        var req = CredencialRequest.builder().login("l").senha("s").build();
        when(encoder.encode("s")).thenReturn("enc");
        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));
        var resp = service.cadastrar(req, user);
        assertEquals("l", resp.login());
    }

    @Test
    void validar_returnsCredencialWhenMatches() {
        var cred = Credencial.builder().id(2L).login("x").senha("enc").build();
        when(repository.findByLogin("x")).thenReturn(Optional.of(cred));
        when(encoder.matches("raw","enc")).thenReturn(true);
        var found = service.validar("x","raw");
        assertNotNull(found);
    }

    @Test
    void validar_returnsNullWhenNotMatches() {
        when(repository.findByLogin("y")).thenReturn(Optional.empty());
        var found = service.validar("y","r");
        assertNull(found);
    }
}
