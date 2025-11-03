<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- Checagem de seguranca, caso esteja vazio o usuario nao fez o login --%>
<c:if test="${empty sessionScope.usuarioLogado}">
	<c:redirect url="login.jsp" />
</c:if>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Meu Estoque</title>
	<style>
		body { font-family: sans-serif; margin: 20px; }
		nav { display: flex; justify-content: space-between; align-items: center;' }
		table { width: 100%; border-collapse: collapse; margin-top: 20px; }
		th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }	
		th { background-color: #f2f2f2; }
		.actions a { margin-right: 10px; }	
	</style>
</head>

<body>
	
	<nav>
		<div>
			<h2>Olá, ${sessionScope.usuarioLogado.nome}!</h2>
			<p>Este é seu estoque pessoal.</p>
		</div>
		<div>
			<a href="logout">Sair</a>
		</div>
	</nav>
	
	<hr>
	
	<a href="produto?action=new">Adicionar Novo Produto</a>
	
	<h3>Seus Produtos</h3>
	
	<table>
		<thead>
			<tr>Produto</tr>
			<tr>Quantidade</tr>
			<tr>Ações</tr>		
		</thead>
		<tbody>
			<c:forEach var="produto" items="${listaProdutos}">
				<tr>
					<td>${produto.nome}</td>
					<td>${produto.quantidade}</td>
					<td class="actions">
						<a href="produto?action=edit&id=${produto.id}">Editar</a>
						<a href="produto?action=delete&id=${produto.id}"
						   onclick="return confirm('Tem certeza que deseja deletar este produto?');">
						   Deletar
						</a>
					</td>
				</tr>
			</c:forEach>
			
			<c:if test="${empty listaProdutos}"> 
				
				<tr>
					<td colspan="3">Você ainda não cadastrou nenhum produto</td>
				</tr>
				
			</c:if>
			
		</tbody>
	</table>
	






	
</body>








</html>