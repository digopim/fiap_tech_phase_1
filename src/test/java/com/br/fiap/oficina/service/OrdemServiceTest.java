package com.br.fiap.oficina.service;

import com.br.fiap.oficina.model.dto.orcamento.OrcamentoRequest;
import com.br.fiap.oficina.model.dto.ordem.Formulario;
import com.br.fiap.oficina.model.dto.ordem.OrdemRequest;
import com.br.fiap.oficina.model.dto.usuario.UsuarioRequest;
import com.br.fiap.oficina.model.dto.veiculo.VeiculoRequest;
import com.br.fiap.oficina.model.entity.*;
import com.br.fiap.oficina.model.enums.CheckList;
import com.br.fiap.oficina.model.enums.Perfil;
import com.br.fiap.oficina.model.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrdemServiceTest {

    @Mock
    private com.br.fiap.oficina.model.repository.OrdemRepository repository;
    @Mock
    private OrcamentoService orcamentoService;
    @Mock
    private VeiculoService veiculoService;
    @Mock
    private UsuarioService usuarioService;
    @Mock
    private CredencialService credencialService;
    @Mock
    private EstoqueService estoqueService;

    private OrdemService service;

    @BeforeEach
    void setup() {
        service = new OrdemService(repository, orcamentoService, veiculoService, usuarioService, credencialService, estoqueService);
    }

    @Test
    void criarOrdem_whenFormularioNotInspecao_throws() {
        var veiculoReq = VeiculoRequest.builder().placa("ABC1234").build();
        var clienteReq = UsuarioRequest.builder().nome("Cli").sobrenome("One").email("c@c.com").cpfCNPJ("111").build();
        var formulario = Formulario.builder().respostas(Map.of()).build();
        var request = OrdemRequest.builder().veiculo(veiculoReq).responsavel(1L).cliente(clienteReq).formulario(formulario).build();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> service.criarOrdem(request));
        assertEquals("CheckList Não Concluido", ex.getMessage());
    }

    @Test
    void criarOrdem_whenClienteNotExists_createsAndRegistersCredential() {
        var veiculoReq = VeiculoRequest.builder().placa("ABC1234").build();
        var clienteReq = UsuarioRequest.builder().nome("Cli").sobrenome("One").email("c@c.com").cpfCNPJ("111").build();
        var formulario = Formulario.builder().respostas(Map.of(CheckList.DOC, Boolean.TRUE)).build();
        var request = OrdemRequest.builder().veiculo(veiculoReq).responsavel(2L).cliente(clienteReq).formulario(formulario).build();

        Veiculo savedVeiculo = Veiculo.builder().id(10L).placa("ABC1234").build();
        Usuario responsavel = Usuario.builder().id(2L).nome("Resp").sobrenome("R").build();
        Usuario novoCliente = Usuario.builder().id(3L).nome("Cli").sobrenome("One").build();
        Ordem savedOrdem = Ordem.builder().id(100L).responsavel(responsavel).cliente(novoCliente).status(Status.RECEBIDA).veiculo(savedVeiculo).valorTotal(BigDecimal.ZERO).build();

        when(veiculoService.buscarVeiculoPorPlaca("ABC1234")).thenReturn(Optional.empty());
        when(veiculoService.salvarVeiculo(ArgumentMatchers.any())).thenReturn(savedVeiculo);
        when(usuarioService.buscarUsuarioPorId(2L)).thenReturn(responsavel);
        when(usuarioService.buscarUsuarioPorCpfCNPJ("111")).thenReturn(Optional.empty());
        when(usuarioService.salvarUsuario(ArgumentMatchers.any())).thenReturn(novoCliente);
        when(repository.save(ArgumentMatchers.any())).thenReturn(savedOrdem);

        var response = service.criarOrdem(request);

        assertNotNull(response);
        assertEquals(100L, response.id());
        assertEquals("Cli One", response.cliente());
        assertEquals("Resp", response.responsavel());
        verify(credencialService, times(1)).cadastrar(ArgumentMatchers.any(), eq(novoCliente));
    }

    @Test
    void incluirOrcamento_whenOrdemNotFound_throws() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(java.util.NoSuchElementException.class, () -> service.incluirOrcamento(1L, mock(OrcamentoRequest.class)));
    }

    @Test
    void incluirOrcamento_whenConcluirDiagnostico_setsAguardandoAprovacao() {
        Ordem os = obterOrdem().status(Status.RECEBIDA).valorTotal(BigDecimal.ZERO).build();
        var orcReq = mock(OrcamentoRequest.class);
        when(orcReq.concluirDiagnostico()).thenReturn(Boolean.TRUE);
        Orcamento novo = Orcamento.builder().id(7L).valor(BigDecimal.TEN).build();

        when(repository.findById(1L)).thenReturn(Optional.of(os));
        when(orcamentoService.criarOrcamento(orcReq, os)).thenReturn(novo);
        when(repository.save(os)).thenReturn(os);

        var resp = service.incluirOrcamento(1L, orcReq);
        assertEquals("AGUARDANDO_APROVACAO", resp.status());
    }

    @Test
    void concluirOrcamento_whenAllOrcamentosConcluded_finalizesOrder() {
        Orcamento orc = Orcamento.builder().id(11L).valor(BigDecimal.valueOf(42)).dataAprovacao(LocalDateTime.now()).dataConclusao(LocalDateTime.now()).build();
        Ordem os = obterOrdem().status(Status.EM_EXECUCAO).valorTotal(BigDecimal.ZERO).orcamentos(List.of(orc)).build();
        when(repository.findFirstById(1L)).thenReturn(Optional.of(os));
        when(repository.save(ArgumentMatchers.any())).thenAnswer(i -> i.getArgument(0));

        var resp = service.concluirOrcamento(1L, null);
        // FINALIZADA maps to "Aguardando Pagamento"
        assertEquals("Aguardando Pagamento", resp.status());
        assertEquals(BigDecimal.valueOf(42), resp.valorTotal());
    }

    @Test
    void registrarPagamento_whenNotFinalizada_throws() {
        Ordem os = obterOrdem().status(Status.EM_EXECUCAO).valorTotal(BigDecimal.TEN).build();
        when(repository.findById(1L)).thenReturn(Optional.of(os));
        assertThrows(IllegalArgumentException.class, () -> service.registrarPagamento(1L));
    }

    @Test
    void registrarPagamento_whenFinalizada_registersAndReturnsLiberada() {
        Ordem os = obterOrdem().status(Status.FINALIZADA).valorTotal(BigDecimal.valueOf(55)).build();
        when(repository.findById(1L)).thenReturn(Optional.of(os));
        when(repository.save(ArgumentMatchers.any())).thenAnswer(i -> i.getArgument(0));

        var resp = service.registrarPagamento(1L);
        assertEquals("Aguardando Resgate do Veiculo", resp.status());
        verify(estoqueService, times(1)).registrar(ArgumentMatchers.contains("Pagamento Ordem"), eq(BigDecimal.valueOf(55)), ArgumentMatchers.any(), ArgumentMatchers.any());
    }

    @Test
    void efetuarRetirada_whenNotLiberada_throws() {
        Ordem os = obterOrdem().status(Status.EM_EXECUCAO).build();
        when(repository.findByVeiculo_PlacaIgnoreCaseOrderByDataCriacaoAsc("ABC")).thenReturn(List.of(os));
        assertThrows(IllegalArgumentException.class, () -> service.efetuarRetirada("ABC"));
    }

    @Test
    void efetuarRetirada_whenLiberada_marksEntregue() {
        Ordem os = obterOrdem().status(Status.LIBERADA).build();
        when(repository.findByVeiculo_PlacaIgnoreCaseOrderByDataCriacaoAsc("ABC")).thenReturn(List.of(os));
        when(repository.save(ArgumentMatchers.any())).thenAnswer(i -> i.getArgument(0));

        var resps = service.efetuarRetirada("ABC");
        assertEquals(1, resps.size());
        assertEquals("ENTREGUE", resps.getFirst().status());
    }

    @Test
    void cancelarOrdem_setsCancelada() {
        Ordem os = obterOrdem().status(Status.RECEBIDA).build();
        when(repository.findById(1L)).thenReturn(Optional.of(os));
        when(repository.save(ArgumentMatchers.any())).thenAnswer(i -> i.getArgument(0));

        var resp = service.cancelarOrdem(1L);
        assertEquals("CANCELADA", resp.status());
        assertNotNull(resp.dataConclusao());
    }

    private Ordem.OrdemBuilder obterOrdem() {
        Usuario responsavel = Usuario.builder().nome("Teste").cpfCNPJ("0123456789").perfil(Perfil.ADMINISTRADOR).credencial(Credencial.builder().build()).build();
        Usuario cliente = Usuario.builder().nome("cliente").sobrenome("Teste").cpfCNPJ("987456321").perfil(Perfil.CLIENTE).credencial(Credencial.builder().build()).build();
        return Ordem.builder().id(1L).responsavel(responsavel).cliente(cliente);
    }
}
