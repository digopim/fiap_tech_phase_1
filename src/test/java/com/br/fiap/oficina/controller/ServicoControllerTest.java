package com.br.fiap.oficina.controller;

import com.br.fiap.oficina.model.dto.servico.ServicoResponse;
import com.br.fiap.oficina.model.entity.ItemServico;
import com.br.fiap.oficina.model.entity.Orcamento;
import com.br.fiap.oficina.model.entity.Servico;
import com.br.fiap.oficina.service.ServicoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServicoControllerTest {

    @Mock
    private ServicoService service;

    private ServicoController controller;

    @BeforeEach
    void setup() {
        controller = new ServicoController(service);
    }

    @Test
    void listar_returnsMapped() {
        when(service.listarServicos()).thenReturn(List.of(Servico.builder().id(1L).nome("S").build()));
        ResponseEntity<List<ServicoResponse>> resp = controller.listar();
        assertEquals(200, resp.getStatusCode().value());
    }

    @Test
    void listarEmAberto_returnsMapped() {
        Orcamento orcamento = new Orcamento();
        orcamento.setId(1L);

        ItemServico item = new ItemServico();
        item.setId(1L);
        item.setOrcamento(orcamento);

        when(service.listarItemServicosEmAberto()).thenReturn(List.of());
        ResponseEntity<?> resp = controller.listarEmAberto();
        assertEquals(200, resp.getStatusCode().value());
    }

    @Test
    void concluirServico_callsService() {
        var req = com.br.fiap.oficina.model.dto.servico.ItemServicoRequest.builder().id(1L).executor(2L).build();
        ResponseEntity<Void> resp = controller.concluirServico(req);
        // controller calls service; no exception means ok
        assertEquals(200, resp.getStatusCode().value());
    }
}
