package com.br.fiap.oficina.service;

import com.br.fiap.oficina.model.dto.usuario.UsuarioRequest;
import com.br.fiap.oficina.model.entity.Usuario;
import com.br.fiap.oficina.model.enums.Perfil;
import com.br.fiap.oficina.model.repository.UsuarioRepository;
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
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    private UsuarioService service;

    @BeforeEach
    void setup() {
        service = new UsuarioService(repository);
    }

    @Test
    void listarUsuarios_delegates() {
        when(repository.findAll()).thenReturn(List.of(Usuario.builder().id(1L).nome("A").build()));
        var res = service.listarUsuarios();
        assertEquals(1, res.size());
    }

    @Test
    void novoUsuario_savesWithCpf() {
        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));
        var saved = service.novoUsuario("123");
        assertEquals("123", saved.getCpfCNPJ());
        assertEquals(Perfil.CLIENTE, saved.getPerfil());
    }

    @Test
    void atualizarUsuario_updatesNonNullFields() {
        var existent = Usuario.builder().id(5L).nome("Old").build();
        var req = UsuarioRequest.builder().id(5L).nome("New").sobrenome(null).email(null).cpfCNPJ(null).telefone(null).perfil(null).build();
        when(repository.findById(5L)).thenReturn(Optional.of(existent));
        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));
        var updated = service.atualizarUsuario(5L, req);
        assertEquals("New", updated.getNome());
    }

    @Test
    void deletarUsuario_deletesWhenFound() {
        var existent = Usuario.builder().id(6L).build();
        when(repository.findById(6L)).thenReturn(Optional.of(existent));
        service.deletarUsuario(6L);
        verify(repository).delete(existent);
    }
}
