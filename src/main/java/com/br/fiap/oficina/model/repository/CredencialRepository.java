package com.br.fiap.oficina.model.repository;

import com.br.fiap.oficina.model.entity.Credencial;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CredencialRepository extends CrudRepository<Credencial, Long> {
    Optional<Credencial> findByLogin(String login);
}