package com.br.fiap.oficina.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name = "tb_item_material")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class ItemMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "orcamento_id", nullable = false)
    private Orcamento orcamento;

    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(precision = 10, scale = 4, nullable = false)
    private BigDecimal precoUnitario;

    @Builder.Default
    @Column(name = "utilizado", nullable = false)
    private boolean utilizado = false;
}
