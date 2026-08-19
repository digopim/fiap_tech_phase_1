package com.br.fiap.oficina.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "tb_item_servico")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class ItemServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "orcamento_id", nullable = false)
    private Orcamento orcamento;

    @ManyToOne
    @JoinColumn(name = "servico_id", nullable = false)
    private Servico servico;

    @ManyToOne
    @JoinColumn(name = "executor_id")
    private Usuario executor;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(precision = 10, scale = 4, nullable = false)
    private BigDecimal precoUnitario;

    @Column(name = "data_execucao")
    private LocalDateTime dataExecucao;

    @Builder.Default
    @Column(name = "executado", nullable = false)
    private boolean executado = true;
}
