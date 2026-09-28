package com.br.fiap.oficina.infrastructure.persistence;

import com.br.fiap.oficina.domain.entity.ItemServico;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemServicoRepository extends CrudRepository<ItemServico, Long> {
    List<ItemServico> findByExecutadoOrderByOrcamento_DataCriacaoAsc(boolean executado);
}