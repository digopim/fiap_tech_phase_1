package com.br.fiap.oficina.model.repository;

import com.br.fiap.oficina.model.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    Usuario findByCpfCNPJ(String cpfCNPJ);
}