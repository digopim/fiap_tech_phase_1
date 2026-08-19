package com.br.fiap.oficina.model.entity;

import com.br.fiap.oficina.model.enums.Insumo;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;


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

    @Column(name = "custo", precision = 10, scale = 4)
    private BigDecimal custo;

    @Column(name = "valor", precision = 10, scale = 4)
    private BigDecimal valor;

    @Enumerated
    @Column(name = "tipo", nullable = false)
    private Insumo tipo;

}
