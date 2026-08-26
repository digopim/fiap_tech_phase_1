package com.br.fiap.oficina.controller;

import com.br.fiap.oficina.model.dto.servico.ServicoRequest;
import com.br.fiap.oficina.model.dto.servico.ServicoResponse;
import com.br.fiap.oficina.model.entity.Servico;
import com.br.fiap.oficina.service.ServicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/servico")
public class ServicoController {

    private final ServicoService service;

    @GetMapping
    public ResponseEntity<List<ServicoResponse>> listar() {
        var lista = service.listarServicos()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicoResponse> buscarPorId(@PathVariable Long id) {
        var s = service.buscarServicoPorId(id);
        return ResponseEntity.ok(toResponse(s));
    }

    @PostMapping
    public ResponseEntity<ServicoResponse> criar(@RequestBody ServicoRequest request) {
        var salvo = service.cadastrarServico(request);
        var resp = toResponse(salvo);
        URI uri = URI.create("/servico/" + salvo.getId());
        return ResponseEntity.created(uri).body(resp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicoResponse> atualizar(@PathVariable Long id, @RequestBody ServicoRequest request) {
        var atualizado = service.atualizarServico(id, request);
        return ResponseEntity.ok(toResponse(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletarServico(id);
        return ResponseEntity.noContent().build();
    }

    private ServicoResponse toResponse(Servico s) {
        return ServicoResponse.builder()
                .id(s.getId())
                .nome(s.getNome())
                .descricao(s.getDescricao())
                .custo(s.getCusto())
                .valor(s.getValor())
                .duracao(s.getDuracao())
                .build();
    }
}
