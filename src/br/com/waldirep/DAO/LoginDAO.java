package br.com.waldirep.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.waldirep.connection.SingleConnection;

public class LoginDAO {
	
	private static Connection connection;
	
	
	public LoginDAO() {
		connection = SingleConnection.getConnection();
	}
	
	
	
	public boolean validarLogin(String login, String senha) throws Exception{
		
		String sql = "SELECT * FROM usuario where login = '"+login+"' and senha = '"+senha+"'";
		
		PreparedStatement statement = connection.prepareStatement(sql); 
		ResultSet result = statement.executeQuery(); // Carrega o resultado do SQL
		
		if(result.next()) {
			return true; // Validou o usuario
		}else {
			return false; // Não validou o usuario
		}
	}

}
