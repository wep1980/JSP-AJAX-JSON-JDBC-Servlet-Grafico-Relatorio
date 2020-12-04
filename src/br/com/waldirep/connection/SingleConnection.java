package br.com.waldirep.connection;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Classe responsavel por fazer a conexão com banco de dados postgresql
 * @author wepbi
 *
 */
public class SingleConnection {

	
	private static String banco = "jdbc:postgresql://localhost:5432/projeto-jsp?autoReconnect=true";
	private static String password = "admin";
	private static String user = "postgres";
	private static Connection connection = null;
	

	// Garante que sempre havera uma conexão com banco de dados
	static {
		conectar();
	}
	

	public SingleConnection() {
		conectar();
	}
	

	private static void conectar() {
		try {
			if (connection == null) {
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(banco, user, password);
				connection.setAutoCommit(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao conectar com o banco de dados: " + e.getMessage());
		}
	}

	/**
	 * Metodo que retorne a conexão
	 * @return
	 */
	public static Connection getConnection() {
		return connection;
	}

}
