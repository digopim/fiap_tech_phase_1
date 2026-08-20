package com.br.fiap.oficina.controller;

import com.br.fiap.oficina.service.OrdemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/ordem")
public class OrdemController {

    private final OrdemService service;

}
