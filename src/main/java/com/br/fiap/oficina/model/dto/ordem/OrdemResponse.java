package com.br.fiap.oficina.model.dto.ordem;

import com.br.fiap.oficina.model.dto.orcamento.OrcamentoResponse;
import com.br.fiap.oficina.model.entity.Ordem;
import com.br.fiap.oficina.model.enums.Status;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OrdemResponse(Long id, String status,
                            String observacoes,
                            LocalDateTime dataCriacao,
                            LocalDateTime dataInicio,
                            LocalDateTime dataConclusao,
                            LocalDateTime dataPagamento,
                            BigDecimal valorTotal,
                            String responsavel,
                            List<OrcamentoResponse> orcamentos) {

    public static OrdemResponse from(Ordem ordem) {
        return OrdemResponse.builder()
                .id(ordem.getId())
                .status(obterStatus(ordem.getStatus()))
                .responsavel(ordem.getResponsavel().getNome())
                .dataCriacao(ordem.getDataCriacao())
                .dataInicio(ordem.getDataInicio())
                .dataConclusao(ordem.getDataConclusao())
                .dataPagamento(ordem.getDataPagamento())
                .valorTotal(ordem.getValorTotal())
                .observacoes(ordem.getObservacoes())
                .orcamentos(ordem.getOrcamentos().stream().map(OrcamentoResponse::from).toList())
                .build();
    }


    private static String obterStatus(Status status) {
        return switch (status) {
            case FINALIZADA -> "Aguardando Pagamento";
            case LIBERADA -> "Aguardando Resgate do Veiculo";
            default -> status.name();
        };
    }
}
