package com.br.fiap.oficina.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name = "tb_servico")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column
    private String nome;

    @Column
    private String descricao;

    @Column(precision = 10, scale = 4)
    private BigDecimal custo;

    @Column(precision = 10, scale = 4)
    private BigDecimal valor;

    @Column
    private Integer duracao;

}
