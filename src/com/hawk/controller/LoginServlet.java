package com.hawk.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hawk.dao.UsuarioDAO;
import com.hawk.model.Usuario;

/**
 * Servlet implementation class LoginServlet
 */
//@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");
		
		try {
			// Chama o dao para validacao do login
			Usuario usuario = usuarioDAO.validarLogin(email, senha);
			
			// Verifica se o login foi validado
			if (usuario != null) {
				// Sucesso
				
				//Cria a sessao
				HttpSession session = request.getSession();
				
				// Guarda o objeto do usuario na sessao
				session.setAttribute("usuarioLogado", usuario);
				
				if("admin".equals(usuario.getRole())) {
					// Redireciona para a dashboard de admin (Pagina Admin)
					response.sendRedirect("admin");
				} else {
					// Redireciona para o dashboard (Pagina principal logada) Usuario comum
					response.sendRedirect("produto");
				}
			} else {
				// erro de login
				request.setAttribute("errorMessage", "Email ou senha invalidos");
				
				// Re-ecaminha (forward) para o login.jsp
				RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
				rd.forward(request, response);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Erro interno do servidor.");
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);
		}
	}

}
