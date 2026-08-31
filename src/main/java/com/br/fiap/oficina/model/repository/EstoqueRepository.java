package com.br.fiap.oficina.model.repository;

import com.br.fiap.oficina.model.entity.Estoque;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

public interface EstoqueRepository extends CrudRepository<Estoque, Long> {
    Estoque findByMaterial_Id(@NonNull Long id);


}