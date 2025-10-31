package com.hawk.model;

public class Produto {
	
	private int id;
	private String nome;
	private int quantidade;
	
	private int usuarioId;
	
	public Produto() {
	
	}

	public Produto(int id, String nome, int quantidade, int usuarioId) {
		this.id = id;
		this.nome = nome;
		this.quantidade = quantidade;
		this.usuarioId = usuarioId;
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
	
	
	
	
}
