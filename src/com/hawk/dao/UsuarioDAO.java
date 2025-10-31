package com.hawk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hawk.model.Usuario;
import com.hawk.web.jdbc.ConnectionFactory;

public class UsuarioDAO {

	/**
	 * REGISTRAR: Faz um INSERT na tabela 'usuarios'
	 * Recebe um objeto Usuario preenchido (Sem o ID)
	 */
	public void registrar(Usuario usuario) {
		String sql = "INSERT INTO usuarios (nome, email, senha, role) VALUES (?, ?, ?, ?)";
		
		try (Connection conexao = ConnectionFactory.getConnection(); PreparedStatement ps = conexao.prepareStatement(sql)) {
			
			ps.setString(1, usuario.getNome());
			ps.setString(1, usuario.getEmail());
			ps.setString(1, usuario.getSenha());
			ps.setString(4, "user");
			
			ps.executeUpdate();
			
			System.out.println("Usuario registrado com sucesso");
			
			
		} catch (SQLException e) {
			System.err.println("Erro ao registrar usuario: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * VALIDAR LOGIN: Faz um SELECT e retorna o usuario se a senha bater
	 * Retorna o objeto Usuario se o login for valido, ou null se for invalido
	 */
	public Usuario validarLogin(String email, String senha) {
		String sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";
		Usuario usuarioLogado = null;
		
		try (Connection conexao = ConnectionFactory.getConnection(); PreparedStatement ps = conexao.prepareStatement(sql)) {
			
			ps.setString(1, email);
			ps.setString(2, senha);
			
			try (ResultSet rs = ps.executeQuery()) {
				
				if (rs.next()) {
					
					usuarioLogado = new Usuario();
					usuarioLogado.setId(rs.getInt("id"));
					usuarioLogado.setNome(rs.getString("nome"));
					usuarioLogado.setEmail(rs.getString("email"));
					usuarioLogado.setRole(rs.getString("role"));
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
