package com.br.fiap.oficina.controller;

import com.br.fiap.oficina.model.dto.orcamento.OrcamentoRequest;
import com.br.fiap.oficina.model.dto.ordem.AprovacaoRequest;
import com.br.fiap.oficina.model.dto.ordem.ConclusaoRequest;
import com.br.fiap.oficina.model.dto.ordem.OrdemRequest;
import com.br.fiap.oficina.model.dto.ordem.OrdemResponse;
import com.br.fiap.oficina.service.OrdemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrdemControllerTest {

    @Mock
    private OrdemService service;

    private OrdemController controller;

    @BeforeEach
    void setup() {
        controller = new OrdemController(service);
    }

    @Test
    void criar_returnsCreated() {
        var req = OrdemRequest.builder().veiculo(null).responsavel(1L).cliente(null).formulario(null).build();
        var respObj = OrdemResponse.builder().status("RECEBIDA").valorTotal(BigDecimal.ZERO).cliente("R").responsavel("c").orcamentos(List.of()).build();

        when(service.criarOrdem(req)).thenReturn(respObj);
        ResponseEntity<OrdemResponse> resp = controller.criar(req);
        assertEquals(201, resp.getStatusCode().value());
    }

    @Test
    void incluirOrcamento_delegates() {
        when(service.incluirOrcamento(eq(1L), any())).thenReturn(OrdemResponse.builder().status("X").orcamentos(List.of()).build());
        ResponseEntity<OrdemResponse> resp = controller.incluirOrcamento(1L, new OrcamentoRequest(null, Map.of(), Map.of(), false));
        assertEquals(200, resp.getStatusCode().value());
    }

    @Test
    void aprovarOrcamento_callsService() {
        var aprov = AprovacaoRequest.builder().aprovado(true).build();
        when(service.aprovarOrcamento("c","p", true)).thenReturn(OrdemResponse.builder().status("X").orcamentos(List.of()).build());
        ResponseEntity<OrdemResponse> resp = controller.aprovarOrcamento("c","p", aprov);
        assertEquals(200, resp.getStatusCode().value());
    }

    @Test
    void obterOrdens_returnsList() {
        when(service.obterOrdens(null, null)).thenReturn(List.of(OrdemResponse.builder().orcamentos(List.of()).build()));
        ResponseEntity<List<OrdemResponse>> resp = controller.obterOrdens(null, null);
        assertEquals(200, resp.getStatusCode().value());
    }

    @Test
    void concluirOrcamento_delegates() {
        var req = ConclusaoRequest.builder().ordem(1L).orcamento(2L).build();
        when(service.concluirOrcamento(1L,2L)).thenReturn(OrdemResponse.builder().status("FINALIZADA").valorTotal(BigDecimal.TEN).orcamentos(List.of()).build());
        ResponseEntity<OrdemResponse> resp = controller.concluirOrcamento(req);
        assertEquals(200, resp.getStatusCode().value());
    }
}
