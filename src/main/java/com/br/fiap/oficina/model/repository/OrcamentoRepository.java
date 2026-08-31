package com.br.fiap.oficina.model.repository;

import com.br.fiap.oficina.model.entity.Orcamento;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrcamentoRepository extends CrudRepository<Orcamento, Long> {
    Optional<Orcamento> findFirstByIdOrderByDataCriacaoDesc(Long id);
}