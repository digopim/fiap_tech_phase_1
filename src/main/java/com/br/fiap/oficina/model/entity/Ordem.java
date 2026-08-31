package com.br.fiap.oficina.model.entity;

import com.br.fiap.oficina.model.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "tb_ordem")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Ordem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column
    private Status status;

    @Column
    private String observacoes;

    @Column
    @Builder.Default
    private LocalDateTime dataCriacao = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
    private LocalDateTime dataInicio;
    private LocalDateTime dataConclusao;
    private LocalDateTime dataPagamento;

    @Column(name = "valor_total", nullable = false, precision = 10, scale = 4)
    private BigDecimal valorTotal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "responsavel_id")
    private Usuario responsavel;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Usuario cliente;

    @ManyToOne
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;


    @Builder.Default
    @OneToMany(mappedBy = "ordem", cascade = CascadeType.ALL, fetch =  FetchType.EAGER)
    private List<Orcamento> orcamentos = new ArrayList<>();
}
