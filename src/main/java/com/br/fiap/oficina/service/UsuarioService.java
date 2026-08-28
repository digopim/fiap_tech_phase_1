package com.br.fiap.oficina.service;

import com.br.fiap.oficina.model.dto.usuario.UsuarioRequest;
import com.br.fiap.oficina.model.entity.Usuario;
import com.br.fiap.oficina.model.enums.Perfil;
import com.br.fiap.oficina.model.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Usuario buscarUsuarioPorCpfCNPJ(String cpfCNPJ) {
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
        usuario.setNome(request.nome());
        usuario.setSobrenome(request.sobrenome());
        usuario.setEmail(request.email());
        usuario.setCpfCNPJ(request.cpfCNPJ());
        usuario.setTelefone(request.telefone());
        usuario.setPerfil(request.perfil() != null ? Perfil.valueOf(request.perfil()) : null);
        return repository.save(usuario);
    }

    public void deletarUsuario(Long id) {
        var usuario = repository.findById(id).orElseThrow();
        repository.delete(usuario);
    }

}
