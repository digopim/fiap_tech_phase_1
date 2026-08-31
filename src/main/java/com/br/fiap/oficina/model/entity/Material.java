package com.br.fiap.oficina.model.entity;

import com.br.fiap.oficina.model.enums.Insumo;
import jakarta.persistence.*;
import lombok.*;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "custo", precision = 10, scale = 4)
    private BigDecimal custo;

    @Column(name = "valor", precision = 10, scale = 4)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private Insumo tipo;

}
