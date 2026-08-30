package com.br.fiap.oficina.model.repository;

import com.br.fiap.oficina.model.entity.Veiculo;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VeiculoRepository extends CrudRepository<Veiculo, Long> {
    Optional<Veiculo> findFirstByPlacaOrderByIdDesc(String placa);
    
}