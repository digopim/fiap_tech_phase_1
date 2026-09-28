package com.br.fiap.oficina.application.controller;

import com.br.fiap.oficina.application.dto.orcamento.OrcamentoRequest;
import com.br.fiap.oficina.application.dto.ordem.AprovacaoRequest;
import com.br.fiap.oficina.application.dto.ordem.ConclusaoRequest;
import com.br.fiap.oficina.application.dto.ordem.OrdemRequest;
import com.br.fiap.oficina.application.dto.ordem.OrdemResponse;
import com.br.fiap.oficina.domain.enums.Status;
import com.br.fiap.oficina.application.service.OrdemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ordem")
public class OrdemController {

    private final OrdemService service;

    @PostMapping
    public ResponseEntity<OrdemResponse> criar(@RequestBody OrdemRequest request) {
        var resp = service.criarOrdem(request);
        URI uri = URI.create("/ordem/" + (resp.id() != null ? resp.id() : ""));
        return ResponseEntity.created(uri).body(resp);
    }

    @PostMapping("/{ordemId}/orcamento")
    public ResponseEntity<OrdemResponse> incluirOrcamento(@PathVariable Long ordemId, @RequestBody OrcamentoRequest request) {
        var resp = service.incluirOrcamento(ordemId, request);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/aprovar")
    public ResponseEntity<OrdemResponse> aprovarOrcamento(@RequestParam String cpfCNPJ, @RequestParam String placa, @RequestBody AprovacaoRequest request) {
        var resp = service.aprovarOrcamento(cpfCNPJ, placa, request.aprovado());
        return ResponseEntity.ok(resp);
    }

    @GetMapping
    public ResponseEntity<List<OrdemResponse>> obterOrdens(@RequestParam(required = false) String placa, @RequestParam(required = false) String cpfCNPJ) {
        var lista = service.obterOrdens(placa, cpfCNPJ);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/status")
    public ResponseEntity<List<OrdemResponse>> obterOrdensPorStatus(@RequestParam Status status) {
        var lista = service.obterOrdensPorStatus(status);
        return ResponseEntity.ok(lista);
    }

    @PostMapping("/concluir")
    public ResponseEntity<OrdemResponse> concluirOrcamento(@RequestBody ConclusaoRequest request) {
        var resp = service.concluirOrcamento(request.ordem(), request.orcamento());
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/{ordemId}/pagar")
    public ResponseEntity<OrdemResponse> registrarPagamento(@PathVariable Long ordemId) {
        var resp = service.registrarPagamento(ordemId);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/retirada")
    public ResponseEntity<List<OrdemResponse>> efetuarRetirada(@RequestParam String placa) {
        var lista = service.efetuarRetirada(placa);
        return ResponseEntity.ok(lista);
    }

    @PostMapping("/{ordemId}/cancelar")
    public ResponseEntity<OrdemResponse> cancelarOrdem(@PathVariable Long ordemId) {
        var resp = service.cancelarOrdem(ordemId);
        return ResponseEntity.ok(resp);
    }
}
