package com.br.fiap.oficina.infrastructure.persistence;

import com.br.fiap.oficina.domain.entity.Estoque;
import org.jspecify.annotations.NonNull;
import org.springframework.data.repository.CrudRepository;

public interface EstoqueRepository extends CrudRepository<Estoque, Long> {
    Estoque findByMaterial_Id(@NonNull Long id);


}