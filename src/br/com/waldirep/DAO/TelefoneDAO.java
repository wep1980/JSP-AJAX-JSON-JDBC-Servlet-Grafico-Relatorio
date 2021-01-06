package br.com.waldirep.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.waldirep.beans.Telefone;
import br.com.waldirep.connection.SingleConnectionHeroku;
import br.com.waldirep.util.LogUtil;

public class TelefoneDAO {
	
	

	private Connection connection;

	private static final String ID_USUARIO = "usuario_id";

	private static final String NUMERO = "numero";
	
	

	public TelefoneDAO() {
		connection = SingleConnectionHeroku.getConnection();
	}
	

	public void salvar(Telefone telefone) {
		try {
			String sql = "INSERT INTO telefone (numero, tipo, usuario_id) VALUES (?, ?, ?)";
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, telefone.getNumero());
				statement.setString(2, telefone.getTipo());
				statement.setLong(3, telefone.getUsuario());
				statement.execute();
				connection.commit();
			}
		} catch (Exception e) {
			LogUtil.getLogger(TelefoneDAO.class).error(e.getCause().toString());
			try {
				connection.rollback();
			} catch (SQLException e1) {
				LogUtil.getLogger(TelefoneDAO.class).error(e1.getCause().toString());
			}
		}
	}
	
	

	public List<Telefone> listar() {
		List<Telefone> telefones = new ArrayList<>();
		try {
			String sql = "SELECT * FROM telefone";
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				try (ResultSet result = statement.executeQuery()) {
					while (result.next()) {
						Telefone telefone = new Telefone();
						telefone.setId(result.getLong("id"));
						telefone.setNumero(result.getString(NUMERO));
						telefone.setTipo(result.getString("tipo"));
						telefone.setUsuario(result.getLong(ID_USUARIO));
						telefones.add(telefone);
					}
				}
			}
		} catch (Exception e) {
			LogUtil.getLogger(TelefoneDAO.class).error(e.getCause().toString());
		}
		return telefones;
	}
	
	

	public List<Telefone> listarTelefonesUsuario(Long usuario) {
		List<Telefone> telefones = new ArrayList<>();
		try {
			String sql = "SELECT * FROM telefone WHERE usuario_id = " + usuario;
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				try (ResultSet result = statement.executeQuery()) {
					while (result.next()) {
						Telefone telefone = new Telefone();
						telefone.setId(result.getLong("id"));
						telefone.setNumero(result.getString(NUMERO));
						telefone.setTipo(result.getString("tipo"));
						telefone.setUsuario(result.getLong(ID_USUARIO));
						telefones.add(telefone);
					}
				}
			}
		} catch (Exception e) {
			LogUtil.getLogger(TelefoneDAO.class).error(e.getCause().toString());
		}
		return telefones;
	}
	
	

	public void deletar(String id) {
		try {
			String sql = "DELETE FROM telefone WHERE id = '" + id + "'";
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.execute();
				connection.commit();
			}
		} catch (Exception e) {
			LogUtil.getLogger(TelefoneDAO.class).error(e.getCause().toString());
			try {
				connection.rollback();
			} catch (SQLException e1) {
				LogUtil.getLogger(TelefoneDAO.class).error(e1.getCause().toString());
			}
		}
	}
	
	

	public Telefone consultar(String id) {
		Telefone telefone = null;
		try {
			String sql = "SELECT * FROM telefone WHERE id = '" + id + "'";
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				try (ResultSet result = statement.executeQuery()) {
					if (result.next()) {
						telefone = new Telefone();
						telefone.setId(result.getLong("id"));
						telefone.setNumero(result.getString(NUMERO));
						telefone.setTipo(result.getString("tipo"));
						telefone.setUsuario(result.getLong(ID_USUARIO));
					}
				}
			}
		} catch (Exception e) {
			LogUtil.getLogger(TelefoneDAO.class).error(e.getCause().toString());
		}
		return telefone;
	}
	
	

	public void atualizar(Telefone telefone) {
		try {
			String sql = "UPDATE telefone SET numero = ?, tipo = ?, usuario_id = ? WHERE id = " + telefone.getId();
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, telefone.getNumero());
				statement.setString(2, telefone.getTipo());
				statement.setLong(3, telefone.getUsuario());
				statement.executeUpdate();
				connection.commit();
			}
		} catch (Exception e) {
			LogUtil.getLogger(TelefoneDAO.class).error(e.getCause().toString());
			try {
				connection.rollback();
			} catch (SQLException e1) {
				LogUtil.getLogger(TelefoneDAO.class).error(e1.getCause().toString());
			}
		}
	}
	
	

}
