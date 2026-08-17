package com.br.fiap.oficina.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "tb_veiculo")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column
    private String placa;

    @Column
    private String marca;

    @Column
    private String modelo;

    @Column
    private String cor;

    @Column
    private String tipo;

    @Column
    private String chassi;

}
