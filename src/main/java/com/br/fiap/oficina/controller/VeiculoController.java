package com.br.fiap.oficina.controller;

import com.br.fiap.oficina.service.VeiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/veiculo")
public class VeiculoController {

    private final VeiculoService service;

}
