<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${empty sessionScope.usuarioLogado || sessionScope.usuarioLogado.role != 'admin' }">
	<c:redirect url="dashboard.jsp" />
</c:if>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Painel Admin</title>
	<style>
		body { font-family: sans-serif; margin: 20px; }
		nav { display: flex; justify-content: space-between; align-items: center;' }
		table { width: 100%; border-collapse: collapse; margin-top: 20px; }
		th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }	
		th { background-color: #f2f2f2; }
	</style>
</head>
<body>

	<nav>
		<div>
			<h2>Painel Administrativo</h2>
			<p>Logado como: ${sessionScope.usuarioLogado.nome} (Admin)</p>
		</div>
		<div>
			<a href="dashboard.jsp">Voltar ao Estoque</a>
			<a href="logout" style="margin-left: 15px;">Sair</a>
		</div>
	</nav>
	
	<hr>
	
	<h3>Gerenciamento de Contas de Usuário</h3>
	
	<table>
		<thead>
			<tr>
				<th>ID</th>
				<th>Nome</th>
				<th>Email</th>
				<th>Função (Role)</th>
				<th>Ações</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="usuario" items="${listaUsuarios}">
				<tr>
					<td>${usuario.id}</td>
					<td>${usuario.nome}</td>
					<td>${usuario.email}</td>
					<td>${usuario.role}</td>
					<td>
						<%-- Delete de usuarios --%>
						<c:if test="${sessionScope.usuarioLogado.id != usuario.id }">
							<a href="admin?action=delete&id=${usuario.id}"
							   onclick="return confirm('ATENÇÃO: Deletar esse usuário também deletará todo o estoque dele. Continuar?')">
							   Deletar
							</a>
						</c:if>
					
						<%-- Caso seja o próprio admin --%>
						<c:if test="${sessionScope.usuarioLogado.id == usuario.id}">
							(Sua conta)
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>

</html>