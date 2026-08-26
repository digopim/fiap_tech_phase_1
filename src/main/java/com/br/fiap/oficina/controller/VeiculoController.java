package com.br.fiap.oficina.controller;

import com.br.fiap.oficina.model.dto.veiculo.VeiculoRequest;
import com.br.fiap.oficina.model.dto.veiculo.VeiculoResponse;
import com.br.fiap.oficina.model.entity.Veiculo;
import com.br.fiap.oficina.service.VeiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/veiculo")
public class VeiculoController {

    private final VeiculoService service;

    @GetMapping
    public ResponseEntity<List<VeiculoResponse>> listar() {
        var lista = service.listarVeiculos()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeiculoResponse> buscarPorId(@PathVariable Long id) {
        var v = service.buscarVeiculoPorId(id);
        return ResponseEntity.ok(toResponse(v));
    }

    @GetMapping("/placa/{placa}")
    public ResponseEntity<VeiculoResponse> buscarPorPlaca(@PathVariable String placa) {
        var v = service.buscarVeiculoPorPlaca(placa);
        return ResponseEntity.ok(toResponse(v));
    }

    @PostMapping
    public ResponseEntity<VeiculoResponse> criar(@RequestBody VeiculoRequest request) {
        var salvo = service.salvarVeiculo(request);
        var resp = toResponse(salvo);
        URI uri = URI.create("/veiculo/" + salvo.getId());
        return ResponseEntity.created(uri).body(resp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VeiculoResponse> atualizar(@PathVariable Long id, @RequestBody VeiculoRequest request) {
        var atualizado = service.atualizarVeiculo(id, request);
        return ResponseEntity.ok(toResponse(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletarVeiculo(id);
        return ResponseEntity.noContent().build();
    }

    private VeiculoResponse toResponse(Veiculo v) {
        return new VeiculoResponse(
                v.getId(),
                v.getPlaca(),
                v.getMontadora(),
                v.getModelo(),
                v.getCor(),
                v.getTipo() != null ? v.getTipo().name() : null,
                v.getChassi(),
                v.getAnoFabricacao(),
                v.getQuilometragem()
        );
    }
}
