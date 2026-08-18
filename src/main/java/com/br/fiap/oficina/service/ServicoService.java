package com.br.fiap.oficina.service;
import com.br.fiap.oficina.model.entity.Servico;
import com.br.fiap.oficina.model.repository.ServicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService {

    ServicoRepository repository;

    public List<Servico> listarServicos() {
        return (List<Servico>) repository.findAll();
    }

    public void cadastrarServico(String nome, String descricao, Double preco, Integer duracao) {
        var servico = Servico.builder()
                .nome(nome)
                .descricao(descricao)
                .preco(preco)
                .duracao(duracao)
                .build();
        repository.save(servico);
    }

    public void deletarServico(Long id) {
        var servico = repository.findById(id).orElseThrow();
        repository.delete(servico);
    }

    public void atualizarServico(Long id, String nome, String descricao, Double preco, Integer duracao) {
        var servico = repository.findById(id).orElseThrow();
        servico.setNome(nome);
        servico.setDescricao(descricao);
        servico.setPreco(preco);
        servico.setDuracao(duracao);
        repository.save(servico);
    }

}
