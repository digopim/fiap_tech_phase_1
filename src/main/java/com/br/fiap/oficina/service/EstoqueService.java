package com.br.fiap.oficina.service;

import com.br.fiap.oficina.model.dto.estoque.EstoqueRequest;
import com.br.fiap.oficina.model.enums.Insumo;
import com.br.fiap.oficina.model.exception.Indisponivel;
import com.br.fiap.oficina.model.repository.EstoqueRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;

import java.math.BigDecimal;

import static com.br.fiap.oficina.model.enums.Fluxo.SAIDA;
import static com.br.fiap.oficina.model.enums.Origem.ESTOQUE;

public class EstoqueService {

    EstoqueRepository repository;
    CaixaService caixaService;

    public void atualizarMinimo(EstoqueRequest request) {
        var item = repository.findByMaterial_Id(request.materialId());
        item.setMinimo(request.quantidade());
        repository.save(item);
    }

    @Transactional
    public boolean debitar(EstoqueRequest request) {
        var item = repository.findByMaterial_Id(request.materialId());
        if (item.getQuantidade() < request.quantidade()) {
            throw new Indisponivel("Quantidade insuficiente em estoque {}", item.getMaterial().getNome());
        }
        item.setQuantidade(item.getQuantidade() - request.quantidade());
        repository.save(item);
        return true;
    }

    @Scheduled(cron = "0 0 9 * * *") // Executa todos os dias à nove horas da manhã
    public void atualizar() {
        var estoque = repository.findAll();
        estoque.forEach(item -> {
            if(item.getQuantidade() < item.getMinimo()) {
                int quantidadePedido = calcularPedido(item.getQuantidade(), item.getMinimo(), item.getMaterial().getTipo());
                if(caixaService.registrar(item.getMaterial().getNome(),
                        item.getMaterial().getCusto().multiply(BigDecimal.valueOf(quantidadePedido)),
                        SAIDA, ESTOQUE)){
                    item.setQuantidade(item.getQuantidade() + quantidadePedido);
                    repository.save(item);
                }
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
