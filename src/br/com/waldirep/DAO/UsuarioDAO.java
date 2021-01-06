package br.com.waldirep.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.waldirep.beans.Usuario;
import br.com.waldirep.connection.SingleConnectionHeroku;
import br.com.waldirep.exception.OrphanRemovalException;
import br.com.waldirep.util.LogUtil;

public class UsuarioDAO {
	
	
	private Connection connection;
	

	public UsuarioDAO() {
		connection = SingleConnectionHeroku.getConnection();
	}
	
	

	public void salvar(Usuario usuario) throws SQLException {
		try {
			String sql = "INSERT INTO usuario (login, senha, nome, cep, rua, "
					+ "bairro, cidade, estado, ibge, fotoBase64, contentType, "
					+ "curriculoBase64, contentTypeCurriculo, fotoBase64Miniatura, "
					+ "ativo, sexo, perfil) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, usuario.getLogin());
				statement.setString(2, usuario.getSenha());
				statement.setString(3, usuario.getNome());
				statement.setString(4, usuario.getCep());
				statement.setString(5, usuario.getRua());
				statement.setString(6, usuario.getBairro());
				statement.setString(7, usuario.getCidade());
				statement.setString(8, usuario.getEstado());
				statement.setString(9, usuario.getIbge());
				statement.setString(10, usuario.getFotoBase64());
				statement.setString(11, usuario.getContentType());
				statement.setString(12, usuario.getCurriculoBase64());
				statement.setString(13, usuario.getContentTypeCurriculo());
				statement.setString(14, usuario.getFotoBase64Miniatura());
				statement.setBoolean(15, usuario.isAtivo());
				statement.setString(16, usuario.getSexo());
				statement.setString(17, usuario.getPerfil());
				statement.execute();
			}
			connection.commit();
		} catch (Exception e) {
			LogUtil.getLogger(UsuarioDAO.class).error(e.getCause().toString());
			connection.rollback();
		}
	}
	
	

	/**
	 * Metodo privado desta classe que lista todos os usuarios, metodo utilizado em metodos abaixo
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	private List<Usuario> listarUsuarios(String sql) throws SQLException {
		List<Usuario> usuarios = new ArrayList<>();
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			try (ResultSet result = statement.executeQuery()) {
				while (result.next()) {
					Usuario usuario = new Usuario();
					usuario.setId(result.getLong("id"));
					usuario.setLogin(result.getString("login"));
					usuario.setSenha(result.getString("senha"));
					usuario.setNome(result.getString("nome"));
					usuario.setCep(result.getString("cep"));
					usuario.setRua(result.getString("rua"));
					usuario.setBairro(result.getString("bairro"));
					usuario.setCidade(result.getString("cidade"));
					usuario.setEstado(result.getString("estado"));
					usuario.setIbge(result.getString("ibge"));
					usuario.setFotoBase64Miniatura(result.getString("fotoBase64Miniatura"));
					usuario.setContentType(result.getString("contentType"));
					usuario.setCurriculoBase64(result.getString("curriculoBase64"));
					usuario.setContentTypeCurriculo(result.getString("contentTypeCurriculo"));
					usuario.setAtivo(result.getBoolean("ativo"));
					usuario.setSexo(result.getString("sexo"));
					usuario.setPerfil(result.getString("perfil"));
					usuarios.add(usuario);
				}
			}
		}
		return usuarios;
	}
	
	
	
	public List<Usuario> listarPorNome(String descricaoConsulta) throws SQLException {		
		String sql = "SELECT * FROM usuario WHERE login <> 'admin' AND LOWER(nome) "
				+ "LIKE LOWER('%" + descricaoConsulta + "%') ORDER BY nome";		
		return listarUsuarios(sql);
		
	}
	
	

	/**
	 * Metodo que lista todos os usuarios menos o usuario admin
	 * @return
	 * @throws SQLException
	 */
	public List<Usuario> listarTodos() throws SQLException {
		String sql = "SELECT * FROM usuario WHERE login <> 'admin'";
		return listarUsuarios(sql);
	}
	
	

	/**
	 * Metodo que deleta um usuario, menos o admin
	 * @param id
	 * @return
	 * @throws OrphanRemovalException
	 * @throws SQLException
	 */
	public Boolean deletar(String id) throws OrphanRemovalException, SQLException {
		try {
			String sql = "DELETE FROM usuario WHERE id = '" + id + "' AND login <> 'admin'";
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.execute();
			}
			connection.commit();
			return Boolean.TRUE;
		} catch (SQLException e) {
			connection.rollback();			
			throw new OrphanRemovalException("Existe telefones cadastrados para o usu�rio!");
		}
	}
	
	

	/**
	 * Metodo que consulta os usuarios cadastrados por ID, o unico usuario que não aparece na consulta e o admin
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public Usuario consultarPorId(String id) throws SQLException {
		Usuario usuario = null;		
		String sql = "SELECT * FROM usuario WHERE id = '" + id + "' AND login <> 'admin'";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			try (ResultSet result = statement.executeQuery()) {
				if (result.next()) {
					usuario = new Usuario();
					usuario.setId(result.getLong("id"));
					usuario.setLogin(result.getString("login"));
					usuario.setSenha(result.getString("senha"));
					usuario.setNome(result.getString("nome"));
					usuario.setCep(result.getString("cep"));
					usuario.setRua(result.getString("rua"));
					usuario.setBairro(result.getString("bairro"));
					usuario.setCidade(result.getString("cidade"));
					usuario.setEstado(result.getString("estado"));
					usuario.setIbge(result.getString("ibge"));
					usuario.setFotoBase64(result.getString("fotoBase64"));
					usuario.setFotoBase64Miniatura(result.getString("fotoBase64Miniatura"));
					usuario.setContentType(result.getString("contentType"));
					usuario.setCurriculoBase64(result.getString("curriculoBase64"));
					usuario.setContentTypeCurriculo(result.getString("contentTypeCurriculo"));
					usuario.setAtivo(result.getBoolean("ativo"));
					usuario.setSexo(result.getString("sexo"));
					usuario.setPerfil(result.getString("perfil"));
				}
			}
		}		
		return usuario;
	}
	
	

	public boolean validarLoginInsert(String login) throws SQLException {		
		String sql = "SELECT COUNT(1) AS qtde FROM usuario WHERE login = '" + login + "'";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			try (ResultSet result = statement.executeQuery()) {
				if (result.next()) {
					return result.getInt("qtde") <= 0;
				}
			}
		}	
		return false;
	}
	
	

	public boolean validarSenhaInsert(String senha) throws SQLException {		
		String sql = "SELECT COUNT(1) AS qtde FROM usuario WHERE senha = '" + senha + "'";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			try (ResultSet result = statement.executeQuery()) {
				if (result.next()) {
					return result.getInt("qtde") <= 0;
				}
			}
		}	
		return false;
	}
	
	

	public boolean validarLoginUpdate(String login, String id) throws SQLException {		
		String sql = "SELECT COUNT(1) AS qtde FROM usuario WHERE login = '" + login + "' AND id <> '" + id + "'";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			try (ResultSet result = statement.executeQuery()) {
				if (result.next()) {
					return result.getInt("qtde") <= 0;
				}
			}
		}	
		return false;
	}
	
	

	public boolean validarSenhaUpdate(String senha, String id) throws SQLException {		
		String sql = "SELECT COUNT(1) AS qtde FROM usuario WHERE senha = '" + senha + "' AND id <> '" + id + "'";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			try (ResultSet result = statement.executeQuery()) {
				if (result.next()) {
					return result.getInt("qtde") <= 0;
				}
			}
		}		
		return false;
	}
	
	

	public void atualizar(Usuario usuario) throws SQLException {
		
		try {
			StringBuilder sql = new StringBuilder();

			sql
			.append("UPDATE usuario SET login = ?, senha = ?, nome = ?, ")
			.append("cep = ?, rua = ?, bairro = ?, cidade = ?, estado = ?, ibge = ?");

			if (usuario.isAtualizarImagem()) {
				sql.append(", fotoBase64 = ?, contentType = ?, fotoBase64Miniatura = ?");
			}

			if (usuario.isAtualizarCurriculo()) {
				sql.append(", curriculoBase64 = ?, contentTypeCurriculo = ?");
			}
			
			sql
			.append(", ativo = ?, sexo = ?, perfil = ?")
			.append(" WHERE id = " + usuario.getId());

			try (PreparedStatement statement = connection.prepareStatement(sql.toString())) {

				int index = 1;

				statement.setString(index ++, usuario.getLogin());
				statement.setString(index ++, usuario.getSenha());
				statement.setString(index ++, usuario.getNome());
				statement.setString(index ++, usuario.getCep());
				statement.setString(index ++, usuario.getRua());
				statement.setString(index ++, usuario.getBairro());
				statement.setString(index ++, usuario.getCidade());
				statement.setString(index ++, usuario.getEstado());
				statement.setString(index ++, usuario.getIbge());

				if (usuario.isAtualizarImagem()) {
					statement.setString(index ++, usuario.getFotoBase64());
					statement.setString(index ++, usuario.getContentType());
					statement.setString(index ++, usuario.getFotoBase64Miniatura());
				}

				if (usuario.isAtualizarCurriculo()) {
					statement.setString(index ++, usuario.getCurriculoBase64());
					statement.setString(index ++, usuario.getContentTypeCurriculo());
				}
				
				statement.setBoolean(index ++, usuario.isAtivo());
				statement.setString(index ++, usuario.getSexo());
				statement.setString(index ++, usuario.getPerfil());
				statement.executeUpdate();
			}
			connection.commit();
		} catch (Exception e) {
			LogUtil.getLogger(UsuarioDAO.class).error(e.getCause().toString());
			connection.rollback();
		}
	}
	


}
