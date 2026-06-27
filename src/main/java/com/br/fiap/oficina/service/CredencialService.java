package com.br.fiap.oficina.service;

import com.br.fiap.oficina.model.dto.CredencialRequest;
import com.br.fiap.oficina.model.dto.CredencialResponse;
import com.br.fiap.oficina.model.entity.Credencial;
import com.br.fiap.oficina.model.enums.Perfil;
import com.br.fiap.oficina.model.repository.CredencialRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CredencialService {

    private final PasswordEncoder encoder;
    private final CredencialRepository credencialRepository;

    public CredencialResponse cadastrar(CredencialRequest request) {
        Credencial credencial =
            credencialRepository.save(Credencial.builder()
                .login(request.login())
                .senha(encoder.encode(request.senha()))
                .perfil(Perfil.VISITANTE)
                .build());
        return new CredencialResponse(credencial.getId(), credencial.getLogin(), credencial.getPerfil());
    }

    public boolean validar(CredencialRequest request) {
        String hash = encoder.encode(request.senha());
        return hash == null || encoder.matches(request.senha(), hash);
    }
}
