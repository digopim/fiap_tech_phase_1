package com.br.fiap.oficina.model.repository;

import com.br.fiap.oficina.model.entity.Material;
import org.springframework.data.repository.CrudRepository;

public interface MaterialRepository extends CrudRepository<Material, Long> {
}