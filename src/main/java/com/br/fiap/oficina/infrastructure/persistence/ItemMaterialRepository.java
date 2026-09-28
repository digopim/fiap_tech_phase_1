package com.br.fiap.oficina.infrastructure.persistence;

import com.br.fiap.oficina.domain.entity.ItemMaterial;
import org.springframework.data.repository.CrudRepository;

public interface ItemMaterialRepository extends CrudRepository<ItemMaterial, Long> {
}