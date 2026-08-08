package com.br.fiap.oficina.model.entity;

import com.br.fiap.oficina.model.enums.Fluxo;
import com.br.fiap.oficina.model.enums.Origem;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Entity(name = "tb_caixa")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Caixa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "valor", nullable = false)
    private Double valor;

    @Enumerated
    @Column(name = "fluxo", nullable = false)
    private Fluxo fluxo;

    @Enumerated
    @Column(name = "origem", nullable = false)
    private Origem origem;

    @Column(name = "data", nullable = false)
    private LocalDateTime data;

}
