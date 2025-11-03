package com.hawk.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hawk.model.Usuario;

//@WebFilter("/*")
public class AuthFilter implements Filter {
	
	public void init(FilterConfig fConfig) throws ServletException {}
	public void destroy() {}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		// Casting
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		// Pegar o caminho do projeto
		String contextPath = httpRequest.getContextPath();
		
		// Pegar a URL do que o usuario esta tentando acessar
		String uri = httpRequest.getRequestURI();
		String path = uri.substring(contextPath.length());
		
		// Pegar a sessao (Sem criar uma nova se ela nao exisitir)
		HttpSession session = httpRequest.getSession(false);
		
		// Pegar o usuario da sessao (se existir)
		Usuario usuarioLogado = (session != null) ? (Usuario) session.getAttribute("usuarioLogado") : null;
		
		
		// Logica de filtro
		boolean isPaginaPublica = path.equals("/login.jsp") ||
									path.equals("/register.jsp") ||
									path.equals("/login") ||
									path.equals("/register") ||
									path.startsWith("/css/") ||
									path.startsWith("/js/");
		
		if (isPaginaPublica) {
			chain.doFilter(request, response);
			return;
		}
		
		// Se nao for publica e nao esta logado
		if (usuarioLogado == null) {
			// redireciona para o login
			httpResponse.sendRedirect(contextPath + "/login.jsp");
			return;
		}
		
		// Se esta aqui, o usuario esta logado
		boolean isPaginaAdmin = path.equals("/admin") || path.equals("/admin.jsp");
		
		if(isPaginaAdmin) {
			
			if ("admin".equals(usuarioLogado.getRole())) {
				// Se for deixa passar
				chain.doFilter(request, response);
			} else {
				// Acesso negado
				httpResponse.sendRedirect(contextPath + "/dashboard.jsp");
			}
			return;
		}
		
		// Paginas logadas
		chain.doFilter(request, response);
	}
}
