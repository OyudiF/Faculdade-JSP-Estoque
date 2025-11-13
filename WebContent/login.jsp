<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="header.jspf">
    <jsp:param name="pageTitle" value="Login - Estoque Hawk"/>
</jsp:include>

<main class="w-100 m-auto form-container" style="max-width: 400px;">

    <%-- Mensagem de Sucesso (vinda do Registro) --%>
    <c:if test="${not empty sessionScope.successMessage}">
        <div class="alert alert-success" role="alert">
            ${sessionScope.successMessage}
        </div>
        <c:remove var="successMessage" scope="session" />
    </c:if>

    <%-- Mensagem de Erro (vinda do próprio Login) --%>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger" role="alert">
            ${errorMessage}
        </div>
    </c:if>

    <form action="login" method="POST">
      <div class="d-flex justify-content-center mb-4">
        <img src="${pageContext.request.contextPath}/assets/Hawk-Estoques.svg" 
             alt="Logo Controle Estoque" 
             class="img-fluid" 
             style="max-height: 150px;">
      </div>

      <h1 class="h3 mb-3 fw-normal text-center">Estoque Hawk</h1>

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

      <div class="form-check text-start my-3">
        <input type="checkbox" class="form-check-input" id="flexCheckDefault">
        <label class="form-check-label" for="flexCheckDefault">Lembrar Acesso</label>
      </div>

      <button class="btn btn-primary w-100 py-2" type="submit">Entrar</button>

    </form>
</main>

<%@ include file="footer.jspf" %>