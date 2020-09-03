package br.com.senai.rest;

import java.sql.Connection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import br.com.senai.bd.Conexao;

import br.com.senai.jdbc.JDBCGerenteDAO;

import br.com.senai.modelo.Gerente;

@Path("gerente")
public class GerenteRest extends UtilRest{
	@GET
	@Path("/checkId")
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response checkId(@QueryParam("matricula")int matricula) {

		try {
			Gerente gerente = new Gerente();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCGerenteDAO jdbcGerente = new JDBCGerenteDAO(conexao);
			gerente = jdbcGerente.checkId(matricula);
			
			conec.fecharConexao();
			return this.buildResponse(gerente);

		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
}
