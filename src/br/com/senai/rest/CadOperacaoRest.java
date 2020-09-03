package br.com.senai.rest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.JsonObject;


import br.com.senai.bd.Conexao;
import br.com.senai.jdbc.JDBCCadOperacaoDAO;
import br.com.senai.jdbc.JDBCColaboradorDAO;
import br.com.senai.modelo.CadOperacao;
import br.com.senai.modelo.Colaborador;





@Path("cadOperacao")
public class CadOperacaoRest extends UtilRest{
	@POST
	@Path("/inserir")
	@Consumes("application/*")
	public Response inserir(String cadOperacaoParam) {
		try {
			CadOperacao cadOperacao = new Gson().fromJson(cadOperacaoParam, CadOperacao.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			
			JDBCCadOperacaoDAO jdbcCadOperacao = new JDBCCadOperacaoDAO(conexao);
			boolean retorno  = jdbcCadOperacao.inserir(cadOperacao);
			String msg="";
			
			if(retorno) {
				msg = "Opera��o cadastrado com sucesso!";
			}else {
				msg = "Erro ao cadastrar opera��o.";
			}
			
			conec.fecharConexao();
			
			return this.buildResponse(msg);
		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@GET
	@Path("/buscar")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarPorNome(@QueryParam("valorBusca") String  nome) {
		try {
			List<JsonObject> listaCadOperacoes = new ArrayList<JsonObject>();
			
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCCadOperacaoDAO jdbcCadOperacao = new JDBCCadOperacaoDAO(conexao);
			listaCadOperacoes = jdbcCadOperacao.buscarPorNome(nome);
			conec.fecharConexao();
			
			String json = new Gson().toJson(listaCadOperacoes);
			return this.buildResponse(json);
		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}				
	}
	@GET
	@Path("/checkId")
	@Produces(MediaType.APPLICATION_JSON)

	public Response checkId(@QueryParam("id")int id) {

		try {
			CadOperacao cadOperacao= new CadOperacao();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCCadOperacaoDAO jdbcCadOperacao = new JDBCCadOperacaoDAO(conexao);

			cadOperacao = jdbcCadOperacao.checkId(id);

			conec.fecharConexao();
			return this.buildResponse(cadOperacao);

		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	@PUT
	@Path("/alterar")
	@Consumes("application/*")
	public Response alterar(String cadOperacaoParam) {
		try {
			CadOperacao cadOperacao = new Gson().fromJson(cadOperacaoParam, CadOperacao.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCCadOperacaoDAO jdbcCadOperacao = new JDBCCadOperacaoDAO(conexao);

			boolean retorno = jdbcCadOperacao.alterar(cadOperacao);

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
	@DELETE
	@Path("/excluir/{id}")
	@Consumes("application/*")
	public Response excluir(@PathParam("id") int id) {
		try {
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCCadOperacaoDAO jdbcCadOperacao = new JDBCCadOperacaoDAO(conexao);
			
			boolean retorno = jdbcCadOperacao.deletar(id);
			
			String msg = "";
			if(retorno) {
				msg="Opera��o exclu�da com sucesso!";
			}else {
				msg="Erro ao excluir opera��o!";
			}
			
			conec.fecharConexao();
			
			return this.buildResponse(msg);
			
		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}			
	}
	@GET
	@Path("/buscarSel")
	@Produces(MediaType.APPLICATION_JSON)

	public Response buscarSel() {

		try {
			List<CadOperacao> listaOperacoes = new ArrayList<CadOperacao>();

			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCCadOperacaoDAO jdbcCadOperacao = new JDBCCadOperacaoDAO(conexao);
			listaOperacoes = jdbcCadOperacao.buscarSel();
			conec.fecharConexao();
			return this.buildResponse(listaOperacoes);

		}catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
}
