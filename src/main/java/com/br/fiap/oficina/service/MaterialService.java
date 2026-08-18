package com.br.fiap.oficina.service;

import com.br.fiap.oficina.model.entity.Estoque;
import com.br.fiap.oficina.model.entity.Material;
import com.br.fiap.oficina.model.enums.Insumo;
import com.br.fiap.oficina.model.repository.EstoqueRepository;
import com.br.fiap.oficina.model.repository.MaterialRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialService {

    MaterialRepository repository;
    EstoqueRepository estoqueRepository;

    @Transactional
    public void cadastrarMaterial(String nome, String descricao, Double valor, Double custo, Insumo tipo) {
        var material = repository.save(Material.builder()
                .nome(nome)
                .descricao(descricao)
                .valor(valor)
                .custo(custo)
                .tipo(tipo)
                .build());

        estoqueRepository.save(Estoque.builder()
                        .material(material)
                        .quantidade(0)
                        .minimo(1)
                        .build()
        );
    }

    public void deletarMaterial(Long id) {
        var material = repository.findById(id).orElseThrow();
        repository.delete(material);
    }

    public void atualizarMaterial(Long id, String nome, String descricao, Double valor, Double custo, Insumo tipo) {
        var material = repository.findById(id).orElseThrow();
        material.setNome(nome);
        material.setDescricao(descricao);
        material.setValor(valor);
        material.setCusto(custo);
        material.setTipo(tipo);
        repository.save(material);
    }

    public List<Material> listarMateriais() {
        return (List<Material>) repository.findAll();
    }

}
