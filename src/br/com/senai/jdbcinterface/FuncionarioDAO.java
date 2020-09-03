package br.com.senai.jdbcinterface;

import br.com.senai.modelo.Funcionario;



public interface FuncionarioDAO {
	public boolean inserir (Funcionario funcionario);
	public Funcionario checkMatricula(int matricula);
	public boolean alterar(Funcionario funcionario);
	public Funcionario checkMatriculaSenha(int matricula);
	public boolean alterarSenha(Funcionario funcionario);
}
