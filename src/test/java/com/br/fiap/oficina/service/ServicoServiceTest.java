package com.br.fiap.oficina.service;

import com.br.fiap.oficina.model.dto.servico.ServicoRequest;
import com.br.fiap.oficina.model.entity.ItemServico;
import com.br.fiap.oficina.model.entity.Servico;
import com.br.fiap.oficina.model.entity.Usuario;
import com.br.fiap.oficina.model.repository.ItemServicoRepository;
import com.br.fiap.oficina.model.repository.ServicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServicoServiceTest {

    @Mock
    private UsuarioService usuarioService;
    @Mock
    private ServicoRepository repository;
    @Mock
    private ItemServicoRepository itemServicoRepository;

    private ServicoService service;

    @BeforeEach
    void setup() {
        service = new ServicoService(usuarioService, repository, itemServicoRepository);
    }

    @Test
    void buscarServicoPorId_returnsEntity() {
        when(repository.findById(1L)).thenReturn(Optional.of(Servico.builder().id(1L).nome("S").build()));
        var s = service.buscarServicoPorId(1L);
        assertEquals(1L, s.getId());
    }

    @Test
    void cadastrarServico_saves() {
        var req = ServicoRequest.builder().id(null).nome("X").descricao("D").custo(null).valor(null).duracao(10).build();
        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));
        var saved = service.cadastrarServico(req);
        assertEquals("X", saved.getNome());
    }

    @Test
    void listarItemServicosEmAberto_delegates() {
        when(itemServicoRepository.findByExecutadoOrderByOrcamento_DataCriacaoAsc(false)).thenReturn(List.of(new ItemServico()));
        var res = service.listarItemServicosEmAberto();
        assertEquals(1, res.size());
    }

    @Test
    void concluirItemServico_marksExecutedAndSaves() {
        var item = ItemServico.builder().id(10L).build();
        when(itemServicoRepository.findById(10L)).thenReturn(Optional.of(item));
        when(usuarioService.buscarUsuarioPorId(2L)).thenReturn(Usuario.builder().id(2L).nome("U").build());
        when(itemServicoRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        service.concluirItemServico(10L, 2L);
        assertTrue(item.isExecutado());
        assertNotNull(item.getDataExecucao());
        assertNotNull(item.getExecutor());
    }
}
