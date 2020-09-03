package br.com.senai.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import com.google.gson.JsonObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import br.com.senai.jdbcinterface.ColaboradorDAO;
import br.com.senai.modelo.CadMaquina;
import br.com.senai.modelo.Colaborador;



public class JDBCColaboradorDAO implements ColaboradorDAO{
	
private Connection conexao;

public JDBCColaboradorDAO(Connection conexao) {
	this.conexao = conexao;
}
public List<JsonObject> buscarPorMatricula (int matricula){

	//Inicia a cria��o do comando SQL de busca 
	String comando = "SELECT * FROM operador ";
	//Se o nome n�o estiver vazio...
	if (matricula != 0) {
		//concatena no comando o WHERE buscando no nome do produto 
		//o texto da vari�vel nome
		comando += "WHERE operador.matricula LIKE '%"+ matricula + "%' "; 
	}
	//Finaliza o comando ordenando alfabeticamente por
	//categoria, marca e depois modelo
	comando += "ORDER BY operador.nome ASC";
	
	
		List<JsonObject> listaColaboradores = new ArrayList<JsonObject>();
		JsonObject colaborador = null;

		try {

			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);

			while(rs.next()) {

				int id = rs.getInt("idOperador");
				int matric = rs.getInt("matricula");
				String nome = rs.getString("nome");
				String senha = rs.getString("senha");
				String fone = rs.getString("telefone");
				
				colaborador = new JsonObject();
				colaborador.addProperty("id", id);
				colaborador.addProperty("matricula", matric);
				colaborador.addProperty("nome", nome);
				colaborador.addProperty("senha", senha);
				colaborador.addProperty("fone", fone);
				
				listaColaboradores.add(colaborador);
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		return listaColaboradores;
	}

public Colaborador checkId(int id) {
	
	String comando = "SELECT * FROM operador WHERE idOperador = ?";
	Colaborador colaborador = new Colaborador();
	try {
		PreparedStatement p = this.conexao.prepareStatement(comando);
		p.setInt(1, id);
		ResultSet rs = p.executeQuery();
		while (rs.next()) {
			int idd = rs.getInt("idOperador");
			int matricula = rs.getInt("matricula");
			String nome = rs.getString("nome");
			String senha = rs.getString("senha");
			String fone = rs.getString("telefone");
			colaborador.setId(idd);
			colaborador.setMatricula(matricula);
			colaborador.setNome(nome);
			colaborador.setSenha(senha);
			colaborador.setFone(fone);

		}
	}catch (Exception e) {
		e.printStackTrace();
	}
	return colaborador;
}
	
	public boolean alterar(Colaborador colaborador) {		
		String comando = "UPDATE operador "
				+ "SET matricula=?, nome=?, telefone=?"
				+ " WHERE idOperador=?";
		PreparedStatement p;
		try {
			
			
			
			p = this.conexao.prepareStatement(comando);
			p.setInt(1,colaborador.getMatricula());
			p.setString(2,colaborador.getNome());
			p.setString(3,colaborador.getFone());
			p.setInt(4,colaborador.getId());

			p.executeUpdate();

		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
public boolean inserir (Colaborador colaborador) {
	
		String comando = "INSERT INTO operador "
				+ "(matricula, nome, senha, telefone) "
				+ "VALUES (?,?,?,?)";
		PreparedStatement p;
		
		try {
			//Prepara o comando para execução no BD em que nos conectamos
			
			p = this.conexao.prepareStatement(comando);
			
			//Substitui no comando os "?" pelos valores do produto
			
			
			String salt="DGE$5SGr@3VsHYUMas2323E4d57vfBfFSTRU@!DSH(*%FDSdfg13sgfsg";
			String senhaSalt = colaborador.getSenha()+salt;
			String senhaSha1SemSal = DigestUtils.shaHex(senhaSalt);
			
			p.setInt(1, colaborador.getMatricula());
			p.setString(2, colaborador.getNome());
			p.setString(3, senhaSha1SemSal);
			p.setString(4, colaborador.getFone());
			
			
			//Executa o comando no BD
			p.execute();
			
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
public boolean deletar(int id) {
	String comando = "DELETE FROM operador WHERE idOperador = ?";
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
public Colaborador checkIdSenha(int id) {
	
	String comando = "SELECT * FROM operador WHERE idOperador = ?";
	Colaborador colaborador = new Colaborador();
	try {
		PreparedStatement p = this.conexao.prepareStatement(comando);
		p.setInt(1, id);
		ResultSet rs = p.executeQuery();
		while (rs.next()) {
			int iduser = rs.getInt("idOperador");			
			String senha = rs.getString("senha");	
			colaborador.setId(iduser);			
			colaborador.setSenha(senha);
		}
	}catch (Exception e) {
		e.printStackTrace();
	}
	return colaborador;
}
public boolean alterarSenha(Colaborador colaborador) {	
	System.out.println(colaborador.getSenha());
	String comando = "UPDATE operador "
			+ "SET senha=?"
			+ " WHERE idOperador=?";
	PreparedStatement p;
	try {
		
		String salt="DGE$5SGr@3VsHYUMas2323E4d57vfBfFSTRU@!DSH(*%FDSdfg13sgfsg";
		String senhaSalt = colaborador.getSenha()+salt;
		String senhaSha1ComSal = DigestUtils.shaHex(senhaSalt);
		
		p = this.conexao.prepareStatement(comando);
		p.setString(1,senhaSha1ComSal);
		p.setInt(2,colaborador.getId());

		p.executeUpdate();

	}catch (SQLException e) {
		e.printStackTrace();
		return false;
	}
	return true;
}
public List<Colaborador> buscarSelO (){

	String comando = "SELECT * FROM operador";
	List<Colaborador> listaColaborador = new ArrayList<Colaborador>();
	Colaborador colaborador = null;

	try {

		Statement stmt = conexao.createStatement();

		ResultSet rs = stmt.executeQuery(comando);

		while(rs.next()) {

			colaborador = new Colaborador();

			int idd = rs.getInt("idOperador");
			String nome = rs.getString("nome");
			

			colaborador.setId(idd);
			colaborador.setNome(nome);
			listaColaborador.add(colaborador);
		}

	}catch (Exception ex) {

		ex.printStackTrace();
	}
	return listaColaborador;
}
}
