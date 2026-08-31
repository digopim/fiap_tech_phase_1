package com.br.fiap.oficina.model.repository;

import com.br.fiap.oficina.model.entity.Material;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface MaterialRepository extends CrudRepository<Material, Long> {
    List<Material> findByIdIn(Collection<Long> ids);
}