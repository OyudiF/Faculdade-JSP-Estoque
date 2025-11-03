<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Registro - Sistema de Estoque</title>
	<style>
		body { font-family: sans-serif; display: grid; place-items: center; min-height: 90vh; }
		form { border: 1px solid #ccc; padding: 20px; border-radius: 8px; }
		div { margin-bottom: 15px; }
		label { display: block; margin-bottom: 5px; font-weight: bold; }
		input { width: 300px; padding: 8px; }
		.success { color: green; }
	</style>
</head>

<body>
	<h2>Crie sua Conta</h2>
	
	<c:if test="${not empty successMessage}">
		<p class="success">${successMessage}</p>
	</c:if>
	
	<form action="register" method="POST">
		<div>
			<label for="nome">Nome Completo: </label>
			<input type="text" id="nome" name="nome" required>
		</div>
		<div>
			<label for="email">Email: </label>
			<input type="email" id="email" name="email" required>
		</div>
		<div>
			<label for="senha">Senha: </label>
			<input type="password" id="senha" name="senha" required>
		</div>
		
		<button type="submit">Registrar</button>
	</form>
	
	<p>Já tem uma conta? <a href="login.jsp">Faça login</a></p>
	
</body>

</html>