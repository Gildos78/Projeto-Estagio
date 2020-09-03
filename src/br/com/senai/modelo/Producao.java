package br.com.senai.modelo;

import java.io.Serializable;
import java.util.Date;

public class Producao implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String codigo;
	private String cliente;
	private String dataInicio;
	private String dataFinal;
	private String descricao;
	private float espMin;
	private float espMax;
	private int numAmostras;
	private int subgrupo;
	private int operacaoId;
	private int maquinaId;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}
	public String getDataFinal() {
		return dataFinal;
	}
	public void setDataFinal(String dataFinal) {
		this.dataFinal = dataFinal;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public float getEspMin() {
		return espMin;
	}
	public void setEspMin(float espMin) {
		this.espMin = espMin;
	}
	public float getEspMax() {
		return espMax;
	}
	public void setEspMax(float espMax) {
		this.espMax = espMax;
	}
	public int getNumAmostras() {
		return numAmostras;
	}
	public void setNumAmostras(int numAmostras) {
		this.numAmostras = numAmostras;
	}
	public int getSubgrupo() {
		return subgrupo;
	}
	public void setSubgrupo(int subgrupo) {
		this.subgrupo = subgrupo;
	}
	public int getOperacaoId() {
		return operacaoId;
	}
	public void setOperacaoId(int operacaoId) {
		this.operacaoId = operacaoId;
	}
	public int getMaquinaId() {
		return maquinaId;
	}
	public void setMaquinaId(int maquinaId) {
		this.maquinaId = maquinaId;
	}
}