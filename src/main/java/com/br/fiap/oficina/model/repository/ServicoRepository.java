package com.br.fiap.oficina.model.repository;

import com.br.fiap.oficina.model.entity.Servico;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface ServicoRepository extends CrudRepository<Servico, Long> {
    List<Servico> findByIdInAllIgnoreCase(Collection<Long> ids);
}