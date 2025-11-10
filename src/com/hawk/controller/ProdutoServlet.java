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

import com.hawk.dao.ProdutoDAO;
import com.hawk.model.Produto;
import com.hawk.model.Usuario;

/**
 * Servlet implementation class ProdutoServlet
 */
//@WebServlet("/produto")
public class ProdutoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Instancia o DAO que sera usado por todos os metodos
	private ProdutoDAO produtoDAO = new ProdutoDAO();
       
    public ProdutoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Chama o metodo central que processa a logica
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Chama o metodo centra que processa a logica
		processRequest(request, response);
	}
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Controle de Acesso
		HttpSession session = request.getSession();
		
		if(session == null || session.getAttribute("usuarioLogado") == null) {
			
			response.sendRedirect("login.jsp");
			return;
		}
		
		// Se o usuario esta logado, pega o objeto
		Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
		int usuarioId = usuarioLogado.getId();
		
		// Controle da acao
		String action = request.getParameter("action");
		
		// Se nenhuma acao for especificada, a acao padrao e "list"
		if(action == null) {
			action = "list";
		}
		
		// Try-Catch para captura de erros
		try {
			
			// Switch decide qual o bloco de codigo executar
			switch (action) {
				case "new":
					// Acao: mostrar o formulario de 'Adicionar Novo'
					mostrarFormularioNovo(request, response);
					break;
					
				case "insert":
					// Acao: Inserir um novo produto no banco (vem do doPost)
					inserirProduto(request, response, usuarioId);
					break;
					
				case "delete":
					// Acao: Deletar um produto (vem do doGet)
					deletarProduto(request, response, usuarioId);
					break;
					
				case "edit":
					// Acao: Mostrar o formulario de "Editar" (preenchido)
					mostrarFormularioEdicao(request, response, usuarioId);
					break;
					
				case "update":
					// Acao: Atualizar um produto no banco (Vem do doPost)
					atualizarProduto(request, response, usuarioId);
					break;
					
				default:
					// Acao padrao: Listar todos os produtos
					listarProdutos(request, response, usuarioId);
					break;
			}
			
		} catch (SQLException e) {
			// Dando erro no Sql, lanca uma excecao do Servlet
			throw new ServletException(e);
		}
	}
	
	// Metodos de acao
	
	// Listar
	private void listarProdutos(HttpServletRequest request, HttpServletResponse response, int usuarioId) throws SQLException, IOException, ServletException {
		
		List<Produto> listaProdutos = produtoDAO.listarPorUsuario(usuarioId);
		
		double valorTotal = produtoDAO.getValorTotalEstoque(usuarioId);
		
		System.out.println("--- ProdutoServlet (listarProdutos): O DAO retornou uma lista com " + listaProdutos.size() + " produtos.");
		
		request.setAttribute("listaProdutos", listaProdutos);
		
		request.setAttribute("valorTotalEstoque", valorTotal);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("dashboard.jsp");
		dispatcher.forward(request, response);
	}
	
	// Adicionar
	private void mostrarFormularioNovo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Limpa qualquer produto antigo que possa estar no request (vindo de uma edicao)
		request.removeAttribute("produto");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("produto-form.jsp");
		dispatcher.forward(request, response);
	}
	
	
	// Editar
	public void mostrarFormularioEdicao(HttpServletRequest request, HttpServletResponse response, int usuarioId) throws SQLException, IOException, ServletException {
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		Produto produtoExistente = produtoDAO.buscarPorId(id, usuarioId);
		
		request.setAttribute("produto", produtoExistente);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("produto-form.jsp");
		dispatcher.forward(request, response);
	}
	
	// Inserir
	private void inserirProduto(HttpServletRequest request, HttpServletResponse response, int usuarioId) throws ServletException, IOException {
		
		String nome = request.getParameter("nome");
		int quantidade = Integer.parseInt(request.getParameter("quantidade"));
		double preco = Double.parseDouble(request.getParameter("preco"));
		
		Produto novoProduto = new Produto();
		
		novoProduto.setNome(nome);
	    novoProduto.setQuantidade(quantidade);
	    novoProduto.setPreco(preco);
	    novoProduto.setUsuarioId(usuarioId);
		
		produtoDAO.adicionar(novoProduto);
		
		// Redireciona para acao "list" (limpa o formulario)
		response.sendRedirect("produto?action=list");
	}
	
	// Atualizar
	private void atualizarProduto(HttpServletRequest request, HttpServletResponse response, int usuarioId) throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("id"));
		String nome = request.getParameter("nome");
		int quantidade = Integer.parseInt(request.getParameter("quantidade"));
		double preco = Double.parseDouble(request.getParameter("preco"));
		
		Produto produto = new Produto();
		
		produto.setId(id);
	    produto.setNome(nome);
	    produto.setQuantidade(quantidade);
	    produto.setPreco(preco);
	    produto.setUsuarioId(usuarioId);
		produto.setId(id);
		
		produtoDAO.atualizar(produto);
		
		response.sendRedirect("produto?action=list");
	}
	
	private void deletarProduto(HttpServletRequest request, HttpServletResponse response, int usuarioId) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		
		produtoDAO.deletar(id, usuarioId);
		
		response.sendRedirect("produto?action=list");
	}
}
