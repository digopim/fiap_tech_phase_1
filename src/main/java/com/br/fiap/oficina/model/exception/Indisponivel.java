package com.br.fiap.oficina.model.exception;

public class Indisponivel extends RuntimeException {
    public Indisponivel(String message, String nome) {
        super(message);
    }
}
