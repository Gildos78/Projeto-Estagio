package br.com.senai.jdbcinterface;

import java.util.List;

import com.google.gson.JsonObject;

import br.com.senai.modelo.Producao;

public interface ProducaoDAO {
	public boolean inserir (Producao producao);
	public List<JsonObject> buscar(String cliente);
	public List<JsonObject> buscarDes(String cliente);
	public List<JsonObject> buscarData (String dataIni, String dataFin);
	public boolean deletar(int id);
	public Producao buscarCodigo(int id);
	public Producao buscarAm(int id);
}