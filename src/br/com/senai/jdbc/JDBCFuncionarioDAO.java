package br.com.senai.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.codec.digest.DigestUtils;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import br.com.senai.jdbcinterface.FuncionarioDAO;
import br.com.senai.modelo.Funcionario;

public class JDBCFuncionarioDAO implements FuncionarioDAO{
	
private Connection conexao;

public JDBCFuncionarioDAO(Connection conexao) {
	this.conexao = conexao;
}
	public boolean inserir (Funcionario funcionario) {
		
		String comando = "INSERT INTO gestor "
				+ "(nome, email, senha) "
				+ "VALUES (?,?,?)";
		PreparedStatement p;
		
		try {
			//Prepara o comando para execução no BD em que nos conectamos
			
			p = this.conexao.prepareStatement(comando);
			
			//Substitui no comando os "?" pelos valores do produto
			
			
			String salt="DGE$5SGr@3VsHYUMas2323E4d57vfBfFSTRU@!DSH(*%FDSdfg13sgfsg";
			String senhaSalt = funcionario.getSenha()+salt;
			String senhaSha1SemSal = DigestUtils.shaHex(senhaSalt);
			System.out.println(senhaSha1SemSal);
			p.setString(1, funcionario.getNome());
			p.setString(2, funcionario.getEmail());
			p.setString(3, senhaSha1SemSal);
			
			
			//Executa o comando no BD
			p.execute();
			
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public Funcionario checkMatricula(int userid) {
		
		String comando = "SELECT * FROM gestor WHERE idGestor = ?";
		Funcionario funcionario = new Funcionario();
		try {
			PreparedStatement p = this.conexao.prepareStatement(comando);
			p.setInt(1, userid);
			ResultSet rs = p.executeQuery();
			while (rs.next()) {

				String nome = rs.getString("nome");
				String email = rs.getString("email");
				funcionario.setId(userid);
				funcionario.setNome(nome);
				funcionario.setEmail(email);

			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return funcionario;
	}
	public boolean alterar(Funcionario funcionario) {		
		String comando = "UPDATE gestor "
				+ "SET nome=?, email=?"
				+ " WHERE idGestor=?";
		PreparedStatement p;
		try {
			p = this.conexao.prepareStatement(comando);			
			p.setString(1,funcionario.getNome());
			p.setString(2,funcionario.getEmail());
			p.setInt(3,funcionario.getId());

			p.executeUpdate();

		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
public Funcionario checkMatriculaSenha(int userid) {
		
		String comando = "SELECT * FROM gestor WHERE idGestor = ?";
		Funcionario funcionario = new Funcionario();
		try {
			PreparedStatement p = this.conexao.prepareStatement(comando);
			p.setInt(1, userid);
			ResultSet rs = p.executeQuery();
			while (rs.next()) {

				String nome = rs.getString("nome");
				String email = rs.getString("email");
				funcionario.setId(userid);
				funcionario.setNome(nome);
				funcionario.setEmail(email);

			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return funcionario;
	}
	public boolean alterarSenha(Funcionario funcionario) {		
		String comando = "UPDATE gestor "
				+ "SET senha=?"
				+ " WHERE idGestor=?";
		PreparedStatement p;
		try {
			String salt="DGE$5SGr@3VsHYUMas2323E4d57vfBfFSTRU@!DSH(*%FDSdfg13sgfsg";
			String senhaSalt = funcionario.getSenha()+salt;
			String senhaSha1SemSal = DigestUtils.shaHex(senhaSalt);
			p = this.conexao.prepareStatement(comando);			
			p.setString(1,senhaSha1SemSal);
			p.setInt(2,funcionario.getId());

			p.executeUpdate();

		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
