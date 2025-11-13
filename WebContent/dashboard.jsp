<jsp:include page="header.jspf">
    <jsp:param name="pageTitle" value="Meu Estoque"/>
</jsp:include>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top shadow-sm">
  <div class="container">
    
    <a class="navbar-brand" href="produto">
      <img src="${pageContext.request.contextPath}/assets/Hawk-Estoques.svg" 
           alt="Logo" width="30" height="24" 
           class="d-inline-block align-text-top">
      Estoque Hawk
    </a>
    
    <%-- Botão de Sair (à direita) --%>
    <div class="navbar-nav ms-auto">
      <a class="nav-link" href="logout">
          Sair <i class="bi bi-box-arrow-right"></i> <%-- Ícone de Sair --%>
      </a>
    </div>
  </div>
</nav>

<main class="container my-4"> <%-- 'my-4' = margem em cima e embaixo --%>
    
    <%-- Cabeçalho da Página --%>
    <div class="d-flex justify-content-between align-items-center mb-3">
      <h2>Olá, ${sessionScope.usuarioLogado.nome}!</h2>
      
      <%-- Link para o Admin (SÓ aparece se o usuário for 'admin') --%>
      <c:if test="${sessionScope.usuarioLogado.role == 'admin'}">
          <a href="admin" class="btn btn-outline-warning">
              <i class="bi bi-person-badge"></i> Painel Admin
          </a>
      </c:if>
    </div>
    
    <p class="lead">Este é o seu estoque pessoal.</p>
    <hr>

    <%-- Card de Valor Total --%>
    <div class="card text-bg-success mb-3 shadow-sm" style="max-width: 25rem;">
      <div class="card-header">Valor Total do Estoque</div>
      <div class="card-body">
        <h3 class="card-title">R$ ${valorTotalEstoque}</h3>
      </div>
    </div>

    <%-- Botão de Adicionar --%>
    <a href="produto?action=new" class="btn btn-primary mb-3 shadow-sm">
        <i class="bi bi-plus-circle"></i> Adicionar Novo Produto
    </a>
    
    <%-- A Tabela (Agora com classes Bootstrap) --%>
    <div class="card shadow-sm">
      <div class="card-header">
        <h3>Seus Produtos</h3>
      </div>
      <div class="card-body">
        <%-- Garante que a tabela seja responsiva em celulares --%>
        <div class="table-responsive">
            <table class="table table-dark table-striped table-hover align-middle">
              <thead>
                <%-- O <thead> CORRIGIDO que fizemos antes --%>
                <tr>
                  <th scope="col">Produto</th>
                  <th scope="col">Quantidade</th>
                  <th scope="col">Preço (R$)</th>
                  <th scope="col" class="text-end">Ações</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="produto" items="${listaProdutos}">
                  <tr>
                    <td>${produto.nome}</td>
                    <td>${produto.quantidade}</td>
                    <td>R$ ${produto.preco}</td>
                    
                    <%-- Ações (Editar/Deletar) alinhadas à direita --%>
                    <td class="text-end">
                      <a href="produto?action=edit&id=${produto.id}" 
                         class="btn btn-outline-warning btn-sm">
                         <i class="bi bi-pencil-fill"></i> Editar
                      </a>
                      <a href="produto?action=delete&id=${produto.id}" 
                         class="btn btn-outline-danger btn-sm" 
                         onclick="return confirm('Tem certeza que deseja deletar este produto?');">
                         <i class="bi bi-trash-fill"></i> Deletar
                      </a>
                    </td>
                  </tr>
                </c:forEach>
                
                <%-- Mensagem se a lista estiver vazia --%>
                <c:if test="${empty listaProdutos}">
                  <tr>
                    <td colspan="4" class="text-center">Você ainda não cadastrou nenhum produto.</td>
                  </tr>
                </c:if>
              </tbody>
            </table>
        </div>
      </div>
    </div>

</main>

<%@ include file="footer.jspf" %>