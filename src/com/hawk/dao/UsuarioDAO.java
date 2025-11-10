package com.hawk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

import com.hawk.model.Usuario;
import com.hawk.web.jdbc.ConnectionFactory;

public class UsuarioDAO {

	/**
	 * REGISTRAR: Faz um INSERT na tabela 'usuarios'
	 * Recebe um objeto Usuario preenchido (Sem o ID)
	 */
	public void registrar(Usuario usuario) {
		// LINHA DE TESTE 3:
	    System.out.println("--- UsuarioDAO: método registrar() foi chamado! ---");
	    System.out.println("--- Registrando usuário: " + usuario.getNome() + " ---");
		
		String sql = "INSERT INTO usuarios (nome, email, senha, role) VALUES (?, ?, ?, ?)";
		
		Connection conexao = null;
		PreparedStatement ps = null;
		
		try {
			
			conexao = ConnectionFactory.getConnection();
			conexao.setAutoCommit(false);
			
			String senhaComHash = BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt());
			
			ps = conexao.prepareStatement(sql);
			ps.setString(1, usuario.getNome());
			ps.setString(2, usuario.getEmail());
			ps.setString(3, senhaComHash);
			ps.setString(4, "user");
			
			int linhasAfetadas = ps.executeUpdate();
			
			if (linhasAfetadas > 0) {
				
				conexao.commit();
				System.out.println("-- UsuarioDAO: Commit realizado! Usuario salvo --");
			} else {
				
				conexao.rollback();
				System.out.println("-- UsuarioDAO: Rollback realizado. Nenhuma Linha afetada --");
			}
			
			System.out.println("Usuario registrado com sucesso");
			
			
		} catch (SQLException e) {
			System.out.println("-- Erro UsuarioDAO.registrar --");
			
			try {
				if (conexao != null) {
					System.err.println("-- UsuarioDAO: Rollback por excecao --");
					conexao.rollback();
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			
			throw new RuntimeException("Erro ao registrar no banco", e);
		} finally {
			try {
				if (ps != null) ps.close();
				if (conexao != null) conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * VALIDAR LOGIN: Faz um SELECT e retorna o usuario se a senha bater
	 * Retorna o objeto Usuario se o login for valido, ou null se for invalido
	 */
	public Usuario validarLogin(String email, String senhaDigitada) {
		String sql = "SELECT * FROM usuarios WHERE email = ?";
		Usuario usuarioLogado = null;
		
		try (Connection conexao = ConnectionFactory.getConnection(); PreparedStatement ps = conexao.prepareStatement(sql)) {
			
			ps.setString(1, email);
			
			try (ResultSet rs = ps.executeQuery()) {
				
				if (rs.next()) {
					
					String hashSalvoNoBanco = rs.getString("senha");
					
					if (BCrypt.checkpw(senhaDigitada, hashSalvoNoBanco)) {
						usuarioLogado = new Usuario();
						usuarioLogado.setId(rs.getInt("id"));
						usuarioLogado.setNome(rs.getString("nome"));
						usuarioLogado.setEmail(rs.getString("email"));
						usuarioLogado.setRole(rs.getString("role"));
					}
				}
			}
			
		} catch (SQLException e) {
			System.err.println("Erro ao validar login: " + e.getMessage());
			e.printStackTrace();
		}
		
		return usuarioLogado;
	}
	
	public List<Usuario> listarTodos() {
		String sql = "SELECT id, nome, email, role FROM usuarios";
		List<Usuario> usuarios = new ArrayList<>();
		
		try (Connection conexao = ConnectionFactory.getConnection(); PreparedStatement ps = conexao.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			
			while (rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setId(rs.getInt("id"));
				usuario.setNome(rs.getString("nome"));
				usuario.setEmail(rs.getString("email"));
				usuario.setRole(rs.getString("role"));
				
				usuarios.add(usuario);
			}
			
			
		} catch (SQLException e) {
			System.err.println("Erro ao listar usuarios: " + e.getMessage());
			e.printStackTrace();
		}
		
		return usuarios;
	}
	
	public void deletarUsuario(int id) {
		String sql = "DELETE FROM usuarios WHERE id = ?";
		
		try (Connection conexao = ConnectionFactory.getConnection(); PreparedStatement ps = conexao.prepareStatement(sql)) {
			
			ps.setInt(1, id);
			
			int linhasAfetadas = ps.executeUpdate();
			
			if (linhasAfetadas > 0) {
				System.out.println("Usuario ID " + id + " deletado com sucesso");
			} else {
				System.out.println("Nenhum usuario encontrado com o ID " + id);
			}
			
			
		} catch (SQLException e) {
			
		}
	}
}
