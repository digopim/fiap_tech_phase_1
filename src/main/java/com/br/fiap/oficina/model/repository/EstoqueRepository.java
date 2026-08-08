package com.br.fiap.oficina.model.repository;

import com.br.fiap.oficina.model.entity.Credencial;
import com.br.fiap.oficina.model.entity.Estoque;
import org.springframework.data.repository.CrudRepository;

public interface EstoqueRepository extends CrudRepository<Estoque, Long> {
}