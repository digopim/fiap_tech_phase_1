package com.br.fiap.oficina.service;

import com.br.fiap.oficina.model.dto.usuario.UsuarioRequest;
import com.br.fiap.oficina.model.entity.Usuario;
import com.br.fiap.oficina.model.enums.Perfil;
import com.br.fiap.oficina.model.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository repository;

    public List<Usuario> listarUsuarios() {
        return (List<Usuario>) repository.findAll();
    }

    public Usuario buscarUsuarioPorId(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public Optional<Usuario> buscarUsuarioPorCpfCNPJ(String cpfCNPJ) {
        return repository.findByCpfCNPJ(cpfCNPJ);
    }

    public Usuario novoUsuario(String cpfCNPJ) {
        return repository.save(Usuario.builder().cpfCNPJ(cpfCNPJ).perfil(Perfil.CLIENTE).build());
    }

    public Usuario salvarUsuario(UsuarioRequest request) {
        var usuario = Usuario.builder()
                .id(request.id())
                .nome(request.nome())
                .sobrenome(request.sobrenome())
                .email(request.email())
                .cpfCNPJ(request.cpfCNPJ())
                .telefone(request.telefone())
                .perfil(request.perfil() != null ? Perfil.valueOf(request.perfil()) : null)
                .build();
        return repository.save(usuario);
    }

    public Usuario atualizarUsuario(Long id, UsuarioRequest request) {
        var usuario = repository.findById(id).orElseThrow();
        if(request.nome() != null) {
            usuario.setNome(request.nome());
        }
        if(request.sobrenome() != null) {
            usuario.setSobrenome(request.sobrenome());
        }
        if(request.email() != null) {
            usuario.setEmail(request.email());
        }
        if(request.cpfCNPJ() != null) {
            usuario.setCpfCNPJ(request.cpfCNPJ());
        }
        if(request.telefone() != null) {
            usuario.setTelefone(request.telefone());
        }
        if(request.perfil() != null) {
            usuario.setPerfil(Perfil.valueOf(request.perfil()));
        }
        return repository.save(usuario);
    }

    public void deletarUsuario(Long id) {
        var usuario = repository.findById(id).orElseThrow();
        repository.delete(usuario);
    }

}
