package com.br.fiap.oficina.model.entity;

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

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "minimo")
    private Integer minimo;

    @OneToOne
    @JoinColumn(name = "material_id", referencedColumnName = "id")
    private Material material;
}
