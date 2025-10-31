<%@ page language="java" contentType="text/html; chraset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Login - Sistema de Estoque</title>
	<style>
		body { font-family: sans-serif; display: grid; place-items: center; min-height: 90vh; }
		form { border: 1px solid #ccc; padding: 20px; border-radius: 8px; }
		div { margin-bottom: 15px; }
		label { display: block; margin-bottom: 5px; font-weight: bold; }
		input { width: 300px; padding: 8px; }
		.error { color: red; }
		.success { color: green }
	</style>
</head>

<body>
	<h2>Login</h2>
	
	<c:if test="${not empty sessionScope.successMessage}">
		<p class="success">${sessionScope.successMessage}</p>
		<c:remove var="successMessage" scope="session" />
	</c:if>
	
	<c:if test="${not empty errorMessage}">
		<p class="error">${errorMessage}</p>
	</c:if>
	
	<form action="login" method="POST">
		<div>
			<label for="email">Email: </label>
			<input type="email" id="email" name="email" required>
		</div>
		<div>
			<label for="senha">Senha: </label>
			<input type="password" id="senha" name="senha" required>
		</div>
		
		<button type="submit">Entrar</button>
	</form>
	
	<p>Novo por aqui? <a href="register.jsp">Cria sua conta</a></p>
</body>


</html>