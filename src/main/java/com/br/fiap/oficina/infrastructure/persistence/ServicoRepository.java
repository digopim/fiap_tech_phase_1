package com.br.fiap.oficina.infrastructure.persistence;

import com.br.fiap.oficina.domain.entity.Servico;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface ServicoRepository extends CrudRepository<Servico, Long> {
    List<Servico> findByIdInAllIgnoreCase(Collection<Long> ids);
}