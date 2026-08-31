package com.br.fiap.oficina.model.dto.servico;

import com.br.fiap.oficina.model.entity.ItemServico;
import lombok.Builder;

@Builder
public record ItemServicoResponse(Long id, Long orcamento, Long ordem, Long servicoId, String servico, Integer quantidade) {

    public static  ItemServicoResponse from(ItemServico itemServico) {
        return ItemServicoResponse.builder()
                .id(itemServico.getId())
                .orcamento(itemServico.getOrcamento().getId())
                .ordem(itemServico.getOrcamento().getOrdem().getId())
                .servicoId(itemServico.getServico().getId())
                .servico(itemServico.getServico().getNome())
                .quantidade(itemServico.getQuantidade())
                .build();
    }
}
