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
			String sql = "insert into usuario (login, senha, nome) values (? , ? ,?)";
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql); 
			preparedStatement.setString(1, usuario.getLogin());
			preparedStatement.setString(2, usuario.getSenha());
			preparedStatement.setString(3, usuario.getNome());
			preparedStatement.execute();
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
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql); 
		ResultSet resultado = preparedStatement.executeQuery();
		
		while(resultado.next()) {
		  Usuario user = new Usuario();
		  user.setId(resultado.getLong("id"));
		  user.setNome(resultado.getString("nome"));
		  user.setLogin(resultado.getString("login"));
		  user.setSenha(resultado.getString("senha"));
		  
		  listaUsuarios.add(user);
		}
		return listaUsuarios;
		
	}
	
	
	public void deletar(String id) {
		
		try {
			String sql = "delete from usuario where id = '"+ id +"'";
			PreparedStatement preparedStatement = connection.prepareStatement(sql); 
			preparedStatement.execute();
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



	public Usuario consultar(String id) throws Exception {
		String sql = "select * from usuario where id = '"+ id +"'";
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultado = preparedStatement.executeQuery();
		
		if(resultado.next()) {
			 Usuario user = new Usuario();
			 user.setId(resultado.getLong("id"));
			 user.setLogin(resultado.getString("login"));
			 user.setSenha(resultado.getString("senha"));
			 user.setNome(resultado.getString("nome"));
			 return user;
		}
		
		return null;
	}



	public void atualizar(Usuario user) {
		
		try {
			String sql = "update usuario set login = ?, senha = ?, nome = ? where id =" + user.getId();
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, user.getLogin());
			preparedStatement.setString(2, user.getSenha());
			preparedStatement.setString(3, user.getNome());
			preparedStatement.execute();
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	
	public boolean validarLogin(String login) throws Exception {
		String sql = "select count(1) as qtd from usuario where login = '"+ login +"'";
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultado = preparedStatement.executeQuery();
		
		if(resultado.next()) {
			
			 return resultado.getInt("qtd") <= 0; // retorna false
		}
		
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
