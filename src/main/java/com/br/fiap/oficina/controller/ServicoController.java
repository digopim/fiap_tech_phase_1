package com.br.fiap.oficina.controller;

import com.br.fiap.oficina.service.ServicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/servico")
public class ServicoController {

    private final ServicoService service;

}
