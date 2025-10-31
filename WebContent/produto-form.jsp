<%@ page language="java" contentType="text/html; chraset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- Checagem de seguranca, caso esteja vazio o usuario nao fez o login --%>
<c:if test="${empty sessionScope.usuarioLogado}">
	<c:redirect url="login.jsp" />
</c:if>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Gerenciar Produto</title>
	<style>
		body { font-family: sans-serif; display: grid; place-items: center; min-height: 90vh; }
		form { border: 1px solid #ccc; padding: 20px; border-radius: 8px; }
		div { margin-bottom: 15px; }
		label { display: block; margin-bottom: 5px; font-weight: bold; }
		input { width: 300px; padding: 8px; }
	</style>

</head>

<body>

	<h1>
		<c:if test="${not empty produto}">Editar Produto</c:if>
		<c:if test="${empty produto}">Adicionar Produto</c:if>
	
	</h1>
	
	<form action="produto" method="POST">
	
		<%-- Logica de acao - Se estamos editando = action="update" - Se estamos criando = action="insert" --%>
		<%-- O ProdutoServlet vai ler o parametro action --%>
		
		<c:if test="${not empty produto}">
			<input type="hidden" name="action" value="update" />
			
			<input type="hidden" name="id" value="${produto.id}" />
		</c:if>
		
		<c:if test="${empty produto}">
			<input type="hidden" name="action" value="insert" />
		</c:if>
		
		<div>
			<label for="nome">Nome do Produto: </label>
			<input type="text" id="nome" name="nome" value="${produto.nome}" required/>
		</div>
		
		<div>
			<label for="quantidade">Quantidade: </label>
			<input type="number" id="quantidade" name="quantidade" value="${produto.quantidade}" required/>
		</div>
	
		<%-- Botao dinamico --%>
		<button type="submit">
			<c:if test="${not empty produto}">Salvar Alterações</c:if>
			<c:if test="${empty produto}">Adicionar Produto</c:if>
		</button>
	
		<a href="produto">Cancelar</a>
	</form>

</body>

</html>