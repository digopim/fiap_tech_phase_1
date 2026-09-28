package com.br.fiap.oficina.infrastructure.persistence;

import com.br.fiap.oficina.domain.entity.Credencial;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CredencialRepository extends CrudRepository<Credencial, Long> {
    Optional<Credencial> findByLogin(String login);
}