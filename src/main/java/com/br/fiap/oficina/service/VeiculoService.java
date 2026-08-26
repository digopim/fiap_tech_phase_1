package com.br.fiap.oficina.service;

import com.br.fiap.oficina.model.dto.veiculo.VeiculoRequest;
import com.br.fiap.oficina.model.entity.Veiculo;
import com.br.fiap.oficina.model.enums.TipoVeiculo;
import com.br.fiap.oficina.model.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VeiculoService {

    private final VeiculoRepository repository;

    public List<Veiculo> listarVeiculos() {
        return (List<Veiculo>) repository.findAll();
    }

    public Veiculo buscarVeiculoPorId(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public Veiculo buscarVeiculoPorPlaca(String placa) {
        return repository.findByPlaca(placa);
    }

    public Veiculo salvarVeiculo(VeiculoRequest request) {
        var veiculo = Veiculo.builder()
                .id(request.id())
                .placa(request.placa())
                .montadora(request.montadora())
                .modelo(request.modelo())
                .cor(request.cor())
                .tipo(request.tipo() != null ? TipoVeiculo.valueOf(request.tipo()) : null)
                .chassi(request.chassi())
                .anoFabricacao(request.anoFabricacao())
                .quilometragem(request.quilometragem())
                .build();
        return repository.save(veiculo);
    }

    public Veiculo atualizarVeiculo(Long id, VeiculoRequest request) {
        var existente = repository.findById(id).orElseThrow();
        existente.setPlaca(request.placa());
        existente.setMontadora(request.montadora());
        existente.setModelo(request.modelo());
        existente.setCor(request.cor());
        existente.setTipo(request.tipo() != null ? TipoVeiculo.valueOf(request.tipo()) : null);
        existente.setChassi(request.chassi());
        existente.setAnoFabricacao(request.anoFabricacao());
        existente.setQuilometragem(request.quilometragem());
        return repository.save(existente);
    }

    public void deletarVeiculo(Long id) {
        var veiculo = repository.findById(id).orElseThrow();
        repository.delete(veiculo);
    }

}
