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

import br.com.senai.jdbcinterface.LeiturasDAO;
import br.com.senai.jdbcinterface.ProducaoDAO;
import br.com.senai.modelo.Leituras;
import br.com.senai.modelo.Producao;

public class JDBCLeiturasDAO implements LeiturasDAO{

	private Connection conexao;

	public JDBCLeiturasDAO(Connection conexao) {
		this.conexao = conexao;
	}
	public boolean inserir (Leituras leituras) {
		String comando = "INSERT INTO leituras"
				+ "(idProducao, idOperador, valor, lmQuan, dataHora, obs) "
				+ "VALUES (?,?,?,?,?,?)";
		PreparedStatement p;

		try {

			p = this.conexao.prepareStatement(comando);			
			p.setInt(1,  leituras.getProducaoId());
			p.setInt(2, leituras.getOperadorId());
			p.setFloat(3, leituras.getValor());
			p.setInt(4, leituras.getLmQtd());
			p.setString(5, leituras.getData());
			p.setString(6, leituras.getObs());
			p.execute();

		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}
	public List<JsonObject> buscar (int ordem){
		String comando = "SELECT *,producao.subGrupo as subgrupo, producao.codigo as producao, producao.numAmostras as amostras, operador.nome as operador FROM leituras "+
		"inner join producao on leituras.idProducao = producao.idProducao "+
		"inner join operador on leituras.idOperador = operador.idOperador ";
		if (ordem != 0) {
			comando += "WHERE producao.codigo LIKE '%"+ ordem + "%' "; 
		}
		comando += "ORDER BY leituras.idLeituras ASC";		
			List<JsonObject> listaLeituras = new ArrayList<JsonObject>();
			JsonObject leituras = null;

			try {

				Statement stmt = conexao.createStatement();
				ResultSet rs = stmt.executeQuery(comando);

				while(rs.next()) {

					int idd = rs.getInt("idLeituras");
					String idProd = rs.getString("producao");
					String idOp = rs.getString("operador");
					float valor = rs.getFloat("valor");
					int quan = rs.getInt("lmQuan");
					int lmAm = rs.getInt("amostras");
					String data = rs.getString("dataHora");
					String observ = rs.getString("obs");
					int subgrupo = rs.getInt("subgrupo");
					
					leituras = new JsonObject();
					leituras.addProperty("id", idd);
					leituras.addProperty("producao", idProd);
					leituras.addProperty("operador", idOp);
					leituras.addProperty("valor", valor);
					leituras.addProperty("lmQuan", quan);
					leituras.addProperty("amostras", lmAm);
					leituras.addProperty("data", data);
					leituras.addProperty("obs", observ);
					leituras.addProperty("subgrupo", subgrupo);
				
					
					
					listaLeituras.add(leituras);
				}

			}catch (Exception e) {
				e.printStackTrace();
			}
			return listaLeituras;
		}
	public boolean deletar(int id) {
		String comando = "DELETE FROM leituras WHERE idLeituras = ?";
	
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
	public Leituras buscarCod (int id){
		String comando = "select count(leituras.lmQuan) as total_Leituras, producao.subGrupo  as subgrupo" + 
				" from leituras, producao " + 
				"where leituras.idProducao=producao.idProducao and leituras.idProducao=?";
		
			
			Leituras leituras = new Leituras();

			try {

				PreparedStatement p = this.conexao.prepareStatement(comando);
				p.setInt(1, id);
				ResultSet rs = p.executeQuery();

				while(rs.next()) {

					
					int quan = rs.getInt("total_Leituras");
					int subgrupo = rs.getInt("subgrupo");
					
					leituras.setCount(quan);
					leituras.setSubgrupo(subgrupo);
					
				}

			}catch (Exception e) {
				e.printStackTrace();
			}
			return leituras;
		}
	public List<JsonObject> buscarDetails (int id){
		System.out.println("id"+id);
				String comando = "SELECT *,producao.subGrupo as subgrupo, producao.codigo as producao, producao.numAmostras as amostras, operador.nome as operador FROM leituras " + 
						"inner join producao on leituras.idProducao = producao.idProducao " + 
						"inner join operador on leituras.idOperador = operador.idOperador " + 
						"where leituras.idProducao = producao.idProducao and leituras.idProducao="+id+""; 
					
					List<JsonObject> listaLeituras = new ArrayList<JsonObject>();
					JsonObject leituras = null;

					try {

						Statement stmt = conexao.createStatement();
						ResultSet rs = stmt.executeQuery(comando);

						while(rs.next()) {

							int idd = rs.getInt("idLeituras");
							String idProd = rs.getString("producao");
							String idOp = rs.getString("operador");
							float valor = rs.getFloat("valor");
							int quan = rs.getInt("lmQuan");
							int lmAm = rs.getInt("amostras");
							String data = rs.getString("dataHora");
							String observ = rs.getString("obs");
							String cliente = rs.getString("cliente");
							int espMin = rs.getInt("especMin");
							int espMax = rs.getInt("especMax");
							int subgrupo = rs.getInt("subgrupo");

							
							leituras = new JsonObject();
							leituras.addProperty("id", idd);
							leituras.addProperty("producao", idProd);
							leituras.addProperty("operador", idOp);
							leituras.addProperty("valor", valor);
							leituras.addProperty("lmQuan", quan);
							leituras.addProperty("amostras", lmAm);
							leituras.addProperty("data", data);
							leituras.addProperty("obs", observ);
							leituras.addProperty("cliente", cliente);
							leituras.addProperty("especMin", espMin);
							leituras.addProperty("especMax", espMax);
							leituras.addProperty("subgrupo", subgrupo);

							
							listaLeituras.add(leituras);
						}

					}catch (Exception e) {
						e.printStackTrace();
					}
					return listaLeituras;
				}
	
}