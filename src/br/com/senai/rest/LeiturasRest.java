package br.com.senai.rest;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import br.com.senai.bd.Conexao;
import br.com.senai.jdbc.JDBCCadOperacaoDAO;
import br.com.senai.jdbc.JDBCLeiturasDAO;
import br.com.senai.jdbc.JDBCProducaoDAO;
import br.com.senai.modelo.Leituras;
import br.com.senai.modelo.Producao;

@Path("leituras")
public class LeiturasRest extends UtilRest{
	@POST
	@Path("/inserir")
	@Consumes("application/*")
	public Response inserir(String leiturasParam) {
		try {
			Leituras leituras = new Gson().fromJson(leiturasParam, Leituras.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();

			JDBCLeiturasDAO jdbcLeituras = new JDBCLeiturasDAO(conexao);
			boolean retorno  = jdbcLeituras.inserir(leituras);
			String msg="";

			if(retorno) {
				msg = "Leitura cadastrado com sucesso!";
			}else {
				msg = "Erro ao cadastrar leitura.";
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
	public Response buscar(@QueryParam("valorBusca") int  ordem) {
		try {
			List<JsonObject> listaLeituras = new ArrayList<JsonObject>();

			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCLeiturasDAO jdbcLeituras = new JDBCLeiturasDAO(conexao);
			listaLeituras = jdbcLeituras.buscar(ordem);
			conec.fecharConexao();

			String json = new Gson().toJson(listaLeituras);
			return this.buildResponse(json);
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
			JDBCLeiturasDAO jdbcLeituras = new JDBCLeiturasDAO(conexao);

			boolean retorno = jdbcLeituras.deletar(id);

			String msg = "";
			if(retorno) {
				msg="Leitura excluida com sucesso!";
			}else {
				msg="Erro ao excluir leitura!";
			}

			conec.fecharConexao();

			return this.buildResponse(msg);

		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}			
	}
	@GET
	@Path("/buscarCod")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarCod(@QueryParam("id") int  id) {
		try {
			Leituras leituras= new Leituras();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCLeiturasDAO jdbcLeituras = new JDBCLeiturasDAO(conexao);

			leituras = jdbcLeituras.buscarCod(id);

			conec.fecharConexao();
			return this.buildResponse(leituras);

		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}		
	}
	@GET
	@Path("/buscarDetails")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarDetails(@QueryParam("id") int  id) {
		try {
			List<JsonObject> listaLeituras = new ArrayList<JsonObject>();

			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCLeiturasDAO jdbcLeituras = new JDBCLeiturasDAO(conexao);
			listaLeituras = jdbcLeituras.buscarDetails(id);
			conec.fecharConexao();

			String json = new Gson().toJson(listaLeituras);
			return this.buildResponse(json);
		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}				
	}	
	
}

