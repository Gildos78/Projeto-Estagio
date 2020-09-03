package br.com.senai.modelo;

import java.io.Serializable;
import java.util.Date;

public class Leituras implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String data;
	private String obs;
	private float valor;
	private int lmQtd;
	private int count;
	private int operadorId;
	private int producaoId;
	private String producao;
	private String operador;
	private int subgrupo;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	
	public int getLmQtd() {
		return lmQtd;
	}
	public void setLmQtd(int lmQtd) {
		this.lmQtd = lmQtd;
	}
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getOperadorId() {
		return operadorId;
	}
	public void setOperadorId(int operadorId) {
		this.operadorId = operadorId;
	}
	public int getProducaoId() {
		return producaoId;
	}
	public void setProducaoId(int producaoId) {
		this.producaoId = producaoId;
	}
	public String getProducao() {
		return producao;
	}
	public void setProducao(String producao) {
		this.producao = producao;
	}
	public String getOperador() {
		return operador;
	}
	public void setOperador(String operador) {
		this.operador = operador;
	}
	public int getSubgrupo() {
		return subgrupo;
	}
	public void setSubgrupo(int subgrupo) {
		this.subgrupo = subgrupo;
	}
	
}