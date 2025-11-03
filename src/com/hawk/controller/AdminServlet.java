package com.hawk.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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
 * Servlet implementation class AdminServlet
 */
//@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UsuarioDAO usuarioDAO = new UsuarioDAO();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Controle de acesso
		HttpSession session = request.getSession();
		Usuario usuarioLogado = null;
		
		if(session != null) {
			usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
		}
		
		if(usuarioLogado == null) {
			response.sendRedirect("login.jsp");
			return;
		}
		
		if(!"admin".equals(usuarioLogado.getRole())) {
			
			response.sendRedirect("dashboard.jsp");
			return;
		}
		
		// Controle de Acao
		String action = request.getParameter("action");
		
		// Se nula, padrao sera list
		if(action == null) {
			action = "list";
		}
		
		try {
			switch (action) {
				case "delete":
					deletarUsuario(request, response, usuarioLogado);
					break;
				
				default:
					listarUsuarios(request, response);
					break;
			}
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}
	
	

	private void deletarUsuario(HttpServletRequest request, HttpServletResponse response, Usuario adminLogado) throws SQLException, IOException {
		
		int idParaDeletar = Integer.parseInt(request.getParameter("id"));
		
		int adminId = adminLogado.getId();
		
		if (idParaDeletar != adminId) {
			usuarioDAO.deletarUsuario(idParaDeletar);
		}
		
		response.sendRedirect("admin?action=list");
	}

	private void listarUsuarios(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
		List<Usuario> listaUsuarios = usuarioDAO.listarTodos();
		
		// Prepara o atributo que o admin.jsp (com <c:forEach>) espera
		request.setAttribute("listaUsuarios", listaUsuarios);
		
		
		// Encaminhamento para a view
		RequestDispatcher dispatcher = request.getRequestDispatcher("admin.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
