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
import br.com.senai.jdbc.JDBCCadMaquinaDAO;
import br.com.senai.jdbc.JDBCColaboradorDAO;
import br.com.senai.jdbc.JDBCFuncionarioDAO;
import br.com.senai.modelo.CadMaquina;
import br.com.senai.modelo.Colaborador;
import br.com.senai.modelo.Funcionario;




@Path("colaborador")
public class ColaboradorRest extends UtilRest{
	
	@GET
	@Path("/buscar")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarPorMatricula(@QueryParam("valorBusca") int  matricula) {
		try {
			List<JsonObject> listaColaboradores = new ArrayList<JsonObject>();
			
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCColaboradorDAO jdbcColaborador = new JDBCColaboradorDAO(conexao);
			listaColaboradores = jdbcColaborador.buscarPorMatricula(matricula);
			conec.fecharConexao();
			
			String json = new Gson().toJson(listaColaboradores);
			return this.buildResponse(json);
		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}				
	}
	
	
	@POST
	@Path("/inserir")
	@Consumes("application/*")
	public Response inserir(String colaboradorParam) {
		try {
			Colaborador colaborador = new Gson().fromJson(colaboradorParam, Colaborador.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			
			JDBCColaboradorDAO jdbcColaborador = new JDBCColaboradorDAO(conexao);
			boolean retorno  = jdbcColaborador.inserir(colaborador);
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
	@Path("/checkId")
	@Produces(MediaType.APPLICATION_JSON)

	public Response checkId(@QueryParam("id")int id) {

		try {
			Colaborador colaborador= new Colaborador();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCColaboradorDAO jdbcColaborador = new JDBCColaboradorDAO(conexao);

			colaborador = jdbcColaborador.checkId(id);

			conec.fecharConexao();
			return this.buildResponse(colaborador);

		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	@PUT
	@Path("/alterar")
	@Consumes("application/*")
	public Response alterar(String colaboradorParam) {
		try {
			Colaborador colaborador = new Gson().fromJson(colaboradorParam, Colaborador.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCColaboradorDAO jdbcColaborador = new JDBCColaboradorDAO(conexao);

			boolean retorno = jdbcColaborador.alterar(colaborador);

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
			JDBCColaboradorDAO jdbcColaborador = new JDBCColaboradorDAO(conexao);
			
			boolean retorno = jdbcColaborador.deletar(id);
			
			String msg = "";
			if(retorno) {
				msg="Produto exclu�do com sucesso!";
			}else {
				msg="Erro ao excluir produto!";
			}
			
			conec.fecharConexao();
			
			return this.buildResponse(msg);
			
		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}			
	}
	@GET
	@Path("/checkIdSenha")
	@Produces(MediaType.APPLICATION_JSON)

	public Response checkIdSenha(@QueryParam("id")int id) {

		try {
			Colaborador colaborador= new Colaborador();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCColaboradorDAO jdbcColaborador = new JDBCColaboradorDAO(conexao);

			colaborador = jdbcColaborador.checkIdSenha(id);

			conec.fecharConexao();
			return this.buildResponse(colaborador);

		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	@PUT
	@Path("/alterarSenha")
	@Consumes("application/*")
	public Response alterarSenha(String colaboradorParam) {
		try {
			Colaborador colaborador = new Gson().fromJson(colaboradorParam, Colaborador.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCColaboradorDAO jdbcColaborador = new JDBCColaboradorDAO(conexao);

			boolean retorno = jdbcColaborador.alterarSenha(colaborador);

			String msg="";
			if (retorno) {
				msg = "Senha atualizada com sucesso!";
			}else {
				msg = "Erro ao atualizar senha";
			}
			conec.fecharConexao();
			return this.buildResponse(msg);
		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	@GET
	@Path("/buscarSelO")
	@Produces(MediaType.APPLICATION_JSON)

	public Response buscarSelO() {

		try {
			List<Colaborador> listaColaborador = new ArrayList<Colaborador>();

			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCColaboradorDAO jdbcColaborador = new JDBCColaboradorDAO(conexao);
			listaColaborador = jdbcColaborador.buscarSelO();
			conec.fecharConexao();
			return this.buildResponse(listaColaborador);

		}catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
}