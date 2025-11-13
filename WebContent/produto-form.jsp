<jsp:include page="header.jspf">
    <jsp:param name="pageTitle" value="${not empty produto ? 'Editar Produto' : 'Adicionar Produto'}"/>
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
    <div class="navbar-nav ms-auto">
      <a class="nav-link" href="logout">
          Sair <i class="bi bi-box-arrow-right"></i>
      </a>
    </div>
  </div>
</nav>

<main class="container my-4">
    <div class="row justify-content-center">
        <div class="col-lg-8 col-md-10"> <%-- Um card um pouco mais largo --%>

            <div class="card shadow-sm">
                <div class="card-header">
                    <%-- Título Dinâmico (de novo) --%>
                    <h3 class="mb-0">
                        <c:if test="${not empty produto}">
                            <i class="bi bi-pencil-fill"></i> Editar Produto
                        </c:if>
                        <c:if test="${empty produto}">
                            <i class="bi bi-plus-circle"></i> Adicionar Novo Produto
                        </c:if>
                    </h3>
                </div>
                
                <div class="card-body p-4">
                    <form action="produto" method="POST">
                    
                        <%-- Campos Ocultos (Hidden) --%>
                        <c:if test="${not empty produto}">
                            <input type="hidden" name="action" value="update" />
                            <input type="hidden" name="id" value="${produto.id}" />
                        </c:if>
                        <c:if test="${empty produto}">
                            <input type="hidden" name="action" value="insert" />
                        </c:if>
                    
                        <%-- Campo Nome --%>
                        <div class="mb-3">
                            <label for="nome" class="form-label">Nome do Produto:</label>
                            <input type="text" class="form-control" id="nome" name="nome" 
                                   value="${produto.nome}" required>
                        </div>
                        
                        <%-- 
                          Layout em 2 colunas (grid) para Quantidade e Preço
                          'row' = linha, 'col-md-6' = metade da largura em telas médias/grandes
                        --%>
                        <div class="row">
                            <%-- Campo Quantidade --%>
                            <div class="col-md-6 mb-3">
                                <label for="quantidade" class="form-label">Quantidade:</label>
                                <input type="number" class="form-control" id="quantidade" name="quantidade" 
                                       value="${produto.quantidade}" min="0" required>
                            </div>
                            
                            <%-- Campo Preço --%>
                            <div class="col-md-6 mb-3">
                                <label for="preco" class="form-label">Preço (R$):</label>
                                <input type="number" class="form-control" id="preco" name="preco" 
                                       value="${produto.preco}" step="0.01" min="0" required 
                                       placeholder="Ex: 25.50">
                            </div>
                        </div>
                        
                        <hr class="my-3">

                        <%-- Botões --%>
                        <div class="d-flex justify-content-end"> <%-- Alinha à direita --%>
                            <a href="produto" class="btn btn-secondary me-2">
                                Cancelar
                            </a>
                            <button type="submit" class="btn btn-primary">
                                <c:if test="${not empty produto}">
                                    <i class="bi bi-check-circle"></i> Salvar Alterações
                                </c:if>
                                <c:if test="${empty produto}">
                                    <i class="bi bi-plus-circle"></i> Adicionar Produto
                                </c:if>
                            </button>
                        </div>
                        
                    </form>
                </div>
            </div>

        </div>
    </div>
</main>

<%@ include file="footer.jspf" %>