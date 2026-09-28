package com.br.fiap.oficina.infrastructure.persistence;

import com.br.fiap.oficina.domain.entity.Orcamento;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrcamentoRepository extends CrudRepository<Orcamento, Long> {
    Optional<Orcamento> findFirstByIdOrderByDataCriacaoDesc(Long id);
}