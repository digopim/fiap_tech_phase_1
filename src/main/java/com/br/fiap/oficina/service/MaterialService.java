package com.br.fiap.oficina.service;

import com.br.fiap.oficina.model.dto.material.MaterialRequest;
import com.br.fiap.oficina.model.dto.material.MaterialResponse;
import com.br.fiap.oficina.model.entity.Estoque;
import com.br.fiap.oficina.model.entity.Material;
import com.br.fiap.oficina.model.enums.Insumo;
import com.br.fiap.oficina.model.repository.MaterialRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialService {

    private final MaterialRepository repository;
    private final EstoqueService estoqueService;

    public List<Material> buscarMateriaisPorIds(List<Long> ids) {
        return repository.findByIdIn(ids);
    }

    @Transactional
    public Material cadastrarMaterial(MaterialRequest request) {
        var material = Material.builder()
                .id(request.id())
                .nome(request.nome())
                .descricao(request.descricao())
                .valor(request.valor())
                .custo(request.custo())
                .tipo(request.tipo() != null ? Insumo.valueOf(request.tipo()) : null)
                .build();
        var salvo = repository.save(material);

        estoqueService.salvar(Estoque.builder()
                .material(salvo)
                .quantidade(0)
                .minimo(1)
                .build()
        );
        return salvo;
    }

    public void debitarMaterial(Long materialId, Integer quantidade) {
        estoqueService.debitar(materialId, quantidade);
    }

    public void deletarMaterial(Long id) {
        var material = repository.findById(id).orElseThrow();
        repository.delete(material);
    }

    public Material atualizarMaterial(Long id, MaterialRequest request) {
        var material = repository.findById(id).orElseThrow();
        material.setNome(request.nome());
        material.setDescricao(request.descricao());
        material.setValor(request.valor());
        material.setCusto(request.custo());
        material.setTipo(request.tipo() != null ? Insumo.valueOf(request.tipo()) : null);
        return repository.save(material);
    }

    public List<MaterialResponse> listarMateriais() {
        List<Material> materiais = (List<Material>) repository.findAll();
        return materiais.stream().map(MaterialResponse::fromEntity).toList();
    }

    public Material buscarMaterialPorId(Long id) {
        return repository.findById(id).orElseThrow();
    }

}
