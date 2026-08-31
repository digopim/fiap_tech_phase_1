package com.br.fiap.oficina.service;

import com.br.fiap.oficina.model.dto.admin.CaixaResponse;
import com.br.fiap.oficina.model.dto.admin.Panorama;
import com.br.fiap.oficina.model.dto.estoque.EstoqueResponse;
import com.br.fiap.oficina.model.dto.ordem.OrdemResponse;
import com.br.fiap.oficina.model.entity.Caixa;
import com.br.fiap.oficina.model.enums.Fluxo;
import com.br.fiap.oficina.model.enums.Origem;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdminService {

    private OrdemService ordemService;
    private EstoqueService estoqueService;

    public Panorama obterPanorama(LocalDateTime inicio, LocalDateTime fim) {
        List<Caixa> registros = estoqueService.obterTodosPorData(inicio, fim);

        Map<Origem, List<CaixaResponse>> custos = agruparPorOrigem(registros, Fluxo.SAIDA);
        Map<Origem, List<CaixaResponse>> receitas = agruparPorOrigem(registros, Fluxo.ENTRADA);

        BigDecimal custosTotal = custos.values().stream().flatMap(List::stream).map(CaixaResponse::valor).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal receitasTotal = receitas.values().stream().flatMap(List::stream).map(CaixaResponse::valor).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal saldoTotal = receitasTotal.subtract(custosTotal);

        return Panorama.builder()
                .custos(custos)
                .receitas(receitas)
                .totalCustos(custosTotal)
                .totalReceitas(receitasTotal)
                .saldo(saldoTotal)
                .build();
    }

    public List<EstoqueResponse> obterEstoqueAtual() {
        return estoqueService.atual();
    }

    public List<OrdemResponse> obterOrdensPorCpfCNPJ(String cpfCNPJ) {
        return ordemService.obterOrdensPorCpfCNPJ(cpfCNPJ);
    }

    public List<OrdemResponse> obterOrdensPorPlaca(String placa) {
        return ordemService.obterOrdensPorPlaca(placa);
    }

    private Map<Origem, List<CaixaResponse>> agruparPorOrigem(List<Caixa> registros, Fluxo fluxo){
        return registros.stream().filter(r -> fluxo.equals(r.getFluxo()))
                .collect(Collectors.groupingBy(Caixa::getOrigem,
                        Collectors.mapping(CaixaResponse::from, Collectors.toList())));
    }
}
