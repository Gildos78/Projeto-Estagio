package br.com.senai.jdbcinterface;

import java.util.List;

import com.google.gson.JsonObject;

import br.com.senai.modelo.CadOperacao;



public interface CadOperacaoDAO {
	public boolean inserir (CadOperacao cadOperacao);
	public List<JsonObject> buscarPorNome(String nome);
	public CadOperacao checkId(int id);
	public boolean alterar(CadOperacao cadOperacao);
	public boolean deletar(int id);
	public List<CadOperacao> buscarSel();
}
