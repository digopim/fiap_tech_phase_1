package com.br.fiap.oficina.model.repository;

import com.br.fiap.oficina.model.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    Optional<Usuario> findByCpfCNPJ(String cpfCNPJ);
}