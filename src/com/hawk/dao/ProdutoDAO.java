package com.hawk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hawk.model.Produto;
import com.hawk.web.jdbc.ConnectionFactory;

public class ProdutoDAO {
	
	/**
	 * ADICIONAR: faz um INSERT na tabela 'produtos'
	 * O objeto 'produto' ja deve vir com o 'usuarioId' preenchido
	 */
	public void adicionar(Produto produto) {
		String sql = "INSERT INTO produtos (nome, quantidade, usuario_id, preco) VALUES (?, ?, ?, ?)";
		
		try (Connection conexao = ConnectionFactory.getConnection(); PreparedStatement ps = conexao.prepareStatement(sql)) {
			
			ps.setString(1, produto.getNome());
			ps.setInt(2, produto.getQuantidade());
			ps.setInt(3, produto.getUsuarioId());
			ps.setDouble(4, produto.getPreco());
			
			ps.executeUpdate();
			System.out.println("Produto '" + produto.getNome() + "' adicionado com sucesso.");
		} catch (SQLException e) {
			System.out.println("Erro ao adicionar produto: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * LISTAR PRO USUARIO: Faz um SELECT... WHERE usuario_id = ?
	 * Retorna uma lista de produtos apenas par ao ID do usuario informado
	 */
	public List<Produto> listarPorUsuario(int usuarioId) {
		String sql = "SELECT * FROM produtos WHERE usuario_id = ?";
		List<Produto> produtos = new ArrayList<>();
		
		try (Connection conexao = ConnectionFactory.getConnection(); PreparedStatement ps = conexao.prepareStatement(sql)) {
			ps.setInt(1, usuarioId);
			
			try (ResultSet rs = ps.executeQuery()) {
				// Itera sobre todos os resultados encontrados
				while (rs.next()) {
					// Preenche o objeto Produto com os dados do banco
					Produto produto = new Produto();
					produto.setId(rs.getInt("id"));
					produto.setNome(rs.getString("nome"));
					produto.setQuantidade(rs.getInt("quantidade"));
					produto.setUsuarioId(rs.getInt("usuario_id"));
					produto.setPreco(rs.getDouble("preco"));
					
					// Adiciona o produto na lista de retorno
					produtos.add(produto);
				}
			}
			
		} catch (SQLException e) {
			System.out.println("Erro ao listar produtos: " + e.getMessage());
			e.printStackTrace();
		}
		
		return produtos;
	}
	
	/**
	 * ATUALIZAR: Faz um UPDATE... WHERE id = ?
	 * O objeto 'produto' deve vir com o 'id' e 'usuarioId' corretos
	 */
	public void atualizar(Produto produto) {
		// Clausula de Seguranca "AND usuario_id = ?"
		String sql = "UPDATE produtos SET nome = ?, quantidade = ?, preco = ?, WHERE id = ? AND usuario_id = ?";
		
		try (Connection conexao = ConnectionFactory.getConnection(); PreparedStatement ps = conexao.prepareStatement(sql)) {
			
			ps.setString(1, produto.getNome());
			ps.setInt(2, produto.getQuantidade());
			ps.setDouble(3, produto.getPreco());
			ps.setInt(4, produto.getId());
			ps.setInt(5, produto.getUsuarioId());
			
			int linhasAfetadas = ps.executeUpdate();
			
			if (linhasAfetadas > 0) {
				System.out.println("Produto ID " + produto.getId() + " atualizado");
			} else {
				System.out.println("Produto nao encontrado ou nao pertence ao usuario");
			}
		} catch (SQLException e) {
			System.out.println("Erro ao atualizar o produto: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * DELETAR: Faz um DELETE... WHERE id = ?
	 * Requer o ID do produto e o ID do usuario dono.
	 */
	public void deletar(int produtoId, int usuarioId) {
		// Mesma clausula de seguranca
		String sql = "DELETE FROM produtos WHERE id = ? AND usuario_id = ?";
		
		try (Connection conexao = ConnectionFactory.getConnection(); PreparedStatement ps = conexao.prepareStatement(sql)) {
			
			ps.setInt(1, produtoId);
			ps.setInt(2, usuarioId);
			
			int linhasAfetadas = ps.executeUpdate();
			
			if(linhasAfetadas > 0) {
				System.out.println("Produto ID " + produtoId + " deletado.");
			} else {
				System.out.println("Produto nao encontrado ou nao pertence ao usuario");
			}
		} catch (SQLException e) {
			System.out.println("Erro ao deletar produto: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * BUSCA UNICA: Buscar um unico produto (para o formulario de edicao)
	 * O Servlet precisara para preencher o formulario de "Editar"
	 */
	public Produto buscarPorId(int produtoId, int usuarioId) {
		String sql = "SELECT * FROM produtos WHERE id = ? AND usuario_id = ?";
		Produto produto = null;
		
		try (Connection conexao = ConnectionFactory.getConnection(); PreparedStatement ps = conexao.prepareStatement(sql)) {
			
			ps.setInt(1, produtoId);
			ps.setInt(2, usuarioId);
			
			try (ResultSet rs = ps.executeQuery()) {
				// If por que se espera apenas 1 resultado
				if (rs.next()) {
					produto = new Produto();
					produto.setId(rs.getInt("id"));
					produto.setNome(rs.getString("nome"));
					produto.setQuantidade(rs.getInt("quantidade"));
					produto.setUsuarioId(rs.getInt("usuario_id"));
					produto.setPreco(rs.getDouble("preco"));
				}
			} 
		} catch (SQLException e) {
			System.out.println("Erro ao buscar produto por ID: " + e.getMessage());
			e.printStackTrace();
		}
		
		return produto;
	}
	
	/**
	 * Retorna o valor total do estoque (Preco * quantidade)
	 * @param usuarioId
	 * @return O valor total (double) do estoque
	 */
	public double getValorTotalEstoque(int usuarioId) {
		String sql = "SELECT SUM(quantidade * preco) AS  valor_total FROM produtos WHERE usuario_id = ?";
		
		double valorTotal = 0.0;
		
		try (Connection conexao = ConnectionFactory.getConnection(); PreparedStatement ps = conexao.prepareStatement(sql)) {
			
			ps.setInt(1, usuarioId);
			
			try (ResultSet rs = ps.executeQuery()) {
				
				if (rs.next()) {
					
					valorTotal = rs.getDouble("valor_total");
				}
			}
		} catch (SQLException e) {
			System.err.println("Erro ao calcular valor total do estoque: " + e.getMessage());
		}
		
		return valorTotal;
	}
	
	
	
	
	
}
