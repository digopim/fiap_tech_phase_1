package com.br.fiap.oficina.model.dto.veiculo;

public record VeiculoResponse(Long id,
                              String placa,
                              String montadora,
                              String modelo,
                              String cor,
                              String tipo,
                              String chassi,
                              Integer anoFabricacao,
                              Integer quilometragem
) { }
