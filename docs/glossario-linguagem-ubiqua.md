# Glossário de Linguagem Ubíqua

Este glossário centraliza os termos do domínio da oficina automotiva usados por clientes, colaboradores, administradores e time de desenvolvimento. A ideia é padronizar a comunicação entre negócio e tecnologia, evitando ambiguidades na modelagem de processos, regras e APIs.

## 1. Visão geral

A linguagem ubíqua da plataforma de oficina deve refletir o fluxo real da operação: cadastro de clientes e veículos, abertura de ordem de serviço, diagnóstico, aprovação de orçamento, execução de serviços, controle de materiais, pagamento e liberação do veículo para retirada.

## 2. Termos de domínio

| Termo | Significado | Contexto de uso | Sinônimos / variações |
|---|---|---|---|
| Cliente | Pessoa física ou jurídica que utiliza os serviços da oficina ou possui veículo em manutenção. | Cadastro, histórico, autorização e retirada. | Consumidor, titular, proprietário |
| Colaborador | Pessoa que realiza atividades operacionais na oficina. | Recebimento, diagnóstico, execução e acompanhamento. | Funcionário, operador, técnico |
| Administrador | Usuário com permissão para gerir pessoas, acessos e operação da plataforma. | Configuração, segurança, supervisão. | Gestor, supervisor |
| Usuário | Entidade autenticada no sistema, com identidade e perfil. | Login, autorização, rastreabilidade. | Conta, credencial, operador |
| Perfil | Papel funcional que define permissões e acessos do usuário. | Autenticação e autorização. | Papel, role, permissão |
| Veículo | Automóvel associado a um cliente e sujeito a manutenção ou serviço. | Cadastro, consulta, diagnóstico, ordem. | Carro, automóvel, unidade |
| Placa | Identificador do veículo. | Busca, consulta e identificação rápida. | Chassi relacionado, código de identificação visual |
| Ordem de Serviço (OS) | Registro do atendimento do veículo e do cliente dentro da oficina. | Recebimento, diagnóstico, execução, pagamento. | Solicitação de serviço, atendimento, ordem |
| Status da Ordem | Estado atual da OS ao longo do ciclo operacional. | Acompanhamento e controle. | Situação, fase, etapa |
| Diagnóstico | Análise inicial do problema ou condição do veículo. | Recebimento e avaliação técnica. | Laudo inicial, inspeção, avaliação |
| Orçamento | Proposta de serviços, materiais e valores para o cliente. | Aprovação, acompanhamento do serviço. | Cotação, proposta |
| Aprovação do Orçamento | Confirmação do cliente ou responsável para seguir com a execução. | Fluxo de execução e autorização. | Autorização, aceite, concordância |
| Serviço | Atividade executada pela oficina para manter ou corrigir o veículo. | Catalogação e execução. | Reparo, manutenção, tarefa |
| Material | Insumo utilizado na execução do serviço. | Estoque, consumo, reposição. | Peça, componente, insumo |
| Estoque | Quantidade disponível de materiais e peças. | Controle de disponibilidade. | Inventário, almoxarifado |
| Estoque Baixo | Situação em que o material se aproxima do mínimo necessário. | Planejamento e reposição. | Reposição pendente, falta iminente |
| Pagamento | Atividade de quitação do serviço realizado. | Financeiro, liberação, entrega. | Cobrança, fechamento, quitação |
| Recebimento | Entrada do veículo na oficina e início do processo. | Fluxo operacional. | Entrada, check-in |
| Execução | Realização do serviço ou conjunto de serviços na OS. | Operação da oficina. | Atendimento, prestação |
| Entrega | Liberação do veículo ao cliente após conclusão e pagamento. | Finalização do atendimento. | Retirada, devolução |
| Retirada | Ação de o cliente buscar o veículo após a conclusão do serviço. | Atendimento final e encerramento. | Devolução, liberação |
| Autenticação | Processo de verificar a identidade do usuário. | Login e acesso ao sistema. | Login, validação de identidade |
| Autorização | Processo de decidir se o usuário pode acessar um recurso ou função. | Proteção de endpoints e regras de negócio. | Permissão, acesso, autorização de ação |
| Token JWT | Credencial digital usada para autenticar requisições. | Segurança da API. | Access token, bearer token |
| Sessão | Período de acesso de um usuário autenticado. | Uso Web e contexto de segurança. | Login ativo, conexão autenticada |
| Auditoria | Registro de ações relevantes para rastreabilidade. | Segurança e acompanhamento de alterações. | Histórico, log de operação |
| Histórico | Registro cronológico de alterações e eventos relevantes. | Status, movimentações e rastreabilidade. | Log, trilha de eventos |

## 3. Fluxos do domínio

### 3.1 Recebimento e diagnóstico

- O cliente entrega o veículo à oficina.
- O colaborador registra a ordem de serviço.
- É realizado o diagnóstico do problema ou condição do automóvel.
- O status da ordem avança para análise técnica.

### 3.2 Orçamento e aprovação

- O colaborador cria o orçamento com serviços e materiais previstos.
- O cliente recebe e valida a proposta.
- A aprovação do orçamento autoriza a execução do trabalho.

### 3.3 Execução e pagamento

- Os serviços são executados conforme a ordem.
- Os materiais consumidos são controlados pelo estoque.
- O pagamento é registrado antes ou no momento da entrega.

### 3.4 Liberação e retirada

- A ordem é concluída.
- O veículo é liberado para retirada.
- O cliente retira o automóvel com o status final registrado.

## 4. Regras de linguagem

Para manter a linguagem ubíqua consistente, os termos abaixo devem ser usados conforme o significado a seguir:

- Sempre usar “ordem de serviço” em vez de “ticket”, “chamado” ou “pedido”, quando o contexto for o fluxo da oficina.
- Usar “orçamento” para proposta de valor e “serviço” para atividade operacional.
- Usar “material” para insumo e “estoque” para disponibilidade e controle.
- Usar “cliente” para quem possui o veículo e “colaborador” para quem executa o atendimento.
- Usar “autenticação” para validação de identidade e “autorização” para acesso a recursos e ações.
- Usar “entrega” e “retirada” com clareza: entrega é a conclusão do atendimento, retirada é a ação do cliente em buscar o veículo.

## 5. Exemplos de uso

- “O cliente aprovou o orçamento da ordem de serviço 1042.”
- “O colaborador registrou o diagnóstico e atualizou o status da OS.”
- “O material de consumo foi baixado do estoque após a execução do serviço.”
- “O administrador revisou o perfil do usuário para conceder autorização de acesso.”
- “A retirada do veículo só pode ocorrer após a liberação e o pagamento.”

## 6. Benefícios

A adoção da linguagem ubíqua:

- reduz ambiguidade entre negócio e tecnologia;
- melhora a clareza de requisitos e regras;
- facilita a modelagem de entidades, status, APIs e fluxos;
- fortalece a comunicação entre analistas, desenvolvedores, clientes e operação.

## 7. Referência

Este glossário deve servir como base para nomes de entidades, campos, endpoints, eventos e regras de negócio da aplicação de oficina automotiva.
