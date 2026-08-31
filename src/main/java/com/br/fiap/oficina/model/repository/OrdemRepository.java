package com.br.fiap.oficina.model.repository;

import com.br.fiap.oficina.model.entity.Ordem;
import com.br.fiap.oficina.model.enums.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

public interface OrdemRepository extends CrudRepository<Ordem, Long> {
    List<Ordem> findByCliente_CpfCNPJIgnoreCaseAndVeiculo_PlacaIgnoreCaseOrderByDataCriacaoAsc(@Nullable String cpfCNPJ, @Nullable String placa);

    List<Ordem> findByStatus(Status status);

    List<Ordem> findByVeiculo_PlacaIgnoreCaseOrderByDataCriacaoAsc(String placa);

    List<Ordem> findByCliente_CpfCNPJOrderByDataCriacaoDesc(String cpfCNPJ);

    Optional<Ordem> findFirstById(Long id);

    Optional<Ordem> findFirstByCliente_CpfCNPJAndVeiculo_PlacaAndDataConclusaoNullOrderByDataCriacaoAsc(String cpfCNPJ, String placa);

}