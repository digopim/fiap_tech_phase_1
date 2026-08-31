package com.br.fiap.oficina.controller;

import com.br.fiap.oficina.model.dto.credencial.CredencialRequest;
import com.br.fiap.oficina.model.dto.credencial.CredencialResponse;
import com.br.fiap.oficina.service.CredencialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/credencial")
public class CredencialController {

    private final CredencialService service;

    @PutMapping("/cadastrar")
    public ResponseEntity<String> cadastrarCredencial(@RequestBody @Validated final CredencialRequest request) {
        CredencialResponse response = service.cadastrar(request, null);
        return ResponseEntity.status(HttpStatus.CREATED).body("Credencial cadastrada com sucesso, Login : " + response.login());
    }
}
