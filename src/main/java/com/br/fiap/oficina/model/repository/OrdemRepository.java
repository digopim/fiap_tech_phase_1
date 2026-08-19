package com.br.fiap.oficina.model.repository;

import com.br.fiap.oficina.model.entity.Ordem;
import org.springframework.data.repository.CrudRepository;

public interface OrdemRepository extends CrudRepository<Ordem, Long> {
}