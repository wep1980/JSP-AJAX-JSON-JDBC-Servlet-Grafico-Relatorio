package br.com.waldirep.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.waldirep.beans.Categoria;
import br.com.waldirep.beans.Produto;
import br.com.waldirep.connection.SingleConnectionHeroku;
import br.com.waldirep.util.LogUtil;

public class ProdutoDAO {
	

private Connection connection;
	
	public ProdutoDAO() {
		connection = SingleConnectionHeroku.getConnection();
	}
	
	public void salvar(Produto produto) {
		try {
			String sql = "INSERT INTO produto (nome, valor, quantidade, categoria_id) VALUES (?, ?, ?, ?)";
			try(PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, produto.getNome());
				statement.setDouble(2, produto.getValor());
				statement.setDouble(3, produto.getQuantidade());
				statement.setLong(4, produto.getCategoria());
				statement.execute();
				connection.commit();
			}			
		} catch (Exception e) {
			LogUtil.getLogger(ProdutoDAO.class).error(e.getCause().toString());
			try {
				connection.rollback();
			} catch (SQLException e1) {				
				LogUtil.getLogger(ProdutoDAO.class).error(e1.getCause().toString());
			}			
		}
	}
	
	public List<Produto> listar() {
		List<Produto> produtos = new ArrayList<>();
		try {			
			String sql = "SELECT * FROM produto";
			try (PreparedStatement statement = connection.prepareStatement(sql)) { 
				try (ResultSet result = statement.executeQuery()) {
					while(result.next()) {
						Produto produto = new Produto();
						produto.setId(result.getLong("id"));
						produto.setNome(result.getString("nome"));
						produto.setValor(result.getDouble("valor"));
						produto.setQuantidade(result.getDouble("quantidade"));
						produto.setCategoria(result.getLong("categoria_id"));
						produtos.add(produto);			
					}
				}	
			}
		} catch (Exception e) {
			LogUtil.getLogger(ProdutoDAO.class).error(e.getCause().toString());
		}
		return produtos;
	}
	
	public List<Categoria> listarCategorias() {
		List<Categoria> categorias = new ArrayList<>();
		try {
			String sql = "SELECT * FROM categoria";
			try (PreparedStatement statement = connection.prepareStatement(sql)) { 
				try (ResultSet result = statement.executeQuery()) {
					while(result.next()) {
						Categoria categoria = new Categoria();
						categoria.setId(result.getLong("id"));
						categoria.setNome(result.getString("nome"));				
						categorias.add(categoria);			
					}	
				}
			}
		} catch (Exception e) {
			LogUtil.getLogger(ProdutoDAO.class).error(e.getCause().toString());
		}
		return categorias;
	}
	
	public void deletar(String id) {
		try {
			String sql = "DELETE FROM produto WHERE id = '" + id + "'";
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.execute();
				connection.commit();
			}
		} catch (Exception e) {
			LogUtil.getLogger(ProdutoDAO.class).error(e.getCause().toString());
			try {
				connection.rollback();
			} catch (SQLException e1) {
				LogUtil.getLogger(ProdutoDAO.class).error(e.getCause().toString());
			}
		}
	}
	
	public Produto consultar(String id) {
		Produto produto = null;
		try {
			String sql = "SELECT * FROM produto WHERE id = '" + id + "'";
			try (PreparedStatement statement = connection.prepareStatement(sql)) { 
				try (ResultSet result = statement.executeQuery()) {
					if (result.next()) {				
						produto = new Produto();
						produto.setId(result.getLong("id"));
						produto.setNome(result.getString("nome"));
						produto.setValor(result.getDouble("valor"));
						produto.setQuantidade(result.getDouble("quantidade"));
						produto.setCategoria(result.getLong("categoria_id"));
					}			
				}
			}
		} catch (Exception e) {
			LogUtil.getLogger(ProdutoDAO.class).error(e.getCause().toString());
		}
		return produto;
	}	
	
	public boolean validarNomeInsert(String nome) {
		try {
			String sql = "SELECT COUNT(1) AS qtde FROM produto WHERE nome = '" + nome + "'";
			try (PreparedStatement statement = connection.prepareStatement(sql)) { 
				try (ResultSet result = statement.executeQuery()) {
					if (result.next()) {				
						return result.getInt("qtde") <= 0;
					}			
				}
			}
		} catch (Exception e) {
			LogUtil.getLogger(ProdutoDAO.class).error(e.getCause().toString());
		}
		return false;
	}
	
	public boolean validarNomeUpdate(String nome, String id) {
		try {
			String sql = "SELECT COUNT(1) AS qtde FROM produto WHERE nome = '" + nome + "' AND id <> '" + id + "'";
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				try (ResultSet result = statement.executeQuery()) {
					if (result.next()) {				
						return result.getInt("qtde") <= 0;
					}			
				}
			}
		} catch (Exception e) {
			LogUtil.getLogger(ProdutoDAO.class).error(e.getCause().toString());
		}
		return false;
	}
	
	public void atualizar(Produto produto) {
		try {
			String sql = "UPDATE produto SET nome = ?, valor = ?, quantidade = ?, categoria_id = ? WHERE id = " + produto.getId();
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, produto.getNome());
				statement.setDouble(2, produto.getValor());
				statement.setDouble(3, produto.getQuantidade());
				statement.setLong(4, produto.getCategoria());
				statement.executeUpdate();
				connection.commit();
			}
		} catch (Exception e) {
			LogUtil.getLogger(ProdutoDAO.class).error(e.getCause().toString());
			try {
				connection.rollback();
			} catch (SQLException e1) {
				LogUtil.getLogger(ProdutoDAO.class).error(e.getCause().toString());
			}
		}		
	}

}
