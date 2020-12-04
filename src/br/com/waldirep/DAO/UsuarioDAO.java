package br.com.waldirep.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.waldirep.beans.Usuario;
import br.com.waldirep.connection.SingleConnection;

public class UsuarioDAO {
	
	
	private static Connection connection;
	
	
	public UsuarioDAO() {
		connection = SingleConnection.getConnection();
	}
	
	
	
	public void salvar (Usuario usuario) {
		
		try {
			String sql = "insert into usuario (login, senha) values (? , ?)";
			
			PreparedStatement insert = connection.prepareStatement(sql); 
			insert.setString(1, usuario.getLogin());
			insert.setString(2, usuario.getSenha());
			insert.execute();
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	public List<Usuario> listarTodos() throws SQLException {
		
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		
		String sql = "SELECT * FROM usuario";
		
		PreparedStatement listar = connection.prepareStatement(sql); 
		ResultSet resultado = listar.executeQuery();
		
		while(resultado.next()) {
		  Usuario user = new Usuario();
		  user.setLogin(resultado.getString("login"));
		  user.setSenha(resultado.getString("senha"));
		  
		  listaUsuarios.add(user);
		}
		return listaUsuarios;
		
	}
	
	
	public void deletar(String login) {
		
		try {
			String sql = "delete from usuario where login = '"+login+"'";
			PreparedStatement deletar = connection.prepareStatement(sql); 
			deletar.execute();
			connection.commit();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
