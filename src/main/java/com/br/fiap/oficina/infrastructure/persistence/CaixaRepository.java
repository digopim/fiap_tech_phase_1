package com.br.fiap.oficina.infrastructure.persistence;

import com.br.fiap.oficina.domain.entity.Caixa;
import com.br.fiap.oficina.domain.enums.Fluxo;
import com.br.fiap.oficina.domain.enums.Origem;
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