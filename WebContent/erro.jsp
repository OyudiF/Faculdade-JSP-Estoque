<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page isErrorPage="true" %>

<DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Erro no sistema</title>
	<style>
		body { font-family: sans-serif; text-align: center; margin-top: 20vh; }
		.error-code { color: #888; }
	</style>
</head>

<body>
	<h1>Opa, algo deu errado!</h1>
	
	<p>Um erro inesperado aconteceu no servidor. Por favor, tente voltar para a página principal ou tente novamente mais tarde!</p>
	
	<hr>
	
	<c:if test="${pageContext.errorData.statusCode == 404 }">
		<p class="error-code">Erro: 404 - A página que você procurou não foi encontrada</p>
	</c:if>
	
	<c:if test="${pageContext.errorData.statusCode == 500 }">
		<p class="error-code">Erro: 500 - Erro interno do servidor</p>
	</c:if>
	
	<br>
	
	<a href="$pageContext.request.contextPath">Voltar para a página inicial</a>
	
	<%--
		<details style="margin-top: 30px; text-align: left;">
		    <summary>Informações de Debug (Apenas para Devs)</summary>
		    <pre>
		      Request URI: ${pageContext.errorData.requestURI}
		      Status Code: ${pageContext.errorData.statusCode}
		      Exceção: ${pageContext.exception}
		    </pre>
		</details>
	 --%>

</body>

</html>