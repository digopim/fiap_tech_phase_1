package com.br.fiap.oficina.model.entity;

import com.br.fiap.oficina.model.enums.Perfil;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "tb_usuario")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column
    private String nome;

    @Column
    private String sobrenome;

    @Column
    private String email;

    @Column
    private String cpfCNPJ;

    @Column
    private String telefone;

    @Enumerated
    @Column(nullable = false)
    private Perfil perfil;
}
