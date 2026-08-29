# ADR 001 — Escolha do Banco de Dados: PostgreSQL

Data: 2026-07-26
Status: Aprovado

Resumo

Decisão de usar um banco de dados relacional (PostgreSQL) para a aplicação MVP. Justifica-se a opção por afinidade no desenvolvimento, simplicidade na construção do MVP e facilidade de uso do PostgreSQL em ambiente Docker quando comparado ao MySQL.

Contexto

- O projeto visa entregar um MVP com modelo de dados bem definido (entidades e relações claras).
- A equipe tem familiaridade com modelos relacionais e ferramentas/ORMs comuns.
- Necessidade de consistência transacional, consultas relacionais e maturidade do ecossistema.

Opções consideradas

1. Banco relacional (PostgreSQL ou MySQL)
2. Banco não relacional (ex.: MongoDB, DynamoDB)

Decisão

Adotar PostgreSQL como banco de dados para o MVP.

Justificativa

- Relacional x Não-relacional:
  - Bancos relacionais (Postgres/MySQL) são ideais quando o domínio exige integridade referencial, transações ACID e consultas ad hoc que envolvem joins. Fornecem um modelo de dados estruturado que casa com a lógica de negócios tradicional e facilita validação e migrações.
  - Bancos não-relacionais (document stores, key-value, wide-column) trazem flexibilidade de esquema e escala horizontal simplificada, úteis quando os requisitos dizem respeito a grandes volumes de dados não estruturados ou quando o modelo de consultas é simples e altamente distribuído.
  - Para este projeto, a natureza dos dados e das regras de negócio favorecem consistência e relações claras; portanto, um banco relacional é mais adequado e reduz complexidade na implementação do MVP.

- Afinidade no desenvolvimento e simplicidade para o MVP:
  - A equipe já conhece paradigmas relacionais e ORMs (por exemplo, Sequelize, TypeORM, Prisma), acelerando desenvolvimento e reduzindo tempo de entrega.
  - Modelagem relacional permite validações e restrições no próprio banco (chaves estrangeiras, constraints), simplificando a camada de aplicação no MVP.
  - Migrações e rolls-backs são bem suportados por ferramentas existentes, facilitando iterações rápidas.

- PostgreSQL vs MySQL — escolha do PostgreSQL:
  - Ambos são SGBDs relacionais maduros. MySQL tem bom desempenho e ampla adoção; PostgreSQL destaca-se pela conformidade com padrões SQL, recursos avançados (tipos JSONB, arrays, índices GIN/GIN_TRGM, funções e extensibilidade) e robustez para consultas complexas.
  - No contexto do projeto, o fator decisivo foi a facilidade de uso do PostgreSQL em Docker: imagem oficial estável, configuração simples, compatibilidade com ferramentas de desenvolvimento e testes locais (volumes, seeds, scripts). Isso reduz o custo operacional para levantar ambientes de desenvolvimento e CI para o MVP.

Impacto e consequências

- Positivos:
  - Entrega mais rápida do MVP por conta da afinidade da equipe com o modelo relacional.
  - Garantia de integridade e transações ACID para dados críticos.
  - Facilidade para testes locais e integração contínua via containers Docker.

- Negativos / Riscos:
  - Se o produto evoluir para um padrão de acesso massivamente distribuído ou dados fortemente não estruturados, poderá ser necessário introduzir tecnologias não-relacionais para casos específicos.
  - Overhead operacional para escalonamento vertical ou arquitetura de replicação; isso será tratado quando necessário.

Plano de mitigação

- Monitorar padrões de acesso e pontos de estrangulamento; quando necessário, considerar caches, particionamento para casos específicos.
- Documentar modelos e migrações via liquibase; padronizar scripts Docker para desenvolvimento/CI.

Revisão

Esta decisão deve ser revisitada se os requisitos de escala, latência ou natureza dos dados mudarem significativamente.

Assinado:
Diego Pimenta
