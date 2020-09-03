package br.com.senai.session;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tomcat.util.digester.Digester;

import br.com.senai.modelo.Funcionario;



/**
 * Servlet implementation class Servlet1
 */
@WebServlet("/Servlet1")
public class Servlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	PrintWriter out = response.getWriter();
	String userid = request.getParameter("userid");
	String password = request.getParameter("password");
	
	String salt="DGE$5SGr@3VsHYUMas2323E4d57vfBfFSTRU@!DSH(*%FDSdfg13sgfsg";
	String senhaSalt = password+salt;
	String senhaSha1SemSal = DigestUtils.shaHex(senhaSalt);
	
	
	HttpSession session = request.getSession();
	boolean flag = false;
	Funcionario funcionario = new Funcionario();
	
	
	try {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection con = java.sql.DriverManager.getConnection("jdbc:mysql://localhost/cartacep?"
		+"user=root&password=root&useTimezone=true&serverTimezone=UTC");	
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select * from gestor");
		String comando = "UPDATE getId "
				+ "SET nomeGestor=?, matricGestor=?"
				+ " WHERE idgetId=1";
		
		PreparedStatement p;
		p = con.prepareStatement(comando);			
		p.setString(1,"teste");
		p.setString(2,"teste.com");
		

		p.executeUpdate();
		while(rs.next()) {
			if(userid.equals(rs.getString(3)) && senhaSha1SemSal.equals(rs.getString(4))) {
				session.setAttribute("idGestor", userid);
				String nome = rs.getString("nome");
				String iduser = rs.getString("idGestor");

				p = con.prepareStatement(comando);			
				p.setString(1,nome);
				p.setString(2,iduser);
				

				p.executeUpdate();
				
				
				session.setAttribute("nome", nome);	

				funcionario.setNome(nome);
				flag = true;
				response.sendRedirect("Servlet2");

			}
			
		}
		
		if(flag==false) {
			response.sendRedirect("index.html");

		}
		
		
	}catch(Exception p){
		out.print(p);
	}
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("Servlet2").forward(request, response);
		doGet(request, response);
	}

}
