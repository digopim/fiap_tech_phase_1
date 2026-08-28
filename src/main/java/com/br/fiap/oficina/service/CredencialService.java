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

    public CredencialResponse cadastrar(CredencialRequest request) {
        Credencial credencial =
                repository.save(Credencial.builder()
                .login(request.login())
                .senha(encoder.encode(request.senha()))
                .build());
        return new CredencialResponse(credencial.getId(), credencial.getLogin());
    }

    public boolean validar(CredencialRequest request) {
        Optional<Credencial> maybe = repository.findByLogin(request.login());
        return maybe.map(c -> encoder.matches(request.senha(), c.getSenha())).orElse(false);
    }

    public Optional<Credencial> buscarPorLogin(String login) {
        return repository.findByLogin(login);
    }
}
