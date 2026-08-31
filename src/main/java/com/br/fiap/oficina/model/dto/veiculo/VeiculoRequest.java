package com.br.fiap.oficina.model.dto.veiculo;

import lombok.Builder;

@Builder
public record VeiculoRequest(Long id,
                             String placa,
                             String montadora,
                             String modelo,
                             String cor,
                             String tipo,
                             String chassi,
                             Integer anoFabricacao,
                             Integer quilometragem
) { }
