package com.hawk.model;

public class Produto {
	
	private int id;
	private String nome;
	private int quantidade;
	
	private int usuarioId;
	
	private double preco;
	
	public Produto() {
	
	}

	public Produto(String nome, int quantidade, int usuarioId, double preco) {
		this.nome = nome;
		this.quantidade = quantidade;
		this.usuarioId = usuarioId;
		this.preco = preco;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	@Override
	public String toString() {
		return "Produto [id=" + id + ", nome=" + nome + ", quantidade=" + quantidade + ", usuarioId=" + usuarioId + "]";
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}
}
