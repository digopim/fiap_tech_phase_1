# Oficina - Plataforma de Gestão Automotiva

[![Java](https://img.shields.io/badge/Java-25-ED8B00?style=flat-square&logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.0-6DB33F?style=flat-square&logo=spring-boot)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-336791?style=flat-square&logo=postgresql)](https://www.postgresql.org/)
[![Maven](https://img.shields.io/badge/Maven-Managed-C71A36?style=flat-square&logo=apache-maven)](https://maven.apache.org/)

## Visão Geral

Plataforma backend para gestão integrada de oficinas automotivas, desenvolvida em Java 25 com Spring Boot 4. O projeto contempla cadastro de usuários, veículos, materiais, serviços, ordens de serviço, autenticação JWT e painel administrativo para acompanhamento de estoque e operação.

## 🚀 Funcionalidades

- **Cadastro e autenticação de usuários** com perfis e geração de JWT
- **Gestão de veículos** por placa, montadora, modelo e tipo
- **Catálogo de serviços** com descrição, duração, custo e valor
- **Controle de materiais e estoque** por item e movimentação
- **Fluxo de ordens de serviço** com orçamento, aprovação, conclusão e pagamento
- **Administração operacional** com panorama por período e consultas por CPF/CNPJ ou placa
- **Documentação automática** por Springdoc OpenAPI / Swagger

## 🛠️ Tecnologias

| Tecnologia | Versão | Propósito |
|---|---|---|
| **Java** | 25 | Linguagem principal |
| **Spring Boot** | 4.1.0 | Framework principal |
| **Spring Security** | 4.1.0 | Autenticação e autorização |
| **Spring Data JPA** | 4.1.0 | Persistência e consultas |
| **PostgreSQL** | 16 | Banco de dados relacional |
| **Liquibase** | 4.1.0 | Versionamento do schema |
| **Springdoc OpenAPI** | 3.1.0 | Swagger / documentação da API |
| **Argon2** | via Spring Security | Hash de senhas |
| **JWT** | JJWT 0.11.5 | Tokens de autenticação |
| **Lombok** | Auto | Redução de boilerplate |
| **Bean Validation** | Auto | Validação de payloads |

## 📋 Pré-requisitos

- Java 25+
- Maven 3.8+
- Docker e Docker Compose
- PostgreSQL 16+ (ou uso do Compose do projeto)

## 🔧 Configuração e execução

### 1. Clonar o repositório

```bash
git clone https://github.com/digopim/fiap_tech_phase_1.git
cd fiap_tech_phase_1
```

### 2. Subir o banco de dados

O projeto inclui um `compose.yaml` para provisionar o PostgreSQL localmente:

```bash
docker compose up -d
```

### 3. Compilar o projeto

```bash
./mvnw clean install
```

### 4. Executar a aplicação

```bash
./mvnw spring-boot:run
```

A aplicação fica disponível em:

- API base: `http://localhost:8080/oficina/v1`
- Swagger UI: `http://localhost:8080/oficina/v1/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/oficina/v1/v3/api-docs`

> A configuração padrão do projeto já define o `context-path` `/oficina/v1` em `src/main/resources/application.yaml`.

## 📁 Estrutura do projeto

```text
src/
├── main/
│   ├── java/com/br/fiap/oficina/
│   │   ├── config/              # Configurações do Spring e OpenAPI
│   │   ├── controller/          # Endpoints REST
│   │   ├── model/
│   │   │   ├── dto/             # DTOs de request/response
│   │   │   ├── entity/          # Entidades JPA
│   │   │   ├── enums/           # Enumeradores do domínio
│   │   │   └── ...
│   │   ├── repository/          # Repositórios JPA
│   │   ├── security/            # JWT e SecurityConfig
│   │   ├── service/             # Casos de uso e regras de negócio
│   │   └── Application.java     # Bootstrap da aplicação
│   └── resources/
│       ├── application.yaml     # Configurações da aplicação
│       ├── static/
│       ├── templates/
│       └── db/changelog/        # Scripts Liquibase
└── test/                        # Testes automatizados
```

## 🔐 Segurança

- **Autenticação JWT** com filtro customizado no Spring Security
- **Autorização por perfil** com papéis `ROLE_CLIENTE`, `ROLE_COLABORADOR`, `ROLE_FORNECEDOR` e `ROLE_ADMINISTRADOR`
- **Criptografia de senha** usando Argon2
- **Headers protegidos** com `Authorization: Bearer <token>`

## 🌐 API REST

A base da API é:

```text
http://localhost:8080/oficina/v1
```

### Login

```bash
curl -X POST http://localhost:8080/oficina/v1/auth/login \
  -H "login: usuario@exemplo.com" \
  -H "senha: senha123"
```

Resposta esperada:

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### Uso do token

```bash
curl http://localhost:8080/oficina/v1/usuario \
  -H "Authorization: Bearer <token>"
```

### Principais endpoints

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/auth/login` | Autentica usuário e gera token JWT |
| `PUT` | `/credencial/cadastrar` | Cadastra credencial de acesso |
| `GET` | `/usuario` | Lista usuários |
| `GET` | `/usuario/{id}` | Busca usuário por ID |
| `GET` | `/usuario/cpf/{cpf}` | Busca usuário por CPF/CNPJ |
| `GET` | `/veiculo` | Lista veículos |
| `GET` | `/veiculo/placa/{placa}` | Busca veículo por placa |
| `POST` | `/veiculo` | Cadastra veículo |
| `GET` | `/material` | Lista materiais |
| `POST` | `/material` | Cadastra material |
| `GET` | `/servico` | Lista serviços |
| `POST` | `/servico` | Cadastra serviço |
| `POST` | `/ordem` | Cria ordem de serviço |
| `GET` | `/ordem` | Lista ordens |
| `GET` | `/ordem/status` | Lista ordens por status |
| `POST` | `/ordem/{ordemId}/orcamento` | Adiciona orçamento |
| `POST` | `/ordem/aprovar` | Aprova orçamento |
| `POST` | `/ordem/concluir` | Conclui ordem |
| `POST` | `/ordem/{ordemId}/pagar` | Registra pagamento |
| `GET` | `/admin/estoque` | Consulta estoque atual |
| `GET` | `/admin/panorama` | Panorama administrativo por período |
| `GET` | `/admin/ordens/cpf` | Ordens por CPF/CNPJ |
| `GET` | `/admin/ordens/placa` | Ordens por placa |

## 🧪 Testes

Executar a suíte de testes:

```bash
./mvnw test
```

## 🚀 Deploy

### Containerização com Docker

```bash
docker build -t oficina:latest .
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/oficina \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=postgres \
  oficina:latest
```

## 📊 Observabilidade

- `GET /oficina/v1/actuator/health`
- `GET /oficina/v1/actuator/metrics`
- Logs via SLF4J/Logback

## 📚 Documentação adicional

- **[Arquitetura C4](./docs/c4model.md)** - Decisões arquiteturais e visão dos componentes
- **[ADR (Architecture Decision Records)](./docs/adr/)** - Histórico de decisões técnicas
- **[Glossário de linguagem ubíqua](./docs/glossario-linguagem-ubiqua.md)** - Termos e definições do domínio
- **[Definição do problema (Tech Challenge)](./docs/15SOAT - Fase 1 - Tech Challenge.pdf)** - Enunciado do desafio
- **[Análise SonarQube](./docs/sonar/)** - Relatório estático do projeto

## 📝 Licença

Este projeto é privado e foi desenvolvido para o Tech Challenge da FIAP - Fase 1.

## 👨‍💻 Autor

**Diego Pimenta** - [digopim@gmail.com](mailto:digopim@gmail.com)

## 📞 Suporte

Para dúvidas, problemas ou sugestões, consulte a documentação do repositório ou abra uma issue no GitHub.
