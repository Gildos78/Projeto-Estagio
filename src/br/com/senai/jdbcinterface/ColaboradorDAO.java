package br.com.senai.jdbcinterface;

import java.util.List;

import com.google.gson.JsonObject;

import br.com.senai.modelo.Colaborador;

public interface ColaboradorDAO {
	public List<JsonObject> buscarPorMatricula(int matricula);
	public Colaborador checkId(int id);
	public boolean alterar(Colaborador colaborador);
	public boolean inserir (Colaborador colaborador);
	public boolean deletar(int id);
	public Colaborador checkIdSenha(int id);
	public boolean alterarSenha(Colaborador colaborador);
}
