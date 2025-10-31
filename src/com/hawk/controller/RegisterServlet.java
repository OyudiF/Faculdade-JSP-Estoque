package com.hawk.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hawk.dao.UsuarioDAO;
import com.hawk.model.Usuario;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UsuarioDAO usuarioDAO = new UsuarioDAO();

	
	/**
	 * O formulario usa method="POST", entao implementamos o doPost
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Recebimento de dados do formulario
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");
		
		// Cria um objeto "Model"
		Usuario novoUsuario = new Usuario();
		novoUsuario.setNome(nome);
		novoUsuario.setEmail(email);
		
		// Senha sem Hash
		novoUsuario.setSenha(senha);
		
		try {
			// Salvar no banco
			usuarioDAO.registrar(novoUsuario);
			
			// redireciona para o login com uma mensagem de sucesso
			request.getSession().setAttribute("successMessage", "Conta criada com sucesso! Faça o login");
			
			// Redirect para a pagina de login
			response.sendRedirect("login.jsp");
		} catch (Exception e) {
			
			//Se o email ja existir (erro de UNIQUE do MySQL) o DAO vai lancar uma excecao
			e.printStackTrace();
			
			// redireciona para o register novamente com uma mensagem de erro
			request.setAttribute("errorMessage", "Erro ao se registrar: " + e.getMessage());
			request.getRequestDispatcher("register.jsp").forward(request, response);
		}
	}
}
