package com.br.fiap.oficina.controller;

import com.br.fiap.oficina.model.dto.TokenResponse;
import com.br.fiap.oficina.model.dto.credencial.CredencialRequest;
import com.br.fiap.oficina.service.CredencialService;
import com.br.fiap.oficina.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final CredencialService credencialService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody final CredencialRequest request) {
        boolean ok = credencialService.validar(request);
        if (!ok) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String token = jwtUtil.generateToken(request.login());
        return ResponseEntity.ok(new TokenResponse(token));
    }
}