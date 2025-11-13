<jsp:include page="header.jspf">
    <jsp:param name="pageTitle" value="Painel Admin"/>
</jsp:include>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${empty sessionScope.usuarioLogado || sessionScope.usuarioLogado.role != 'admin'}">
    <%-- Se não for admin, chuta ele para o dashboard normal --%>
    <c:redirect url="produto"/>
</c:if>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top shadow-sm">
  <div class="container">
    
    <a class="navbar-brand" href="produto">
      <img src="${pageContext.request.contextPath}/assets/Hawk-Estoques.svg" 
           alt="Logo" width="30" height="24" 
           class="d-inline-block align-text-top">
      Estoque Hawk
    </a>
    
    <%-- Links do Admin --%>
    <div class="navbar-nav">
        <a class="nav-link" href="produto">
            <i class="bi bi-box-seam"></i> Voltar ao Estoque
        </a>
    </div>
    
    <div class="navbar-nav ms-auto">
      <a class="nav-link" href="logout">
          Sair <i class="bi bi-box-arrow-right"></i>
      </a>
    </div>
  </div>
</nav>

<main class="container my-4">
    
    <h2 class="mb-3">Painel Administrativo</h2>
    <p class="lead">Gerenciamento de contas de usuário.</p>
    <hr>

    <div class="card shadow-sm">
      <div class="card-header">
        <h3>Contas Registradas</h3>
      </div>
      <div class="card-body">
        <div class="table-responsive">
            <table class="table table-dark table-striped table-hover align-middle">
              <thead>
                <tr>
                  <th scope="col">ID</th>
                  <th scope="col">Nome</th>
                  <th scope="col">Email</th>
                  <th scope="col">Função (Role)</th>
                  <th scope="col" class="text-end">Ações</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="usuario" items="${listaUsuarios}">
                  <tr>
                    <td>${usuario.id}</td>
                    <td>${usuario.nome}</td>
                    <td>${usuario.email}</td>
                    <td>
                      <%-- Destaca o "admin" --%>
                      <c:if test="${usuario.role == 'admin'}">
                          <span class="badge text-bg-warning">Admin</span>
                      </c:if>
                      <c:if test="${usuario.role == 'user'}">
                          <span class="badge text-bg-secondary">User</span>
                      </c:if>
                    </td>
                    
                    <%-- Ações (Deletar) --%>
                    <td class="text-end">
                      <%-- 
                        LÓGICA DE DELEÇÃO SEGURA:
                        Impede que o Admin delete a si mesmo.
                      --%>
                      <c:if test="${sessionScope.usuarioLogado.id != usuario.id}">
                        <a href="admin?action=delete&id=${usuario.id}" 
                           class="btn btn-outline-danger btn-sm" 
                           onclick="return confirm('ATENÇÃO: Deletar este usuário também deletará todo o estoque dele. Continuar?');">
                           <i class="bi bi-trash-fill"></i> Deletar
                        </a>
                      </c:if>

                      <c:if test="${sessionScope.usuarioLogado.id == usuario.id}">
                        <span class="text-muted fst-italic">(Sua conta)</span>
                      </c:if>
                    </td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
        </div>
      </div>
    </div>

</main>

<%@ include file="footer.jspf" %>