package com.br.fiap.oficina.model.repository;

import com.br.fiap.oficina.model.entity.Caixa;
import com.br.fiap.oficina.model.enums.Fluxo;
import com.br.fiap.oficina.model.enums.Origem;
import org.jspecify.annotations.NonNull;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CaixaRepository extends CrudRepository<Caixa, Long> {

    List<Caixa> findByFluxo(Fluxo fluxo);

    List<Caixa> findByOrigem(Origem origem);

    List<Caixa> findByFluxoAndDataBetween(Fluxo fluxo, LocalDateTime dataInicio, LocalDateTime dataFim);

    List<Caixa> findByOrigemAndDataBetween(Origem origem, LocalDateTime dataInicio, LocalDateTime dataFim);

    List<Caixa> findByDataBetweenOrderByDataAsc(@NonNull LocalDateTime dataStart, @NonNull LocalDateTime dataEnd);

}