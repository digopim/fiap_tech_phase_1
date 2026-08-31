package com.br.fiap.oficina.model.repository;

import com.br.fiap.oficina.model.entity.ItemServico;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemServicoRepository extends CrudRepository<ItemServico, Long> {
    List<ItemServico> findByExecutadoOrderByOrcamento_DataCriacaoAsc(boolean executado);
}