package com.br.fiap.oficina.model.dto.ordem;

import com.br.fiap.oficina.model.dto.orcamento.OrcamentoResponse;
import com.br.fiap.oficina.model.entity.Orcamento;
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
                            String tempoExecucao,
                            String responsavel,
                            String cliente,
                            List<OrcamentoResponse> orcamentos) {

    public static OrdemResponse from(Ordem ordem) {
        return OrdemResponse.builder()
                .id(ordem.getId())
                .status(obterStatus(ordem.getStatus()))
                .responsavel(ordem.getResponsavel().getNome())
                .cliente(ordem.getCliente().getNome() + " " + ordem.getCliente().getSobrenome())
                .dataCriacao(ordem.getDataCriacao())
                .dataInicio(ordem.getDataInicio())
                .dataConclusao(ordem.getDataConclusao())
                .dataPagamento(ordem.getDataPagamento())
                .valorTotal(ordem.getValorTotal())
                .observacoes(ordem.getObservacoes())
                .orcamentos(ordem.getOrcamentos().stream().map(OrcamentoResponse::from).toList())
                .tempoExecucao("Tempo restante estimado : " + calculaTempoExecucao(ordem.getOrcamentos()) + " Horas")
                .build();
    }


    private static String obterStatus(Status status) {
        return switch (status) {
            case FINALIZADA -> "Aguardando Pagamento";
            case LIBERADA -> "Aguardando Resgate do Veiculo";
            default -> status.name();
        };
    }

    private static Integer calculaTempoExecucao(List<Orcamento> orcamentos) {
        int tempoExecucao = 0;
        for (Orcamento orcamento : orcamentos) {
            tempoExecucao += orcamento.getServicos().stream().filter(i -> !i.isExecutado()).mapToInt(i -> i.getServico().getDuracao()).sum();
        }
        return tempoExecucao;
    }
}
