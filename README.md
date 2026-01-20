# 🍰 Confeitaria API

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)

## 📌 Sobre o Projeto

A **Confeitaria API** é um sistema robusto de gerenciamento para e-commerce de produtos alimentícios. O projeto foi desenvolvido para resolver problemas reais de gestão de estoque, fluxo de pedidos e processamento de pagamentos.

O diferencial técnico deste projeto é a implementação de um sistema de **Reserva Temporária de Estoque**: pedidos aguardando pagamento reservam os itens por um tempo determinado (ex: 3 minutos) e, caso não sejam pagos, um **Scheduler (Robô)** cancela o pedido automaticamente e devolve os itens à prateleira virtual, garantindo alta disponibilidade e consistência de dados.

## 🚀 Tecnologias Utilizadas

* **Java 17**
* **Spring Boot 4**
* **Spring Data JPA** (Hibernate)
* **Spring Security + JWT** (Autenticação e Autorização Stateless)
* **PostgreSQL** (Banco de Dados Relacional)
* **Mockito & JUnit 5** (Testes Unitários)
* **SpringDoc OpenAPI** (Documentação Swagger)
* **Maven** (Gerenciamento de dependências)

## ✨ Funcionalidades Principais

* ✅ **Gestão de Usuários:** Cadastro, Login e Autenticação via Token JWT.
* ✅ **Catálogo de Produtos:** CRUD completo com controle de preços e descrição.
* ✅ **Carrinho de Compras:** Lógica de adição, remoção e cálculo de subtotal.
* ✅ **Gestão de Pedidos:**
    * Criação de pedidos com baixa automática de estoque.
    * Histórico de pedidos do cliente.
    * Regras de negócio para cancelamento e estorno.
* ✅ **Automação (Scheduler):** Tarefa agendada que monitora e cancela pedidos não pagos, liberando o estoque.
* ✅ **Pagamentos:** Simulação de fluxo de pagamento integrado ao pedido.

## 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas bem definida para garantir desacoplamento e facilidade de manutenção:

* **Controller:** Camada REST que recebe as requisições HTTP.
* **Service:** Camada de regras de negócio (Core da aplicação).
* **Repository:** Camada de acesso a dados (JPA).
* **Domain:** Entidades mapeadas no banco de dados.
* **DTO (Data Transfer Objects):** Objetos para tráfego de dados, protegendo a estrutura interna das entidades.

## 📚 Documentação (API)

A API é totalmente documentada com **Swagger UI**.
Após rodar a aplicação, acesse:
