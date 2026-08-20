package com.br.fiap.oficina.service;
import com.br.fiap.oficina.model.entity.Servico;
import com.br.fiap.oficina.model.repository.ServicoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ServicoService {

    ServicoRepository repository;

    public Servico buscarServicoPorId(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public List<Servico> buscarServicosPorIds(List<Long> ids) {
        return repository.findByIdInAllIgnoreCase(ids);
    }

    public List<Servico> listarServicos() {
        return (List<Servico>) repository.findAll();
    }

    public void cadastrarServico(String nome, String descricao, BigDecimal preco, Integer duracao) {
        repository.save(Servico.builder()
                .nome(nome)
                .descricao(descricao)
                .valor(preco)
                .duracao(duracao)
                .build());
    }

    public void deletarServico(Long id) {
        var servico = repository.findById(id).orElseThrow();
        repository.delete(servico);
    }

    public void atualizarServico(Long id, String nome, String descricao, BigDecimal preco, Integer duracao) {
        var servico = repository.findById(id).orElseThrow();
        servico.setNome(nome);
        servico.setDescricao(descricao);
        servico.setValor(preco);
        servico.setDuracao(duracao);
        repository.save(servico);
    }

}
