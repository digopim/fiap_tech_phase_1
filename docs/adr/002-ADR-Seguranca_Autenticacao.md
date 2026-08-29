# ADR 002 — Segurança na Autenticação de Acesso

Data: 2026-07-01
Status: Aprovado

Resumo

Decisão sobre a estratégia de segurança para autenticação no projeto: adotar uma abordagem prática e segura (senha com hashing, tokens JWT, refresh tokens, HTTPS em produção) estruturada de forma a permitir experimentação e aprendizado pelo desenvolvedor. A motivação principal é a curiosidade do desenvolvedor e o uso do projeto como ferramenta de estudo para uma arquitetura de autenticação de interesse.

Contexto

- Projeto em fase de MVP com objetivo também pedagógico: servir como base para o desenvolvedor aprender e experimentar padrões e tecnologias de autenticação.
- Necessidade de proteger endpoints sensíveis, garantir confidencialidade e integridade das credenciais e fornecer um caminho claro para evoluções (OAuth2, SSO, MFA).
- Ambiente de desenvolvimento executado em Docker; premissa de segurança do projeto.

Opções consideradas

1. Simples implementação caseira (ex.: armazenar senhas em texto ou hashing fraco) — rápida, mas insegura.
2. Implementação com bibliotecas consolidadas: hashing com bcrypt/argon2, JWT para autenticação stateless, refresh tokens e armazenamento seguro de segredos.
3. Delegar autenticação a provedores externos (OAuth2/OpenID Connect) desde o início — aumenta complexidade de integração e limita experimentação do desenvolvedor.

Decisão

Adotar a opção 2: implementar autenticação segura usando práticas consolidadas, dimensionada para o MVP e organizada para permitir experimentos e aprendizado. Implementação inicial baseada em:

- Hash de senhas com bcrypt ou argon2 (salgamento adequado e parâmetros configuráveis).
- Emissão de access tokens JWT assinados (curta validade) e refresh tokens armazenados com proteção (revogação/rotacionamento).
- Comunicação sempre sobre HTTPS em ambientes de produção; em desenvolvimento usar TLS quando possível e documentar exceções.

Justificativa (foco em aprendizado)

- A abordagem 2 equilibra segurança real com material de estudo: permite ao desenvolvedor entender como senha, hashing, tokens e políticas de rotação funcionam na prática.
- Mantém um nível de segurança compatível com um MVP (protege dados de usuários e reduz riscos), ao mesmo tempo que deixa espaço para experimentar padrões avançados (OAuth2 flows, MFA, token introspection).
- Delegar totalmente para provedores externos limitaria o aprendizado do desenvolvedor nas internals da autenticação e criptografia.

Impacto e consequências

- Positivos:
  - Aprendizado prático sobre técnicas de autenticação seguras e suas implicações.
  - Base sólida para introduzir SSO/OAuth2/MFA posteriormente.
  - Redução de riscos imediatos por uso de bibliotecas testadas e práticas recomendadas.

- Negativos / Riscos:
  - Implementação mais complexa que uma solução mínima — pode atrasar entregas se não for bem priorizada.
  - Risco de erros de configuração (ex.: tokens mal protegidos) se práticas de segurança não forem seguidas.

Plano de mitigação

- Usar bibliotecas maduras e atualizadas para hashing e tokens; revisar CVEs e manter dependências atualizadas.
- Escrever testes automatizados para fluxos de autenticação e casos de revogação/expiração.
- Revisões de código focadas em segurança e checklists (não comitar segredos, validar políticas de CORS/CSRF, revisar configuração de cookies seguros).
- Documentar claramente o fluxo de autenticação no repositório e fornecer scripts Docker para ambientes de desenvolvimento e testes que não exponham segredos.

Quando simplificar para o MVP

- Para acelerar entrega, o MVP pode iniciar com email/senha + JWT + refresh tokens e habilitar proteções incrementais (rate limiting, lockout) conforme prioridade.
- Funcionalidades como MFA e integração com provedores externos ficam como próximas etapas de aprendizagem e extensão.

Revisão

Esta decisão deve ser revisitada se o objetivo do projeto mudar (por exemplo, transição para produção em larga escala) ou se surgirem requisitos de conformidade (PCI, GDPR) que exijam controles adicionais.

Assinado:
Desenvolvedor: Diego Pimenta
