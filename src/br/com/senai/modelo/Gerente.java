package br.com.senai.modelo;

import java.io.Serializable;

public class Gerente implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	
	private int id;
	private String nome;
	private int matricula;
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
	public int getMatricula() {
		return matricula;
	}
	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}	
}
