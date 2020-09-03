package br.com.senai.jdbcinterface;

import java.util.List;

import com.google.gson.JsonObject;

import br.com.senai.modelo.Leituras;
import br.com.senai.modelo.Producao;

public interface LeiturasDAO {
	public boolean inserir (Leituras leituras);
	public List<JsonObject> buscar(int ordem);
	public boolean deletar(int id);
	public Leituras buscarCod(int id);
	public List<JsonObject> buscarDetails(int id);
}