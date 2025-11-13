<jsp:include page="header.jspf">
    <jsp:param name="pageTitle" value="Registro - Estoque Hawk"/>
</jsp:include>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<main class="w-100 m-auto form-container" style="max-width: 400px;">

    <%-- Mensagem de Erro (Ex: email já existe) --%>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger" role="alert">
            ${errorMessage}
        </div>
    </c:if>

    <form action="register" method="POST">
      
      <div class="d-flex justify-content-center mb-4">
        <img src="${pageContext.request.contextPath}/assets/Hawk-Estoques.svg" 
             alt="Logo Controle Estoque" 
             class="img-fluid" 
             style="max-height: 150px;">
      </div>

      <h1 class="h3 mb-3 fw-normal text-center">Crie sua Conta</h1>

      <div class="form-floating mb-3">
        <input type="text" class="form-control" id="floatingName" 
               placeholder="Nome Completo" name="nome" required>
        <label for="floatingName">Nome Completo</label>
      </div>
      
      <div class="form-floating mb-3">
        <input type="email" class="form-control" id="floatingEmail" 
               placeholder="your-email@gmail.com" name="email" required>
        <label for="floatingEmail">E-mail</label>
      </div>

      <div class="form-floating mb-3">
        <input type="password" class="form-control" id="floatingPassword" 
               placeholder="Senha" name="senha" required>
        <label for="floatingPassword">Senha</label>
      </div>

      <button class="btn btn-primary w-100 py-2 mt-3" type="submit">Registrar</button>

    </form>
    
    <div class="text-center mt-3">
        <p>Já tem uma conta? <a href="login.jsp">Faça login</a></p>
    </div>
    
</main>

<%-- 4. Inclui o rodapé (FOOTER) --%>
<%@ include file="footer.jspf" %>