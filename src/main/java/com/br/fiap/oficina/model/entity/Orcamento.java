package com.br.fiap.oficina.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "tb_orcamento")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Orcamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Builder.Default
    private LocalDateTime dataCriacao = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));

    private LocalDateTime dataAprovacao;

    private LocalDateTime dataConclusao;

    @Column(name = "valor", nullable = false, precision = 10, scale = 4)
    private BigDecimal valor;

    @ManyToOne
    private Ordem ordem;

    @Builder.Default
    @OneToMany(mappedBy = "orcamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemServico> servicos = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "orcamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemMaterial> materiais = new ArrayList<>();

}
