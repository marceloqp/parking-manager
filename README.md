# Parking Manager

Sistema teste para gerenciamento de geragem, contendo endpoints para entrada e saida de veículos, gerenciamento de setores e vagas, além de informações sobre faturamento.

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.3.11**
- **Maven**
- **Postgres** com Liquibase para gerenciamento de versões de entidades.
- **Springdoc OpenAPI**

## Iniciando o Projeto

Para iniciar o projeto, siga os passos abaixo:

1. **Atualizar o Banco de Dados**:
   Certifique-se de configurar as credenciais do banco de dados no arquivo `application.yml`, localizado em
   `src/main/resources`.

ou

1. **Gerar o JAR**:
   Execute o comando abaixo para limpar e construir o projeto:
   ```bash
   mvn clean package

2. Após gerar o JAR com o comando `mvn clean package`, inicie os serviços utilizando o Docker Compose:

  ```bash
  docker compose up -d
  ```

## Dados de Simulação

Ao iniciar o liquibase irá incluir alguns dados fictícios de garagem, setor e vagas para dar suporte ao GET "/garage" que inicializa os eventos.

## Melhorias

- Permitir recebimento assíncrono de eventos (como filas);
- Melhorar o tratamento de Exceções;
- Deixar mais dinâmico o tarifamento;