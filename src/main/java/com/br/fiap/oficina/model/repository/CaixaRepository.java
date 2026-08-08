package com.br.fiap.oficina.model.repository;

import com.br.fiap.oficina.model.entity.Caixa;
import com.br.fiap.oficina.model.entity.Credencial;
import org.springframework.data.repository.CrudRepository;

public interface CaixaRepository extends CrudRepository<Caixa, Long> {
}