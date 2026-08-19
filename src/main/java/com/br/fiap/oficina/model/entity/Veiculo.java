package com.br.fiap.oficina.model.entity;

import com.br.fiap.oficina.model.enums.TipoVeiculo;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "tb_veiculo")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column
    private String placa;

    @Column
    private String montadora;

    @Column
    private String modelo;

    @Column
    private String cor;

    @Column
    private TipoVeiculo tipo;

    @Column
    private String chassi;

}
