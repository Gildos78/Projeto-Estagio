package br.com.senai.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.senai.jdbcinterface.ProducaoDAO;
import br.com.senai.modelo.CadOperacao;
import br.com.senai.modelo.Producao;

public class JDBCProducaoDAO implements ProducaoDAO{

	private Connection conexao;

	public JDBCProducaoDAO(Connection conexao) {
		this.conexao = conexao;
	}
	public boolean inserir (Producao producao) {
		String comando = "INSERT INTO producao"
				+ "(codigo, cliente, dataInicio, dataFinal, descricao, especMin, especMax, numAmostras, subGrupo, idOperacao, idMaquina) "
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement p;

		try {

			p = this.conexao.prepareStatement(comando);			
			p.setString(1, producao.getCodigo());
			p.setString(2, producao.getCliente());
			p.setString(3, producao.getDataInicio());
			p.setString(4, producao.getDataFinal());
			p.setString(5, producao.getDescricao());
			p.setFloat(6, producao.getEspMin());
			p.setFloat(7, producao.getEspMax());
			p.setInt(8, producao.getNumAmostras());
			p.setInt(9, producao.getSubgrupo());
			p.setInt(10,  producao.getOperacaoId());
			p.setInt(11, producao.getMaquinaId());
			p.execute();

		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}
	public List<JsonObject> buscar (String cliente){

		String comando = "SELECT *, maquina.nome as maquina, operacao.nome as operacao FROM producao "+
		"inner join maquina on producao.idmaquina = maquina.idmaquina "+
		"inner join operacao on producao.idOperacao = operacao.idOperacao ";
		if (cliente != "") {
			comando += "WHERE producao.cliente LIKE '%"+ cliente + "%' "; 
		}
		comando += "ORDER BY producao.cliente ASC";		
			List<JsonObject> listaProducoes = new ArrayList<JsonObject>();
			JsonObject producao = null;

			try {

				Statement stmt = conexao.createStatement();
				ResultSet rs = stmt.executeQuery(comando);

				while(rs.next()) {

					int id = rs.getInt("idProducao");
					String codigo = rs.getString("codigo");
					String nomeCliente = rs.getString("cliente");
					String dataI = rs.getString("dataInicio");
					String dataF = rs.getString("dataFinal");
					String descricao = rs.getString("descricao");
					float espMin = rs.getFloat("especMin");
					float espMax = rs.getFloat("especMax");
					int numAm = rs.getInt("numAmostras");
					int subgrupo = rs.getInt("subGrupo");
					String idOp = rs.getString("operacao");
					String idMaq = rs.getString("maquina");
					
					producao = new JsonObject();
					producao.addProperty("id", id);
					producao.addProperty("codigo", codigo);
					producao.addProperty("cliente", nomeCliente);
					producao.addProperty("dataInicio", dataI);
					producao.addProperty("dataFinal", dataF);
					producao.addProperty("descricao", descricao);
					producao.addProperty("espMin", espMin);
					producao.addProperty("espMax", espMax);
					producao.addProperty("numAmostras", numAm);
					producao.addProperty("subgrupo", subgrupo);
					producao.addProperty("operacao", idOp);
					producao.addProperty("maquinaId", idMaq);
					
					
					listaProducoes.add(producao);
				}

			}catch (Exception e) {
				e.printStackTrace();
			}
			return listaProducoes;
		}
	public List<JsonObject> buscarData (String dataIni, String dataFin){

		String comando = "SELECT *, maquina.nome as maquina, operacao.nome as operacao FROM producao "+
		"inner join maquina on producao.idmaquina = maquina.idmaquina "+
		"inner join operacao on producao.idOperacao = operacao.idOperacao ";
		if (dataIni != "" && dataFin != "") {
			comando += "WHERE producao.dataInicio >= '" + dataIni + "' AND producao.dataFinal <= '" + dataFin + "' ";
			
		}
	
		comando += "ORDER BY producao.cliente ASC";		
			List<JsonObject> listaProducoes = new ArrayList<JsonObject>();
			JsonObject producao = null;

			try {

				Statement stmt = conexao.createStatement();
				ResultSet rs = stmt.executeQuery(comando);

				while(rs.next()) {

					int id = rs.getInt("idProducao");
					String codigo = rs.getString("codigo");
					String nomeCliente = rs.getString("cliente");
					String dataI = rs.getString("dataInicio");
					String dataF = rs.getString("dataFinal");
					String descricao = rs.getString("descricao");
					float espMin = rs.getFloat("especMin");
					float espMax = rs.getFloat("especMax");
					int numAm = rs.getInt("numAmostras");
					int subgrupo = rs.getInt("subGrupo");
					String idOp = rs.getString("operacao");
					String idMaq = rs.getString("maquina");
					
					producao = new JsonObject();
					producao.addProperty("id", id);
					producao.addProperty("codigo", codigo);
					producao.addProperty("cliente", nomeCliente);
					producao.addProperty("dataInicio", dataI);
					producao.addProperty("dataFinal", dataF);
					producao.addProperty("descricao", descricao);
					producao.addProperty("espMin", espMin);
					producao.addProperty("espMax", espMax);
					producao.addProperty("numAmostras", numAm);
					producao.addProperty("subgrupo", subgrupo);
					producao.addProperty("operacao", idOp);
					producao.addProperty("maquinaId", idMaq);
					
					
					listaProducoes.add(producao);
				}

			}catch (Exception e) {
				e.printStackTrace();
			}
			return listaProducoes;
		}
	
	public List<JsonObject> buscarDes (String cliente){
		String comando = "SELECT *, maquina.nome as maquina, operacao.nome as operacao FROM producao "+
				"inner join maquina on producao.idmaquina = maquina.idmaquina "+
				"inner join operacao on producao.idOperacao = operacao.idOperacao ";
				if (cliente != "") {
					comando += "WHERE producao.cliente LIKE '%" + cliente + "%' OR producao.codigo LIKE '%" + cliente + "%' ";
				}
		comando += "ORDER BY producao.cliente ASC";		
			List<JsonObject> listaProducoes = new ArrayList<JsonObject>();
			JsonObject producao = null;
			try {

				Statement stmt = conexao.createStatement();
				ResultSet rs = stmt.executeQuery(comando);

				while(rs.next()) {

					int id = rs.getInt("idProducao");
					String codigo = rs.getString("codigo");
					String nomeCliente = rs.getString("cliente");
					String dataI = rs.getString("dataInicio");
					String dataF = rs.getString("dataFinal");
					String descricao = rs.getString("descricao");
					float espMin = rs.getFloat("especMin");
					float espMax = rs.getFloat("especMax");
					int numAm = rs.getInt("numAmostras");
					int subgrupo = rs.getInt("subGrupo");
					String idOp = rs.getString("operacao");
					String idMaq = rs.getString("maquina");
					
					producao = new JsonObject();
					producao.addProperty("id", id);
					producao.addProperty("codigo", codigo);
					producao.addProperty("cliente", nomeCliente);
					producao.addProperty("dataInicio", dataI);
					producao.addProperty("dataFinal", dataF);
					producao.addProperty("descricao", descricao);
					producao.addProperty("espMin", espMin);
					producao.addProperty("espMax", espMax);
					producao.addProperty("numAmostras", numAm);
					producao.addProperty("subgrupo", subgrupo);
					producao.addProperty("operacao", idOp);
					producao.addProperty("maquinaId", idMaq);
					
					
					listaProducoes.add(producao);
				}

			}catch (Exception e) {
				e.printStackTrace();
			}
			return listaProducoes;
		}
	public boolean deletar(int id) {
		String comando = "DELETE FROM producao WHERE idProducao = ?";
		PreparedStatement p;
		try {
			p=this.conexao.prepareStatement(comando);
			p.setInt(1, id);
			p.execute();
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public List<Producao> buscarSelP (){

		String comando = "SELECT * FROM producao";
		List<Producao> listaProducao = new ArrayList<Producao>();
		Producao producao = null;

		try {

			Statement stmt = conexao.createStatement();

			ResultSet rs = stmt.executeQuery(comando);

			while(rs.next()) {

				producao = new Producao();

				int idd = rs.getInt("idProducao");
				String codigo = rs.getString("codigo");
				

				producao.setId(idd);
				producao.setCodigo(codigo);
				listaProducao.add(producao);
			}

		}catch (Exception ex) {

			ex.printStackTrace();
		}
		return listaProducao;
	}
public Producao buscarCodigo(int id) {
		System.out.println("Id"+id);
		String comando = "SELECT * FROM producao WHERE idProducao = ?";
		Producao producao = new Producao();
		try {
			PreparedStatement p = this.conexao.prepareStatement(comando);
			p.setInt(1, id);
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				
				int idd = rs.getInt("idProducao");
				String codigo = rs.getString("codigo");
				int numAmostras = rs.getInt("numAmostras");
				int subgrupo = rs.getInt("subGrupo");


				producao.setId(idd);
				producao.setCodigo(codigo);
				producao.setNumAmostras(numAmostras);
				producao.setSubgrupo(subgrupo);
				
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return producao;
	}
public Producao buscarAm(int id) {
	String comando = "SELECT * FROM producao WHERE idProducao = ?";
	Producao producao = new Producao();
	try {
		PreparedStatement p = this.conexao.prepareStatement(comando);
		p.setInt(1, id);
		ResultSet rs = p.executeQuery();
		while (rs.next()) {
			
			int idd = rs.getInt("idProducao");
			
			int numAmostras = rs.getInt("numAmostras");

			producao.setId(idd);
			producao.setNumAmostras(numAmostras);
			
			
		}
	}catch (Exception e) {
		e.printStackTrace();
	}
	return producao;
}
}