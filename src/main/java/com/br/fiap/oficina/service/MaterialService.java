package com.br.fiap.oficina.service;

import com.br.fiap.oficina.model.dto.material.MaterialRequest;
import com.br.fiap.oficina.model.dto.material.MaterialResponse;
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

    private MaterialRepository repository;
    private EstoqueRepository estoqueRepository;

    public List<Material> buscarMateriaisPorIds(List<Long> ids) {
        return repository.findByIdIn(ids);
    }

    @Transactional
    public void cadastrarMaterial(MaterialRequest request) {
        var material = repository.save(Material.builder()
                .nome(request.nome())
                .descricao(request.descricao())
                .valor(request.valor())
                .custo(request.custo())
                .tipo(Insumo.valueOf(request.tipo()))
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

    public void atualizarMaterial(Long id, MaterialRequest request) {
        var material = repository.findById(id).orElseThrow();
        material.setNome(request.nome());
        material.setDescricao(request.descricao());
        material.setValor(request.valor());
        material.setCusto(request.custo());
        material.setTipo(Insumo.valueOf(request.tipo()));
        repository.save(material);
    }

    public List<MaterialResponse> listarMateriais() {
        List<Material> materiais = (List<Material>) repository.findAll();
        return materiais.stream().map(MaterialResponse::fromEntity).toList();
    }

    public Material buscarMaterialPorId(Long id) {
        return repository.findById(id).orElseThrow();
    }

}
