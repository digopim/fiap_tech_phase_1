package com.br.fiap.oficina.model.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private Long id;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "minimo")
    private Integer minimo;

    @OneToOne
    @JoinColumn(name = "material_id", referencedColumnName = "id")
    private Material material;
}
