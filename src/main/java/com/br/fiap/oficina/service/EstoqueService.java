package com.br.fiap.oficina.service;

import com.br.fiap.oficina.model.dto.estoque.EstoqueRequest;
import com.br.fiap.oficina.model.dto.estoque.EstoqueResponse;
import com.br.fiap.oficina.model.enums.Insumo;
import com.br.fiap.oficina.model.exception.Indisponivel;
import com.br.fiap.oficina.model.repository.EstoqueRepository;

public class EstoqueService {

    EstoqueRepository repository;
    CaixaService caixaService;

    public EstoqueResponse cadastrar(EstoqueRequest request) {
        return EstoqueResponse.from(repository.save(request.toEntity()));
    }

    public EstoqueResponse debitar(EstoqueRequest request) {
        var item = repository.findById(request.id()).orElseThrow(() -> new RuntimeException("Item não encontrado em estoque"));
        if (item.getQuantidade() < request.quantidade()) {
            throw new Indisponivel("Quantidade insuficiente em estoque");
        }
        item.setQuantidade(item.getQuantidade() - request.quantidade());
        repository.save(item);
//        caixaService.registrar(request);
        return EstoqueResponse.from(item);
    }

//  Metodo de atualização de estoque a ser rodado via scheduler
    public void atualizar() {
        var estoque = repository.findAll();
        estoque.forEach(item -> {
            if(item.getQuantidade() < item.getMinimo()) {
                int quantidadePedido = calcularPedido(item.getQuantidade(), item.getMinimo(), item.getTipo());
//                caixaService.registrar()
            }
        });
    }

    private int calcularPedido(int quantidadeAtual, int quantidadeMinima, Insumo tipo) {
        return switch (tipo) {
            case PECA -> (quantidadeMinima * 2) - quantidadeAtual;
            case MATERIAL -> (quantidadeMinima * 3) - quantidadeAtual;
            case FERRAMENTA, ALIMENTO -> quantidadeMinima - quantidadeAtual;
        };
    }
}
