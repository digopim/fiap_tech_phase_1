package com.br.fiap.oficina.controller;

import com.br.fiap.oficina.model.dto.admin.Panorama;
import com.br.fiap.oficina.model.dto.estoque.EstoqueResponse;
import com.br.fiap.oficina.model.dto.ordem.OrdemResponse;
import com.br.fiap.oficina.service.AdminService;
import com.br.fiap.oficina.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final UsuarioService usuarioService;

    @GetMapping("/estoque")
    public ResponseEntity<List<EstoqueResponse>> listarEstoqueAtual() {
        return ResponseEntity.ok(adminService.obterEstoqueAtual());
    }

    @GetMapping("/panorama")
    public ResponseEntity<List<Panorama>> obterPanorama(
            @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam("fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        return ResponseEntity.ok(adminService.obterPanorama(inicio, fim));
    }

    @GetMapping("/ordens/cpf")
    public ResponseEntity<List<OrdemResponse>> obterOrdensPorCpfCNPJ(@RequestParam("cpfCNPJ") String cpfCNPJ) {
        return ResponseEntity.ok(adminService.obterOrdensPorCpfCNPJ(cpfCNPJ));
    }

    @GetMapping("/ordens/placa")
    public ResponseEntity<List<OrdemResponse>> obterOrdensPorPlaca(@RequestParam("placa") String placa) {
        return ResponseEntity.ok(adminService.obterOrdensPorPlaca(placa));
    }

}
