package com.br.fiap.oficina.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "tb_servico")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column
    private String nome;

    @Column
    private String descricao;

    @Column
    private Double preco;

    @Column
    private Integer duracao;


}
