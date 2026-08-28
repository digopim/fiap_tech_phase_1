package com.br.fiap.oficina.service;

import com.br.fiap.oficina.model.dto.credencial.CredencialRequest;
import com.br.fiap.oficina.model.dto.credencial.CredencialResponse;
import com.br.fiap.oficina.model.entity.Credencial;
import com.br.fiap.oficina.model.repository.CredencialRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CredencialService {

    private final PasswordEncoder encoder;
    private final CredencialRepository repository;
    private final UsuarioService usuarioService;

    public CredencialResponse cadastrar(CredencialRequest request) {
        Credencial credencial =
                repository.save(Credencial.builder()
                .login(request.login())
                .senha(encoder.encode(request.senha()))
                .usuario(usuarioService.novoUsuario(request.login()))
                .build());
        return CredencialResponse.builder().id(credencial.getId()).login(credencial.getLogin()).build();
    }

    public Credencial validar(String  login, String senha) {
        Optional<Credencial> maybe = repository.findByLogin(login);
        return maybe.filter(c -> encoder.matches(senha, c.getSenha())).orElse(null);
    }

    public Optional<Credencial> buscarPorLogin(String login) {
        return repository.findByLogin(login);
    }
}
