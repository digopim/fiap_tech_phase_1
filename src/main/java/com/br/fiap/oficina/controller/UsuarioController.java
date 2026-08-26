package com.br.fiap.oficina.controller;

import com.br.fiap.oficina.model.dto.usuario.UsuarioRequest;
import com.br.fiap.oficina.model.dto.usuario.UsuarioResponse;
import com.br.fiap.oficina.model.entity.Usuario;
import com.br.fiap.oficina.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService service;

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listar() {
        var lista = service.listarUsuarios().stream().map(this::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Long id) {
        var u = service.buscarUsuarioPorId(id);
        return ResponseEntity.ok(toResponse(u));
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<UsuarioResponse> buscarPorCpf(@PathVariable("cpf") String cpf) {
        var u = service.buscarUsuarioPorCpfCNPJ(cpf);
        return ResponseEntity.ok(toResponse(u));
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> criar(@RequestBody UsuarioRequest request) {
        var salvo = service.salvarUsuario(request);
        var resp = toResponse(salvo);
        URI uri = URI.create("/usuario/" + salvo.getId());
        return ResponseEntity.created(uri).body(resp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> atualizar(@PathVariable Long id, @RequestBody UsuarioRequest request) {
        var atualizado = service.atualizarUsuario(id, request);
        return ResponseEntity.ok(toResponse(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    private UsuarioResponse toResponse(Usuario u) {
        return new UsuarioResponse(
                u.getId(),
                u.getNome(),
                u.getSobrenome(),
                u.getEmail(),
                u.getCpfCNPJ(),
                u.getTelefone(),
                u.getPerfil() != null ? u.getPerfil().name() : null
        );
    }
}
