package com.br.fiap.oficina.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CheckList {

    CLI("Cliente cadastrado?"),
    VEI("Veiculo cadastrado?"),
    DOC("Documentação e Objetos Pessoais: O veículo está livre de objetos pessoais de valor e com os documentos necessários?"),
    LAT("Avarias na Lataria e Pintura: O veículo apresenta riscos, amassados ou danos pré-existentes na lataria/pintura?"),
    VID("Integridade dos Vidros e Espelhos: Os vidros, para-brisa e retrovisores estão livres de trincas ou quebras?"),
    PNE("Estado dos Pneus e Estepe: Os pneus (incluindo o estepe) estão em bom estado de conservação e calibrados?"),
    EME("Kit de Emergência: O veículo possui macaco, chave de roda e triângulo de sinalização em condições de uso?"),
    FAR("Iluminação Externa: Os faróis, lanternas, luzes de freio e setas estão funcionando perfeitamente?"),
    PAI("Painel e Luzes de Alerta: Há alguma luz de avaria (Injeção, ABS, Airbag, Óleo) acesa no painel ao ligar a chave?"),
    FLU("Nível de Fluidos de Emergência: Os níveis do fluido de freio e óleo do motor estão dentro do limite visível recomendado?"),
    VAZ("Vazamentos Visíveis: Há indícios de vazamento aparente de óleo, água ou fluido sob o veículo estacionado?"),
    ACE("Acessórios e Equipamentos: O rádio/multimídia, tapetes e acessórios internos estão presentes e em bom estado?");

    final String pergunta;

    public static CheckList getCheckList(String pergunta) {
        for (CheckList c : CheckList.values()) {
            if (c.pergunta.equals(pergunta)) {
                return c;
            }
        }
        throw new IllegalArgumentException("CheckList não encontrado para a pergunta: " + pergunta);
    }
}
