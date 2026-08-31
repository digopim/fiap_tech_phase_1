package com.br.fiap.oficina.service;

import com.br.fiap.oficina.model.dto.orcamento.OrcamentoRequest;
import com.br.fiap.oficina.model.entity.*;
import com.br.fiap.oficina.model.repository.OrcamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrcamentoServiceTest {

    @Mock
    private OrcamentoRepository repository;
    @Mock
    private ServicoService servicoService;
    @Mock
    private MaterialService materialService;
    @Mock
    private EstoqueService estoqueService;

    private OrcamentoService service;

    @BeforeEach
    void setup() {
        service = new OrcamentoService(repository, servicoService, materialService, estoqueService);
    }

    @Test
    void criarOrcamento_newOrcamento_calculatesTotal() {
        Ordem ordem = Ordem.builder().id(1L).build();
        OrcamentoRequest req = new OrcamentoRequest(null, Map.of(1L, 2), Map.of(2L, 3), false);

        Servico serv1 = Servico.builder().id(1L).valor(BigDecimal.TEN).build(); // 10 * 2 = 20
        Material mat2 = Material.builder().id(2L).valor(BigDecimal.valueOf(5)).build(); // 5 * 3 = 15

        when(servicoService.buscarServicoPorId(1L)).thenReturn(serv1);
        when(materialService.buscarMaterialPorId(2L)).thenReturn(mat2);
        when(repository.save(ArgumentMatchers.any())).thenAnswer(i -> i.getArgument(0));

        Orcamento saved = service.criarOrcamento(req, ordem);

        assertEquals(BigDecimal.valueOf(35).stripTrailingZeros(), saved.getValor().stripTrailingZeros());
        assertEquals(1, saved.getServicos().size());
        assertEquals(1, saved.getMateriais().size());
    }

    @Test
    void criarOrcamento_existingOrcamento_updatesQuantities() {
        Ordem ordem = Ordem.builder().id(2L).build();
        Orcamento existing = Orcamento.builder().id(10L).ordem(ordem).build();
        ItemServico its = ItemServico.builder().id(100L).orcamento(existing).servico(Servico.builder().id(1L).valor(BigDecimal.TEN).build()).quantidade(1).precoUnitario(BigDecimal.TEN).build();
        ItemMaterial itm = ItemMaterial.builder().id(200L).orcamento(existing).material(Material.builder().id(2L).valor(BigDecimal.valueOf(2)).build()).quantidade(1).precoUnitario(BigDecimal.valueOf(2)).build();
        existing.getServicos().add(its);
        existing.getMateriais().add(itm);
        ordem.getOrcamentos().add(existing);

        OrcamentoRequest req = new OrcamentoRequest(10L, Map.of(1L, 5), Map.of(2L, 4), false);

        when(servicoService.buscarServicoPorId(1L)).thenReturn(its.getServico());
        when(materialService.buscarMaterialPorId(2L)).thenReturn(itm.getMaterial());
        when(repository.save(ArgumentMatchers.any())).thenAnswer(i -> i.getArgument(0));

        Orcamento saved = service.criarOrcamento(req, ordem);

        assertEquals(5, saved.getServicos().getFirst().getQuantidade());
        assertEquals(4, saved.getMateriais().getFirst().getQuantidade());
    }

    @Test
    void aprovarOrcamento_whenApproved_marksMaterialsUsedAndDebits() {
        Material material = Material.builder().id(3L).valor(BigDecimal.ONE).build();
        ItemMaterial item = ItemMaterial.builder().id(300L).material(material).quantidade(2).precoUnitario(BigDecimal.ONE).build();
        Orcamento orc = Orcamento.builder().id(20L).build();
        orc.getMateriais().add(item);

        when(repository.findById(20L)).thenReturn(Optional.of(orc));
        when(repository.save(ArgumentMatchers.any())).thenAnswer(i -> i.getArgument(0));

        service.aprovarOrcamento(20L, true);

        assertTrue(item.isUtilizado());
        verify(materialService, times(1)).debitarMaterial(3L, 2);
        assertNotNull(orc.getDataAprovacao());
    }

    @Test
    void aprovarOrcamento_whenNotApproved_setsConclusao() {
        Orcamento orc = Orcamento.builder().id(21L).build();
        when(repository.findById(21L)).thenReturn(Optional.of(orc));
        when(repository.save(ArgumentMatchers.any())).thenAnswer(i -> i.getArgument(0));

        service.aprovarOrcamento(21L, false);

        assertNotNull(orc.getDataConclusao());
    }

    @Test
    void concluirOrcamento_whenNotAllExecuted_throws() {
        Orcamento orc = Orcamento.builder().id(30L).build();
        ItemServico item = ItemServico.builder().id(400L).executado(false).dataExecucao(null).executor(null).build();
        orc.getServicos().add(item);
        when(repository.findById(30L)).thenReturn(Optional.of(orc));

        assertThrows(IllegalArgumentException.class, () -> service.concluirOrcamento(30L));
    }

    @Test
    void concluirOrcamento_whenAllExecuted_setsDataConclusao() {
        Orcamento orc = Orcamento.builder().id(31L).build();
        ItemServico item = ItemServico.builder().id(401L).executado(true).dataExecucao(LocalDateTime.now()).build();
        orc.getServicos().add(item);
        when(repository.findById(31L)).thenReturn(Optional.of(orc));
        when(repository.save(ArgumentMatchers.any())).thenAnswer(i -> i.getArgument(0));

        service.concluirOrcamento(31L);

        assertNotNull(orc.getDataConclusao());
    }
}
