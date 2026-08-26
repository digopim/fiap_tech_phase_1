package com.br.fiap.oficina.service;

import com.br.fiap.oficina.model.dto.veiculo.VeiculoRequest;
import com.br.fiap.oficina.model.entity.Veiculo;
import com.br.fiap.oficina.model.enums.TipoVeiculo;
import com.br.fiap.oficina.model.repository.VeiculoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeiculoService {

    private VeiculoRepository repository;

    public List<Veiculo> listarVeiculos() {
        return (List<Veiculo>) repository.findAll();
    }

    public Veiculo buscarVeiculoPorId(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public Veiculo buscarVeiculoPorPlaca(String placa) {
        return repository.findByPlaca(placa);
    }

    public void salvarVeiculo(VeiculoRequest request) {
        var veiculo = Veiculo.builder()
                .id(request.id())
                .placa(request.placa())
                .montadora(request.montadora())
                .modelo(request.modelo())
                .cor(request.cor())
                .tipo(TipoVeiculo.valueOf(request.tipo()))
                .chassi(request.chassi())
                .anoFabricacao(request.anoFabricacao())
                .quilometragem(request.quilometragem())
                .build();
        repository.save(veiculo);
    }

    public void deletarVeiculo(Long id) {
        var veiculo = repository.findById(id).orElseThrow();
        repository.delete(veiculo);
    }

}
