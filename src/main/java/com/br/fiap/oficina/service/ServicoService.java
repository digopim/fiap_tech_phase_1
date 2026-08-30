package com.br.fiap.oficina.service;

import com.br.fiap.oficina.model.dto.servico.ServicoRequest;
import com.br.fiap.oficina.model.entity.ItemServico;
import com.br.fiap.oficina.model.entity.Servico;
import com.br.fiap.oficina.model.repository.ItemServicoRepository;
import com.br.fiap.oficina.model.repository.ServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicoService {

    private final UsuarioService usuarioService;
    private final ServicoRepository repository;
    private final ItemServicoRepository itemServicoRepository;

    public Servico buscarServicoPorId(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public List<Servico> buscarServicosPorIds(List<Long> ids) {
        return repository.findByIdInAllIgnoreCase(ids);
    }

    public List<Servico> listarServicos() {
        return (List<Servico>) repository.findAll();
    }

    public Servico cadastrarServico(ServicoRequest request) {
        var servico = Servico.builder()
                .id(request.id())
                .nome(request.nome())
                .descricao(request.descricao())
                .custo(request.custo())
                .valor(request.valor())
                .duracao(request.duracao())
                .build();
        return repository.save(servico);
    }

    public Servico atualizarServico(Long id, ServicoRequest request) {
        var servico = repository.findById(id).orElseThrow();
        servico.setNome(request.nome());
        servico.setDescricao(request.descricao());
        servico.setCusto(request.custo());
        servico.setValor(request.valor());
        servico.setDuracao(request.duracao());
        return repository.save(servico);
    }

    public void deletarServico(Long id) {
        var servico = repository.findById(id).orElseThrow();
        repository.delete(servico);
    }

    public List<ItemServico> listarItemServicosEmAberto() {
        return itemServicoRepository.findByExecutadoOrderByOrcamento_DataCriacaoAsc(false);
    }

    public void concluirItemServico(Long itemServicoId, Long usuarioId) {
        var itemServico = itemServicoRepository.findById(itemServicoId).orElseThrow();
        itemServico.setExecutado(true);
        itemServico.setDataExecucao(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        itemServico.setExecutor(usuarioService.buscarUsuarioPorId(usuarioId));
        itemServicoRepository.save(itemServico);
    }

}
