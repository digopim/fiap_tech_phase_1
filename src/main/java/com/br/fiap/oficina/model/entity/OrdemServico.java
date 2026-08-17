package com.br.fiap.oficina.model.entity;

import com.br.fiap.oficina.model.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "tb_ordem_servico")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class OrdemServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column
    private Status status;

    @Column
    private String observacoes;

    private LocalDateTime dataCriacao;
    private LocalDateTime dataInicio;
    private LocalDateTime dataConclusao;

    private Double valorTotal;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario responsavel;

    @OneToMany(mappedBy = "ordemServico", cascade = CascadeType.ALL)
    private List<Orcamento> orcamentos;
}
