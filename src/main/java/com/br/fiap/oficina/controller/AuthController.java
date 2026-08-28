package com.br.fiap.oficina.controller;

import com.br.fiap.oficina.model.dto.TokenResponse;
import com.br.fiap.oficina.model.entity.Credencial;
import com.br.fiap.oficina.security.JwtUtil;
import com.br.fiap.oficina.service.CredencialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final CredencialService credencialService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestHeader String login, @RequestHeader String senha) {
        Credencial credencial = credencialService.validar(login, senha);
        if (credencial == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String token = jwtUtil.generateToken(credencial);
        return ResponseEntity.ok(new TokenResponse(token));
    }
}