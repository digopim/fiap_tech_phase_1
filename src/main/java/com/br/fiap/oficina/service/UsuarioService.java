package com.br.fiap.oficina.service;

import com.br.fiap.oficina.model.dto.usuario.UsuarioRequest;
import com.br.fiap.oficina.model.entity.Usuario;
import com.br.fiap.oficina.model.enums.Perfil;
import com.br.fiap.oficina.model.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    private UsuarioRepository repository;

    public List<Usuario> listarUsuarios() {
        return (List<Usuario>) repository.findAll();
    }

    public Usuario buscarUsuarioPorId(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public Usuario buscarUsuarioPorCpfCNPJ(String cpfCNPJ) {
        return repository.findByCpfCNPJ(cpfCNPJ);
    }

    public void salvarUsuario(UsuarioRequest request) {
        var usuario = Usuario.builder()
                .id(request.id())
                .nome(request.nome())
                .sobrenome(request.sobrenome())
                .email(request.email())
                .cpfCNPJ(request.cpfCNPJ())
                .telefone(request.telefone())
                .perfil(Perfil.valueOf(request.perfil()))
                .build();
        repository.save(usuario);
    }

    public void deletarUsuario(Long id) {
        var usuario = repository.findById(id).orElseThrow();
        repository.delete(usuario);
    }

}
