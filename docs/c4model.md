# Documentação de Arquitetura - Java 21/25 + Spring Boot 3/4

## Visão Geral

| Item | Descrição |
|------|------------|
| Objetivo | Disponibilizar uma plataforma de gestão de oficina automotiva com controle de clientes, veículos, ordens de serviço, materiais, serviços, pagamentos e autorização de retirada, com segurança e rastreabilidade por identidade e permissões. |
| Público-alvo | Clientes, colaboradores da oficina, fornecedores, administradores e responsáveis pela operação da manutenção automotiva. |
| Tecnologias | Java 21/25, Spring Boot 3/4, Spring Web MVC, Spring Security, Spring Data JPA, PostgreSQL, Liquibase, JWT, Actuator, Bean Validation, Lombok, Maven/Gradle; extensível para Redis, Kafka e integrações HTTP corporativas. |
| Responsável | Equipe de arquitetura e desenvolvimento do projeto de oficina. |
| Status | Em desenvolvimento/validação de arquitetura funcional e operacional. |

### Problema de negócio

A oficina precisa centralizar o registro de clientes, veículos, diagnósticos, orçamentos, execução e pagamento de serviços, reduzindo retrabalho manual, padronizando o fluxo de aprovação e garantindo controle de acesso por perfil. A solução elimina a dependência de processos dispersos em planilhas, e-mails e registros manuais.

### Funcionalidades principais

- Cadastro e consulta de usuários, clientes e colaboradores.
- Registro de veículos e associação por CPF/CNPJ e placa.
- Criação de ordens de serviço e diagnóstico inicial.
- Inclusão, aprovação e conclusão de orçamentos.
- Controle de status operacional da ordem de serviço.
- Gestão de materiais, serviços e estoque.
- Autenticação e autorização por JWT por perfil.
- Pagamento e liberação/entrega do veículo.

### Escopo

O escopo da solução abrange o backend de negócio da oficina, o acesso HTTP para clientes e operadores, a persistência relacional para a operação do dia a dia e a segurança do acesso. Integrações com serviços externos são tratadas como dependências de infraestrutura e APIs complementares, não como parte do domínio principal.

## Modelo C4

| Nível | Objetivo |
|--------|----------|
| Contexto | Relação com usuários e sistemas externos |
| Contêineres | Aplicações e infraestrutura |
| Componentes | Módulos internos |
| Código | Estrutura lógica do código |

## C4 Nível 1 - Contexto

### Participantes

| Elemento | Tipo | Responsabilidade | Interação |
|----------|------|------------------|-----------|
| Cliente | Pessoa | Solicita orçamento, acompanha ordem e retira veículo | Acessa API REST e autentica-se |
| Colaborador | Pessoa | Recebe veículo, gera diagnósticos, executa serviços | Cria e atualiza ordens e orçamentos |
| Administrador | Pessoa | Gerencia usuários, permissões e operacionais | Acesso administrativo e supervisão |
| Oficina API | Sistema | Orquestra fluxo de negócio e autenticação | Expõe endpoints REST e acessa dados |
| PostgreSQL | Sistema de dados | Persistência de clientes, veículos, serviços e ordens | Armazena transações e estados operacionais |
| Sistema externo | Sistema | Pagamento, notificação, e-mail ou CRM | Consumo ou integração via HTTP |

### Fluxos

| Origem | Destino | Ação |
|--------|---------|------|
| Cliente/Colaborador | Oficina API | Autenticação e consumo de endpoints REST |
| Oficina API | PostgreSQL | Persistência de dados e consultas transacionais |
| Colaborador | Oficina API | Criação ou atualização de ordem de serviço |
| Oficina API | Sistema externo | Notificação, confirmação ou integração relacionada |
| Administrador | Oficina API | Operação administrativa e acompanhamento |

### Regras

| Regra | Descrição |
|-------|-----------|
| R1 | Acesso ao sistema exige autenticação por credencial e token JWT. |
| R2 | Permissões são definidas por perfil: cliente, colaborador, administrador. |
| R3 | Ordens de serviço seguem ciclo de recebimento, diagnóstico, aprovação, execução, pagamento e entrega. |
| R4 | Dados de clientes e veículos devem ser tratados conforme requisitos de privacidade e proteção pessoal. |
| R5 | Dados críticos ficam em bancos e segredos em gestão segura fora do código-fonte. |

### Resumo arquitetural

- Sistema monolítico modular em Spring Boot para gestão da oficina.
- API REST expõe operações de negócio e autenticação.
- Persistência relacional em PostgreSQL com transações e validações.
- Segurança baseada em JWT e perfis de acesso.
- Fluxo operacional centralizado em ordens e orçamentos.

## C4 Nível 2 - Contêineres

| Contêiner | Tecnologia | Responsabilidade | Comunicação |
|-----------|------------|------------------|-------------|
| Aplicação backend | Java 21/25 + Spring Boot 3/4 | Orquestração das regras de negócio e exposição REST | HTTP/JSON, JDBC, JWT |
| Segurança | Spring Security + JWT | Autenticação, autorização e filtro de requisições | HTTP/Headers, Bearer Token |
| Persistência | PostgreSQL | Armazenamento principal de entidades e histórico operacional | JDBC/TLS |
| Migração de schema | Liquibase | Versionamento e evolução do banco | SQL e changelogs |
| Observabilidade | Spring Actuator | Health checks, métricas e monitoramento interno | HTTP/Actuator |
| Cliente consumidor | Navegador, app ou partner API | Consumo do backend e operação de clientes/colaboradores | HTTPS/REST |
| Cache e mensageria | Redis/Kafka (opcional) | Buffer, cache e processamento assíncrono futuro | TCP/AMQP/HTTP |
| Dependências externas | HTTP, e-mail, gateway, pagamentos | Complemento funcional e notificação | HTTPS/JSON |

### Fluxo entre contêineres

| Origem | Destino | Protocolo | Objetivo |
|--------|---------|-----------|----------|
| Cliente | Aplicação backend | HTTPS/REST | Login, consulta e cadastro |
| Aplicação backend | PostgreSQL | JDBC | Persistência e leitura transacional |
| Aplicação backend | JWT Security | Bearer Token | Validação e autorização |
| Aplicação backend | Actuator | HTTP | Health, métricas e diagnósticos |
| Aplicação backend | Sistema externo | HTTPS/JSON | Integrações de notificação e pagamento |

### Protocolos

| Protocolo | Utilização |
|-----------|------------|
| HTTP/HTTPS | Exposição de APIs REST e comunicação com clientes e integrações |
| JDBC | Conectividade com PostgreSQL |
| JWT | Autenticação stateless e transporte de identidade |
| SQL | Migração e manutenção do schema |
| TLS | Proteção de tráfego sensível em ambientes produtivos |

### Resumo dos contêineres

- O principal contêiner é a API REST da oficina.
- A persistência principal é relacional, com PostgreSQL como sistema de registro.
- Segurança e observabilidade são componentes de infraestrutura intrínsecos ao backend.
- A solução é adequada para monólito modular com crescimento orientado por módulos e integrações.

## C4 Nível 3 - Componentes

| Componente | Camada | Responsabilidade | Dependências |
|------------|--------|------------------|--------------|
| UsuarioController | Controller | Endpoints de cadastro, busca e atualização de usuários | UsuarioService |
| AuthController | Controller | Autenticação e emissão de JWT | CredencialService, JwtUtil |
| OrdemController | Controller | Fluxo de ordens, orçamentos, aprovação e pagamento | OrdemService |
| MaterialController | Controller | Gestão de materiais e estoque | MaterialService |
| VeiculoController | Controller | Cadastro e consulta de veículos | VeiculoService |
| ServicoController | Controller | Cadastro e manutenção de serviços | ServicoService |
| CredencialController | Controller | Gestão de credenciais e acesso | CredencialService |
| UsuarioService | Service | Regras de negócio de usuários | UsuarioRepository |
| OrdemService | Service | Fluxo operacional da ordem de serviço | OrdemRepository, OrcamentoService |
| OrcamentoService | Service | Criação, aprovação e conclusão de orçamento | OrcamentoRepository |
| EstoqueService | Service | Controle de materiais e disponibilidade | EstoqueRepository |
| CaixaService | Service | Gestão financeira e fechamento de fluxo financeiro | CaixaRepository |
| Repository | Repository | Persistência JPA de entidades | EntityManager / JPA |
| SecurityConfig | Security | Configuração de filtros, perfis e endpoints protegidos | JwtFilter, JwtUtil |
| JwtFilter | Security | Validação do token em requisições | JwtUtil |
| JwtUtil | Security | Geração e validação de tokens JWT | Secret e claims |
| Configuration | Configuration | Beans, validações e ajustes de infraestrutura | Spring Context |
| Client | Integration | Comunicação com APIs externas, caso existam | WebClient/RestTemplate/HTTP |
| Producer/Consumer | Messaging | Padrão extensível para mensageria, se adotado | Kafka ou broker |

### Fluxo interno

| Etapa | Componente | Ação |
|-------|------------|------|
| 1 | UsuarioController/AuthController | Recebe requisição HTTP |
| 2 | Service | Valida regras de negócio e perfil |
| 3 | Repository | Consulta ou persiste entidade no banco |
| 4 | Entity | Representa estado e domínio da operação |
| 5 | DTO/Mapper | Transforma dados externos em modelos internos |
| 6 | Security | Valida token, roles e autorização |
| 7 | Response | Retorna payload ou status HTTP apropriado |

### Caso de uso principal

O fluxo principal começa com a autenticação do usuário e a criação de uma ordem de serviço. O operador registra o veículo e o cliente, realiza diagnóstico, inclui orçamento, solicita aprovação do cliente, conclui atividades e finaliza o pagamento. Ao final, a ordem muda de status até liberação e retirada do veículo.

## C4 Nível 4 - Código

### Estrutura lógica de pacotes

| Pacote | Responsabilidade |
|--------|------------------|
| controller | Endpoints REST e orquestração de entrada/saída |
| service | Regras de negócio e coordenação entre entidades e repositórios |
| repository | Acesso de dados via Spring Data JPA |
| entity | Modelagem do domínio e persistência JPA |
| dto | DTOs de entrada e saída para contratos da API |
| mapper | Transformação entre entidades e DTOs |
| config | Beans de configuração e ajustes do Spring |
| security | JWT, filtro, autorização e segurança |
| integration | Clients HTTP para integrações externas |
| messaging | Produção/consumo de mensagens, se houver mensageria |
| exception | Tratamento de erros e exceções de domínio |
| util | Classes utilitárias e helpers |

### Convenções

| Convenção | Descrição |
|-----------|-----------|
| Nome de pacotes | Organização por domínio e camada |
| Nome de classes | Substantivos claros e de domínio |
| DTOs | Separados por operação e contexto |
| Entidades | Mapeadas com JPA e regras de persistência |
| Response | Estruturas de saída sem expor detalhes internos |
| Validação | Bean Validation com mensagens específicas |
| Tratamento de exceções | Centralizado por camada de serviço e controller |

### Padrões

| Padrão | Aplicação |
|--------|-----------|
| SOLID | Separação de responsabilidades entre controller, service e repository |
| Clean Code | Nomes explícitos, método pequeno e lógica direta |
| Clean Architecture | Domínio isolado de infraestrutura e entrada/saída |
| Repository | Acesso a dados encapsulado em interfaces JPA |
| DTO | Contratos explícitos para requisições e respostas |
| Factory | Aplicável para criação de objetos de domínio e respostas complexas |

## Fluxos de Negócio

| Fluxo | Entrada | Processamento | Saída |
|-------|---------|--------------|-------|
| Consulta | CPF/CNPJ, placa ou status | Busca de usuário, veículo, ordem ou serviço | Lista ou registro detalhado |
| Cadastro | Dados de cliente, veículo, serviço ou material | Validação e persistência | Entidade criada e retorno HTTP 201 |
| Atualização | Identificador e nova informação | Regras de transição de status e validações | Entidade atualizada e resposta de sucesso |

### Fluxos principais

- Consulta de ordens por cliente e placa.
- Cadastro de ordem com validação de checklist e responsável.
- Aprovação do orçamento e movimento para execução e pagamento.

## Integrações Externas

| Sistema | Tipo | Finalidade | Protocolo |
|---------|------|------------|-----------|
| Cliente/Portal | Usuário / front-end | Acesso ao sistema e operação da oficina | HTTPS/REST |
| Gateway de pagamento | Sistema externo | Cobrança e confirmação de pagamento | HTTPS/JSON |
| Sistema de notificações | Sistema externo | Envio de alertas e confirmações | HTTPS/JSON |
| CRM/ERP | Sistema externo | Sincronização de dados corporativos | HTTPS/JSON |
| Broker de mensageria | Mensageria | Processamento assíncrono futuro | Kafka/AMQP |

### APIs consumidas e expostas

- APIs expostas: endpoints REST em módulos de usuário, veículo, ordem, material, serviço e autenticação.
- APIs consumidas: integrações HTTP para pagamentos, notificações, ERP ou parceiros, quando configuradas.
- Dependências externas: sistemas de mensageria, serviços de e-mail, gateways de pagamento e integrações de meio de pagamento.

## Segurança

| Item | Implementação |
|------|----------------|
| Autenticação | JWT com geração e validação de tokens em filtros Spring Security |
| Autorização | Perfis e regras de endpoint por papel: cliente, colaborador, administrador |
| TLS | Recomendado em ambientes de produção e terminação no ingress HTTP |
| Criptografia | Argon2 para senha e uso de mecanismos de criptografia em dados sensíveis |
| Auditoria | Logs de operação, rastreio de identificadores e monitoramento de eventos críticos |
| LGPD | Minimização e proteção de dados pessoais; uso justificado e controle de acesso |
| Rate Limiting | Aplicado em camadas de API gateway ou ingress para proteção de endpoints |
| WAF | Proteção no perímetro da aplicação e mascaramento de tráfego malicioso |
| Secrets Management | Variáveis de ambiente, secret manager e controle de acesso a credenciais |

## Observabilidade

| Recurso | Ferramenta | Objetivo |
|---------|-----------|----------|
| Logs | SLF4J/Logback + agregação centralizada | Registro de transações, erros e auditoria operacional |
| Métricas | Spring Actuator + Prometheus | Monitoramento de latência, erros e uso de recursos |
| Tracing | OpenTelemetry / observabilidade distribuída | Rastreamento de requisições e propagação de contexto |
| Dashboard | Grafana / painel de operação | Visualização de desempenho, falhas e indicadores de negócio |
| Alertas | Alertmanager / monitoramento externo | Notificação de falhas, latência e SLA |

## Infraestrutura e Deploy

| Item | Tecnologia |
|------|------------|
| CI/CD | GitHub Actions, GitLab CI ou pipeline corporativo |
| Containerização | Docker |
| Orquestração | Kubernetes, AKS, ECS ou ambiente equivalente |
| Ambiente | Desenvolvimento, homologação e produção |
| Cloud | Azure, AWS ou infraestrutura equivalente |

### Deploy, rollout e rollback

- O deploy deve seguir pipeline automatizado com validação de build, testes e qualidade.
- O rollout deve ser progressivo por ambiente, com health checks e baixa latência de impacto.
- O rollback deve ser orientado por revision do container ou release do deploy, com reversão por status de saúde.
- Ambientes devem separar dados sensíveis e regras operacionais conforme perfil de produção.

## Decisões Arquiteturais

| Decisão | Justificativa |
|---------|----------------|
| Monólito modular em Spring Boot | Agilidade inicial, alta coesão de domínio e menor custo operacional no projeto de oficina |
| Banco relacional | Consistência transacional e modelagem clara de pedidos, clientes, produtos e serviços |
| Segurança stateless com JWT | Redução de dependência de sessões e melhor escalabilidade horizontal |
| Estrutura por camadas | Organização clara de domínio, regras e acesso a dados |
| PostgreSQL como padrão | Suporte robusto a integridade transacional e consultas operacionais |
| Extensão para mensageria | Permite desacoplamento futuro sem reestruturar o core da aplicação |

## Riscos e Limitações

| Risco | Impacto | Mitigação |
|-------|---------|-----------|
| Acoplamento funcional por domínio | Dificulta evolução em larga escala | Dividir módulos por contexto e manter contratos estáveis |
| Crescimento de regras de negócio no controller | Aumento de complexidade | Encapsular regras em services e casos de uso |
| Dependência de integração externa | Falhas de terceiros afetam operação | Implementar retries, circuit breakers e timeouts |
| Falta de mensageria | Processos assíncronos ficam acoplados | Adotar broker em fases posteriores |
| Falta de governança de dados | Risco de exposição de informações sensíveis | Padronizar policies, auditoria e segregação de acesso |

## Glossário

| Termo | Significado |
|-------|-------------|
| API REST | Interface de comunicação baseada em HTTP e representação de recursos |
| DTO | Objeto de transferência de dados para contratos de entrada e saída |
| JWT | Token de acesso assinado para autenticação stateless |
| JPA | Java Persistence API para acesso e persistência de dados |
| ORM | Mapeamento objeto-relacional |
| SLA | Acordo de nível de serviço |
| TLS | Transport Layer Security para comunicação segura |
| WAF | Web Application Firewall |
| Kafka | Plataforma de mensageria distribuída |
| PostgreSQL | Banco de dados relacional open source |
| Microserviço | Arquitetura de serviços independentes; não aplicável ao caso atual |

## Histórico de Alterações

| Data | Versão | Autor | Descrição |
|------|--------|-------|-----------|
| 2026-08-28 | 1.0.0 | Diego Pimenta | Criação da documentação arquitetural C4 para o projeto de oficina em Java 21/25 com Spring Boot 3/4 |