package com.br.fiap.oficina.model.entity;

import com.br.fiap.oficina.model.enums.Insumo;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;


@Builder
@Entity(name = "tb_material")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Material {
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

    @Column(name = "valor")
    private Double valor;

    @Enumerated
    @Column(name = "tipo", nullable = false)
    private Insumo tipo;

}
