package com.br.fiap.oficina.model.entity;

import com.br.fiap.oficina.model.enums.Insumo;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;


@Builder
@Entity(name = "tb_estoque")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Estoque {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    @JdbcTypeCode(SqlTypes.INTEGER)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "custo")
    private Double custo;

    @Column(name = "preco")
    private Double preco;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "minimo")
    private Integer minimo;

    @Enumerated
    @Column(name = "tipo", nullable = false)
    private Insumo tipo;

}
