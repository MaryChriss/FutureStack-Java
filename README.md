# 🚀 FutureStack - Sistema de Gestão de Pátios e Motos

Aplicação desenvolvida em **Spring Boot** com **Thymeleaf** para o front-end e **Flyway** para o versionamento do banco de dados.  
O sistema permite o **gerenciamento de motos e pátios**, incluindo controle de ocupação por zonas e autenticação de usuários.

🔗 **Acesse o sistema em produção:**  
👉 [FutureStack ](https://futurestack-java.onrender.com/login)

---

## ✅ Requisitos Técnicos Atendidos

### 1. Thymeleaf (30 pontos)
- Templates HTML para **listar, criar, editar e excluir registros** (`moto`, `patio`).
- Uso de **fragmentos Thymeleaf** em `fragments.html` (cabeçalho, rodapé, menu) para evitar repetição de código.

### 2. Flyway (20 pontos)
- Configuração do **Flyway** no projeto.
- **5 versões de migração** disponíveis em `db/migration`:

### 3. Funcionalidades completas além do CRUD (20 pontos)
Além dos cadastros básicos (CRUD), o sistema possui **dois fluxos de negócio completos**:

1. **Autenticação e Login**  
   - Usuários podem acessar o sistema via login.  
   - Regras de segurança implementadas.  

2. **Gestão de Ocupação do Pátio**  
   - Controle automático de zonas A e B.  
   - Cálculo da ocupação (`OcupacaoDTO`) com total de motos, motos por zona e vagas disponíveis.  
   - Consulta de motos de um pátio com filtros de modelo e placa.

---

## ⚙️ Instalação e Execução Local

### 📋 Pré-requisitos
- **Java 21**
- **Gradle**
- **Docker**

### 🛠️ Passos para rodar localmente

1. **Clone o repositório**
   ```bash
   git clone https://github.com/MaryChriss/FutureStack-Java.git
   cd FutureStack-Java

2. **Rode a aplicação! (com o Docker aberto se local)**


3. **Acesse no navegador**

   ```
   http://localhost:8080/login
   ```

---

## 🗄️ Banco de Dados

* O versionamento é feito com **Flyway**.
* Ao iniciar a aplicação, as migrações em `db/migration` serão aplicadas automaticamente.
* Isso garante a criação das tabelas e inserção de dados iniciais.

---

## 📚 Estrutura do Projeto

* `src/main/java/br/com/fiap/sprint3/` → Código fonte principal

  * `moto/` → CRUD de motos
  * `patio/` → CRUD de pátios + lógica de ocupação
  * `auth/` → Autenticação e login
  * `config/` → Configurações (CORS, segurança, internacionalização)

* `src/main/resources/templates/` → Páginas HTML com Thymeleaf

* `src/main/resources/db/migration/` → Scripts Flyway para versionamento do banco



