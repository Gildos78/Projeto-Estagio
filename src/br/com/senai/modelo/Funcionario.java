package br.com.senai.modelo;

import java.io.Serializable;

public class Funcionario implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	
	private int id;
	private String nome;
	private String senha;
	private String email;

	
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
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
