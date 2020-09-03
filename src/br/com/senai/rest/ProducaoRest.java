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
import br.com.senai.jdbc.JDBCProducaoDAO;
import br.com.senai.modelo.Producao;

@Path("producao")
public class ProducaoRest extends UtilRest{
	@POST
	@Path("/inserir")
	@Consumes("application/*")
	public Response inserir(String producaoParam) {
		try {
			Producao producao = new Gson().fromJson(producaoParam, Producao.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			
			JDBCProducaoDAO jdbcProducao = new JDBCProducaoDAO(conexao);
			boolean retorno  = jdbcProducao.inserir(producao);
			String msg="";
			
			if(retorno) {
				msg = "Produto cadastrado com sucesso!";
				
			}else {
				msg = "Erro ao cadastrar produto.";
				
			}
			System.out.println(msg);
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
	public Response buscar(@QueryParam("valorBusca") String  cliente) {
		try {
			List<JsonObject> listaProducoes = new ArrayList<JsonObject>();
			
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCProducaoDAO jdbcProducao = new JDBCProducaoDAO(conexao);
			listaProducoes = jdbcProducao.buscar(cliente);
		
			conec.fecharConexao();
			
			String json = new Gson().toJson(listaProducoes);
			return this.buildResponse(json);
		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}				
	}
	@GET
	@Path("/buscarDes")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarDes(@QueryParam("valorBusca") String  cliente) {
		try {
			List<JsonObject> listaProducoes = new ArrayList<JsonObject>();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCProducaoDAO jdbcProducao = new JDBCProducaoDAO(conexao);
			listaProducoes = jdbcProducao.buscarDes(cliente);
		
			conec.fecharConexao();
			
			String json = new Gson().toJson(listaProducoes);
			return this.buildResponse(json);
		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}				
	}

	@GET
	@Path("/buscarData")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarData(@QueryParam("valorDataIni") String  dataIni,
						   @QueryParam("valorDataFin") String  dataFin) {
		try {
			List<JsonObject> listaProducoes = new ArrayList<JsonObject>();
			
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCProducaoDAO jdbcProducao = new JDBCProducaoDAO(conexao);
			listaProducoes = jdbcProducao.buscarData(dataIni, dataFin);
		
			conec.fecharConexao();
			
			String json = new Gson().toJson(listaProducoes);
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
			JDBCProducaoDAO jdbcProducao = new JDBCProducaoDAO(conexao);
			
			boolean retorno = jdbcProducao.deletar(id);
			
			String msg = "";
			if(retorno) {
				msg="Produção excluída com sucesso!";
			}else {
				msg="Erro ao excluir produção!";
			}
			
			conec.fecharConexao();
			
			return this.buildResponse(msg);
			
		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}			
	}
	@GET
	@Path("/buscarSelP")
	@Produces(MediaType.APPLICATION_JSON)

	public Response buscarSelP() {

		try {
			List<Producao> listaProducao = new ArrayList<Producao>();

			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCProducaoDAO jdbcProducao = new JDBCProducaoDAO(conexao);
			listaProducao = jdbcProducao.buscarSelP();
			conec.fecharConexao();
			return this.buildResponse(listaProducao);

		}catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	@GET
	@Path("/buscarCodigo")
	@Produces(MediaType.APPLICATION_JSON)

	public Response checkId(@QueryParam("id")int id) {

		try {
			Producao producao= new Producao();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCProducaoDAO jdbcProducao = new JDBCProducaoDAO(conexao);

			producao = jdbcProducao.buscarCodigo(id);

			conec.fecharConexao();
			return this.buildResponse(producao);

		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	@GET
	@Path("/buscarAm")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarAm(@QueryParam("id") int  id) {
		try {
			Producao producao= new Producao();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCProducaoDAO jdbcProducao = new JDBCProducaoDAO(conexao);

			producao = jdbcProducao.buscarAm(id);
			conec.fecharConexao();
			return this.buildResponse(producao);

		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}		
	}
	
}
