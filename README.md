# 🚀 FutureStack — Sistema de Gestão de Pátios e Motos

Aplicação desenvolvida em **Spring Boot** com **Thymeleaf** para o front-end e **Flyway** para o versionamento do banco de dados.
A solução foi criada para atender o desafio da **Mottu**, permitindo o **gerenciamento de motos e pátios**, o controle de ocupação por zonas e a visualização organizada das informações.

🔗 **Acesse o sistema em produção:**
👉 [FutureStack](https://futurestack-java.onrender.com/login)

---

## 💡 Visão Geral da Solução

O **FutureStack** foi desenvolvido com foco em **otimizar a operação de pátios** e melhorar o controle sobre as motos registradas, trazendo clareza e eficiência para os processos da empresa.

Atualmente, o sistema permite:

* O **cadastro e gerenciamento** de motos e pátios.
* O **controle automático de ocupação** por zonas (A e B).
* A **consulta de motos por modelo, placa e pátio**.

🔜 **Futuras implementações:**

* Criação de **perfis de acesso diferenciados**, onde:

  * **Administradores** poderão cadastrar motos e pátios.
  * **Usuários comuns** poderão apenas buscar e acompanhar a localização das motos.

Essa evolução visa trazer **maior segurança e controle de permissões** dentro da aplicação.

---

## ✅ Requisitos Técnicos Atendidos

### 1. Thymeleaf

* Templates HTML para **listar, criar, editar e excluir registros** (`moto`, `patio`).
* Uso de **fragmentos Thymeleaf** (`fragments.html`) para cabeçalho, rodapé e menu.

### 2. Flyway

* Configuração completa do **Flyway**.
* **5 versões de migração** localizadas em `src/main/resources/db/migration`.
* As migrações são executadas automaticamente ao iniciar a aplicação.

### 3. Funcionalidades Além do CRUD

* **Autenticação e Login:** controle de acesso seguro e individual.
* **Gestão de Ocupação:** cálculo automático de vagas disponíveis e motos por zona.
* **Filtros Dinâmicos:** pesquisa por modelo, placa e pátio.
* **Integração com Zonas:** lógica de divisão entre áreas A e B dentro de cada pátio.

---

## ⚙️ Instalação e Execução Local

### 📋 Pré-requisitos

* **Java 21+**
* **Gradle**
* **Docker (opcional)**

### 🧭 Passos

```bash
# Clone o repositório
git clone https://github.com/MaryChriss/FutureStack-Java.git
cd FutureStack-Java

# Rode o projeto (com Docker aberto, se local)
./gradlew bootRun
```

Acesse no navegador:
👉 `http://localhost:8080/login`

---

## 🗄️ Banco de Dados

* O banco é **versionado com Flyway**.
* As migrações em `db/migration` criam automaticamente as tabelas e dados iniciais.
* As conexões podem ser ajustadas no arquivo `application.properties`.

---

## 🧩 Estrutura do Projeto

```
src/main/java/br/com/fiap/sprint3/
├── moto/       → CRUD de motos
├── patio/      → CRUD de pátios e lógica de ocupação
├── auth/       → Autenticação e login
├── users/      → Controle de usuários e tokens
├── config/     → Segurança, CORS e internacionalização
└── Sprint3Application.java → Classe principal
```

```
src/main/resources/
├── templates/      → Páginas Thymeleaf (HTML)
├── db/migration/   → Scripts SQL versionados (Flyway)
├── application.properties
└── messages_pt_BR.properties (i18n)
```
