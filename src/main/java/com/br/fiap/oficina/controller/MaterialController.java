package com.br.fiap.oficina.controller;

import com.br.fiap.oficina.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/material")
public class MaterialController {

    private final MaterialService service;

}
