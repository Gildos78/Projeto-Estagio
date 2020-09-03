package br.com.senai.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import br.com.senai.jdbcinterface.GerenteDAO;
import br.com.senai.modelo.Gerente;


public class JDBCGerenteDAO implements GerenteDAO{
	
private Connection conexao;

public JDBCGerenteDAO(Connection conexao) {
	this.conexao = conexao;
}
public Gerente checkId(int userid) {
	
	String comando = "SELECT * FROM getId WHERE idgetId = ?";
	Gerente gerente= new Gerente();
	try {
		PreparedStatement p = this.conexao.prepareStatement(comando);
		p.setInt(1, 1);
		ResultSet rs = p.executeQuery();
		while (rs.next()) {

			String nome = rs.getString("nomeGestor");
			int matricula = rs.getInt("matricGestor");
			
			gerente.setNome(nome);
			gerente.setMatricula(matricula);

		}
	}catch (Exception e) {
		e.printStackTrace();
	}
	return gerente;
}




}