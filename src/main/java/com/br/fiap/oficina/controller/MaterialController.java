package com.br.fiap.oficina.controller;

import com.br.fiap.oficina.model.dto.material.MaterialRequest;
import com.br.fiap.oficina.model.dto.material.MaterialResponse;
import com.br.fiap.oficina.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/material")
public class MaterialController {

    private final MaterialService service;

    @GetMapping
    public ResponseEntity<List<MaterialResponse>> listar() {
        return ResponseEntity.ok(service.listarMateriais());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialResponse> buscarPorId(@PathVariable Long id) {
        var m = service.buscarMaterialPorId(id);
        return ResponseEntity.ok(MaterialResponse.fromEntity(m));
    }

    @PostMapping
    public ResponseEntity<MaterialResponse> criar(@RequestBody MaterialRequest request) {
        var salvo = service.cadastrarMaterial(request);
        var resp = MaterialResponse.fromEntity(salvo);
        URI uri = URI.create("/material/" + salvo.getId());
        return ResponseEntity.created(uri).body(resp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaterialResponse> atualizar(@PathVariable Long id, @RequestBody MaterialRequest request) {
        var atualizado = service.atualizarMaterial(id, request);
        return ResponseEntity.ok(MaterialResponse.fromEntity(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletarMaterial(id);
        return ResponseEntity.noContent().build();
    }

}
