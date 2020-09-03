package br.com.senai.rest;
import java.sql.Connection;


import javax.ws.rs.Consumes;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import javax.ws.rs.core.Response;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;


import br.com.senai.bd.Conexao;
import br.com.senai.jdbc.JDBCFuncionarioDAO;
import br.com.senai.modelo.Funcionario;



@Path("funcionario")
public class FuncionarioRest extends UtilRest{

	@POST
	@Path("/inserir")
	@Consumes("application/*")
	public Response inserir(String funcionarioParam) {
		try {
			Funcionario funcionario = new Gson().fromJson(funcionarioParam, Funcionario.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			
			JDBCFuncionarioDAO jdbcFuncionario = new JDBCFuncionarioDAO(conexao);
			boolean retorno  = jdbcFuncionario.inserir(funcionario);
			String msg="";
			
			if(retorno) {
				msg = "Colaborador cadastrado com sucesso!";
			}else {
				msg = "Erro ao cadastrar colaborador.";
			}
			
			conec.fecharConexao();
			
			return this.buildResponse(msg);
		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	@GET
	@Path("/checkMatricula")
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response checkMat(@QueryParam("matricula")int matricula) {

		try {
			Funcionario funcionario = new Funcionario();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCFuncionarioDAO jdbcFuncionario = new JDBCFuncionarioDAO(conexao);
			funcionario = jdbcFuncionario.checkMatricula(matricula);
			
			conec.fecharConexao();
			return this.buildResponse(funcionario);

		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	@PUT
	@Path("/alterar")
	@Consumes("application/*")
	public Response alterar(String funcionarioParam) {
		try {
			Funcionario funcionario = new Gson().fromJson(funcionarioParam, Funcionario.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCFuncionarioDAO jdbcFuncionario = new JDBCFuncionarioDAO(conexao);

			boolean retorno = jdbcFuncionario.alterar(funcionario);

			String msg="";
			if (retorno) {
				msg = "Cadastro alterado com sucesso!";
			}else {
				msg = "Erro ao alterar cadastro";
			}
			conec.fecharConexao();
			return this.buildResponse(msg);
		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	@GET
	@Path("/checkMatriculaSenha")
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response checkMatriculaSenha(@QueryParam("matricula")int matricula) {

		try {
			Funcionario funcionario = new Funcionario();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCFuncionarioDAO jdbcFuncionario = new JDBCFuncionarioDAO(conexao);
			funcionario = jdbcFuncionario.checkMatriculaSenha(matricula);
			
			conec.fecharConexao();
			return this.buildResponse(funcionario);

		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	@PUT
	@Path("/alterarSenha")
	@Consumes("application/*")
	public Response alterarSenha(String funcionarioParam) {
		try {
			Funcionario funcionario = new Gson().fromJson(funcionarioParam, Funcionario.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCFuncionarioDAO jdbcFuncionario = new JDBCFuncionarioDAO(conexao);

			boolean retorno = jdbcFuncionario.alterarSenha(funcionario);

			String msg="";
			if (retorno) {
				msg = "Senha alterada com sucesso!";
			}else {
				msg = "Erro ao alterar senha";
			}
			conec.fecharConexao();
			return this.buildResponse(msg);
		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
}
