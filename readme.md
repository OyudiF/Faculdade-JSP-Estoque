# Estoque Faculdade (Projeto Java Web com Docker)
[Estoque Hawk](https://estoque-hawk.onrender.com)

## Sobre o projeto

Este é um projeto (originalmente acadêmico) de um sistema de gerenciamento de estoque multiusuário. O objetivo era construir uma aplicação web seguindo a arquitetura clássica **Java Web (Servlets, JSP e JDBC)**, mas modernizada para ser implantada em um ambiente de nuvem real usando **Docker, Render** e um banco de dados **PostgreSQL** (Supabase).

O sistema permite que múltiplos usuários se cadastrem. Cada usuário possui seu próprio estoque, privado e separado dos demais (modelo multi-tenant).

### Features (Funcionalidades)

- **Autenticação:** Sistema completo de Login (`/login`), Registro (`/register`) e Logout (`/logout`).
- **Controle de Acesso:** Um Filtro de Autenticação (`AuthFilter`) protege todas as rotas privadas, redirecionando usuários não logados.
- **CRUD de Produtos:** Funcionalidade completa de Criar, Ler, Atualizar e Deletar produtos no estoque.
- **Multi-Tenant (Multilocação):** A lógica do banco de dados garante que um usuário (`usuario_id`) só possa ver, editar e deletar os produtos que ele mesmo cadastrou.
- **Painel Admin:** Uma rota protegida (`/admin`) que permite a um usuário com role de "admin" visualizar e deletar contas de outros usuários.

### Stack de Tecnologias (Tech Stack)

Este projeto foi desenvolvido usando:

- **Back-End (Servidor):**
    - Java 17
    - Servlets API (para os Controladores)
    - JSP (JavaServer Pages) (para as Views)
    - JSTL (para lógica simples nas JSPs)
    - JDBC (para a conexão com o banco)
- **Front-End (Cliente):**
    - HTML5
    - CSS3
- **Banco de Dados:**
    - PostgreSQL (Hospedado no **Supabase**)
- **Build & Empacotamento:**
    - Apache Maven (gerencia as dependências no `pom.xml`)
- **Infraestrutura & Deploy:**
    - **Docker:** O projeto é "conteinerizado" com um `Dockerfile` que instala o Tomcat 9 e o `ROOT.war` do projeto.
    - **Render:** A plataforma de nuvem (PaaS) que roda o container Docker e hospeda a aplicação.

### Arquitetura e Como Funciona

A aplicação segue o padrão **MVC (Model-View-Controller):**
1. **Model:** As classes Java "puras" que representam os dados (ex: `Usuario.java`, `Produto.java`).
2. **View:** As páginas `.jsp` (ex: `dashboard.jsp`, `produto-form.jsp`) que o usuário vê.
3. **Controller:** Os `Servlets` (ex: `ProdutoServlet.java`, `LoginServlet.java`) que recebem as requisições, processam a lógica de negócios e decidem qual View mostrar.

O projeto também usa **DAOs (Data Access Objects)** (ex: `ProdutoDAO.java`) para separar a lógica de negócios (Servlets) da lógica de banco de dados (SQL).

##### Fluxo de Deploy (Render + Docker)
Este projeto não é "arrastado" para um servidor. Ele usa um fluxo de deploy moderno:

1. Um `push` para a _branch_ `main` no GitHub é feito.
2. O **Render** detecta esse _push_.
3. O Render lê o `Dockerfile` e inicia um "build":
    - **Estágio 1:** Inicia um container Maven para compilar o código Java (`src` e `WebContent`) e "empacotá-lo" em um arquivo `ROOT.war` (definido no `pom.xml`).
    - **Estágio 2:** Inicia um container Tomcat 9 limpo, copia o `ROOT.war` do estágio anterior para a pasta `webapps` do Tomcat.
4. O Render publica este container, e a aplicação fica no ar.

##### Fluxo de Conexão com o Banco
O `ConnectionFactory.java` **não** contém senhas. Ele usa Variáveis de Ambiente (`System.getenv()`) para obter os dados do banco.
- **No Render (Produção):** As variáveis `JDBC_DATABASE_URL`, `JDBC_DATABASE_USERNAME`, etc., são configuradas no painel do Render e apontam para o banco Supabase de **Produção.**
- **Localmente (Desenvolvimento):** As mesmas variáveis são configuradas nas "Run Configurations" do Eclipse/IDE e apontam para um banco Supabase de **Dev.**

### Como rodar o Projeto Localmente (Desenvolvimento)
Para rodar esse projeto na sua máquina:
1. **Clone o repositório**
    ```Bash
    git clone https://github.com/OyudiF/Faculdade-JSP-Estoque.git
    ```
2. **Crie seu banco de Dev**
    - Crie um novo projeto no Supabase (ex: `projeto-dev`).
    - Vá em **SQL Editor** e execute os scripts `CREATE TABLE` para criar as tabelas `usuarios` e `produtos` (usando `SERIAL PRIMARY KEY`).
        ```SQL
        CREATE TABLE usuarios (
            id SERIAL PRIMARY KEY,
            nome VARCHAR(100) NOT NULL,
            email VARCHAR(100) NOT NULL UNIQUE,
            senha VARCHAR(255) NOT NULL,
            role VARCHAR(10) DEFAULT 'user'
        );
        
        CREATE TABLE produtos (
            id SERIAL PRIMARY KEY,
            nome VARCHAR(150) NOT NULL,
            quantidade INT DEFAULT 0,
            usuario_id INT NOT NULL,
            FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
                ON DELETE CASCADE
        );
        ```
3. **Obtenha as Credenciais de DEV**
    - No Supabase, vá em **Project Settings -> Database**.
    - Mude de "Direct connection" para "**Session pooler**" (para evitar o bloqueio de IPv4/Rede).
    - Copie o **Host** (ex: `aws-1...pooler.supabase.com`), a **Porta** (`6543`), e o **Usuário** (ex: `postgres.abcdefg`).
4. **Configure o IDE (Eclipse/IntelliJ):**
    - Importe o projeto como um "Existing Maven Project".
    - Crie uma configuração de servidor (Tomcat 9).
    - Vá em `Run -> Run Configurations...`
    - Encontre seu servidor Tomcat e vá na aba `Environment`.
    - Adicione as 3 variáveis de ambiente que o `ConnectionFactory.java` espera, com os dados do seu banco **DEV**:
        - `JDBC_DATABASE_URL` = `jdbc:postgresql://[SEU_HOST_DEV]:6543/postgres?sslmode=require`
        - `JDBC_DATABASE_USERNAME` = `[SEU_USUARIO_DEV]`
        - `JDBC_DATABASE_PASSWORD` = `[SUA_SENHA_DEV]`
5. **Rode o Projeto:** Inicie o servidor Tomcat a partir do seu IDE.

### RoadMap (Próximas Features)
- [x] **Segurança de Senha:** Implementar Hashing com `jBCrypt` no registro e login.
- [x] **Melhorar Produtos:** Adicionar mais propriedades (Preço, SKU, etc.).
- [x] **Cálculo de Valor:** Criar uma feature que calcula o valor total do estoque (preço * quantidade).
- [ ] **Categorias:** Implementar um CRUD de categorias personalizadas, permitindo ao usuário filtrar seu estoque.
- [x] **Front-end:** Integrar `Bootstrap` para melhorar o design e a responsividade.
- [ ] **Validação:** Adicionar validação no _back-end_ (Servlets) para garantir que dados nulos ou maliciosos não cheguem ao banco.
- [x] **Página de Erro:** Adicionar página de erro, em casos de problemas não levar para a página padrão do TomCat