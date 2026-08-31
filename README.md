# Oficina - Plataforma de Gestão Automotiva

[![Java](https://img.shields.io/badge/Java-25-ED8B00?style=flat-square&logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.0-6DB33F?style=flat-square&logo=spring-boot)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-336791?style=flat-square&logo=postgresql)](https://www.postgresql.org/)
[![Maven](https://img.shields.io/badge/Maven-Managed-C71A36?style=flat-square&logo=apache-maven)](https://maven.apache.org/)

## Visão Geral

Plataforma backend de gestão integrada para oficinas automotivas, desenvolvida com Java 25 e Spring Boot 4. Oferece controle completo de clientes, veículos, ordens de serviço, materiais, serviços e pagamentos com autenticação baseada em JWT e autorização por perfis de acesso.

## 🚀 Características Principais

- **Gestão de Clientes**: Cadastro, busca e perfis de acesso
- **Gestão de Veículos**: Registro associado a clientes com rastreamento por placa
- **Ordens de Serviço**: Fluxo completo de diagnóstico, orçamento, aprovação e execução
- **Controle de Materiais**: Gestão de estoque e alocação em serviços
- **Catálogo de Serviços**: Manutenção e precificação de ofertas
- **Sistema de Pagamentos**: Integração e rastreamento de transações
- **Autenticação Segura**: JWT com geração de tokens stateless
- **Autorização por Perfis**: Cliente, Colaborador, Administrador

## 🛠️ Tecnologias

| Tecnologia | Versão | Propósito |
|---|---|---|
| **Java** | 25 | Linguagem principal |
| **Spring Boot** | 4.1.0 | Framework web e composição |
| **Spring Security** | 4.1.0 | Autenticação e autorização |
| **Spring Data JPA** | 4.1.0 | Acesso a dados |
| **PostgreSQL** | 16 | Banco de dados relacional |
| **Liquibase** | 4.1.0 | Versionamento de schema |
| **Spring Actuator** | 4.1.0 | Observabilidade e health checks |
| **Lombok** | Auto | Redução de boilerplate |
| **Bean Validation** | Auto | Validação de dados |

## 📋 Pré-requisitos

- Java 25+
- Maven 3.8+
- PostgreSQL 16+
- Docker (opcional, para containerização)

## 🔧 Instalação e Configuração

### 1. Clonar o repositório

```bash
git clone https://github.com/digopim/fiap_tech_phase_1.git
cd fiap_tech_phase_1
```

### 2. Configurar banco de dados

Usar Docker Compose para subir PostgreSQL:

```bash
docker-compose up -d
```

Ou configurar manualmente em `application.properties` / `application.yml`.

### 3. Compilar o projeto

```bash
./mvnw clean install
```

### 4. Executar a aplicação

```bash
./mvnw spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`.

O Swagger gerado pelo Spring Docs também está disponível em `http://localhost:8080/swagger-ui/index.html`.

## 📁 Estrutura do Projeto

```
src/
├── main/
│   └── java/com/br/fiap/oficina/
│       ├── controller/          # Endpoints REST
│       ├── service/             # Regras de negócio
│       ├── repository/          # Acesso a dados (JPA)
│       ├── entity/              # Modelos de domínio
│       ├── dto/                 # Objetos de transferência
│       ├── mapper/              # Transformação de dados
│       ├── security/            # JWT e autenticação
│       ├── config/              # Configurações Spring
│       ├── exception/           # Tratamento de erros
│       └── util/                # Classes utilitárias
├── resources/
│   ├── application.properties    # Configurações da aplicação
│   └── db/changelog/            # Scripts Liquibase
└── test/                         # Testes unitários e integração
```

## 🔐 Segurança

- **Autenticação**: JWT com geração e validação em filtros Spring Security
- **Autorização**: Controle granular por perfil de usuário
- **Criptografia**: Senhas com Argon2 e TLS em produção
- **Auditoria**: Logs de operação e rastreamento de identidades
- **Conformidade**: Protocolos LGPD para proteção de dados pessoais

## 🌐 API REST

### Autenticação

```bash
POST /auth/login
Content-Type: application/json

{
  "email": "usuario@example.com",
  "senha": "senha123"
}
```

**Resposta:**
```json
{
  "token": "eyJhbGc...",
  "tipo": "Bearer",
  "expiracao": 3600
}
```

### Uso de Tokens

Incluir o token nos headers de requisições protegidas:

```bash
Authorization: Bearer eyJhbGc...
```

### Principais Endpoints

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/usuarios` | Cadastrar usuário |
| `GET` | `/usuarios/{id}` | Obter usuário |
| `POST` | `/veiculos` | Registrar veículo |
| `GET` | `/veiculos?placa={placa}` | Buscar veículo por placa |
| `POST` | `/ordens` | Criar ordem de serviço |
| `GET` | `/ordens/{id}` | Obter status da ordem |
| `POST` | `/materiais` | Adicionar material ao catálogo |
| `GET` | `/materiais` | Listar materiais |

> Para documentação completa de API, consultar [Swagger/OpenAPI](#observabilidade).

## 🧪 Testes

Executar suite de testes:

```bash
./mvnw test
```

Cobertura de testes:

```bash
./mvnw test jacoco:report
```

## 🚀 Deploy

### Containerização com Docker

```bash
docker build -t oficina:latest .
docker run -p 8080:8080 \
  -e DB_URL=jdbc:postgresql://postgres:5432/oficina \
  -e DB_USER=postgres \
  -e DB_PASSWORD=postgres \
  oficina:latest
```

### CI/CD

O projeto está preparado para pipelines contínuos via GitHub Actions, GitLab CI ou equivalentes. Configurar secrets e variáveis de ambiente no seu provedor.

## 📊 Observabilidade

- **Health Checks**: `GET /actuator/health`
- **Métricas**: `GET /actuator/metrics`
- **Logs**: Agregados via SLF4J/Logback (configurável)

## 📚 Documentação Adicional

- **[Arquitetura C4](./docs/c4model.md)** - Decisões arquiteturais, componentes e fluxos
- **[ADR (Architecture Decision Records)](./docs/adr/)** - Histórico de decisões técnicas
- **[Documentação de Upstream](https://miro.com/app/board/uXjVH_w6xEM=/?share_link_id=927880207641)** - Visão estratégica e mapeamento de requisitos
- **[Definição do Problema (Tech Challenge)](./docs/15SOAT - Fase 1 - Tech Challenge.pdf)** - Enunciado do desafio (PDF)
- **[Análise SonarQube](./docs/sonar/)** - Relatório e resultados da análise estática (pasta)
- **[Glossário de Linguagem Ubíqua](./docs/glossario-linguagem-ubiqua.md)** - Termos do domínio e definições

## 📝 Licença

Este projeto é privado e desenvolvido para FIAP Tech Challenge Phase 1.

## 👨‍💻 Autor

**Diego Pimenta** - [digopim@gmail.com](mailto:digopim@gmail.com)

## 📞 Suporte

Para dúvidas ou problemas, consulte a documentação nos links acima ou abra uma issue no repositório.