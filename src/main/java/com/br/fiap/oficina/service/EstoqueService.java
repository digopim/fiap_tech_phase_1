package com.br.fiap.oficina.service;

import com.br.fiap.oficina.model.dto.estoque.EstoqueRequest;
import com.br.fiap.oficina.model.dto.estoque.EstoqueResponse;
import com.br.fiap.oficina.model.entity.Estoque;
import com.br.fiap.oficina.model.enums.Insumo;
import com.br.fiap.oficina.model.repository.EstoqueRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.br.fiap.oficina.model.enums.Fluxo.SAIDA;
import static com.br.fiap.oficina.model.enums.Origem.ESTOQUE;

@Service
@AllArgsConstructor
public class EstoqueService {

    private EstoqueRepository repository;
    private CaixaService caixaService;

    public void salvar(Estoque estoque) {
        repository.save(estoque);
    }

    @Transactional
    public void debitar(Long materialId, Integer quantidade) {
        var estoque = repository.findByMaterial_Id(materialId);
        estoque.setQuantidade(estoque.getQuantidade() - quantidade);
        repository.save(estoque);
    }

    public void atualizarMinimo(EstoqueRequest request) {
        var item = repository.findByMaterial_Id(request.materialId());
        item.setMinimo(request.quantidade());
        repository.save(item);
    }

    public List<EstoqueResponse> atual() {
        List<Estoque> retorno = (List<Estoque>) repository.findAll();
        return retorno.stream()
                .map(EstoqueResponse::from)
                .toList();
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
