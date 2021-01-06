package br.com.waldirep.connection;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Classe responsavel por fazer a conex�o com banco de dados postgresql
 * @author wepbi
 *
 */
public class SingleConnectionHeroku {

	// URI -> postgres://mlodhsfpoguxgx:73553d4dc7f77855847eb23bb5dc4a7e39e7cc938271802e2a7d77ad6b439df2@ec2-54-156-73-147.compute-1.amazonaws.com:5432/dfm74unu3bnjf9
	private static String banco = "jdbc:postgresql://ec2-54-156-73-147.compute-1.amazonaws.com:5432/dfm74unu3bnjf9?autoReconnect=true";
	private static String password = "73553d4dc7f77855847eb23bb5dc4a7e39e7cc938271802e2a7d77ad6b439df2";
	private static String user = "mlodhsfpoguxgx";
	private static Connection connection = null;
	

	// Garante que sempre havera uma conex�o com banco de dados
	static {
		conectar();
	}
	

	public SingleConnectionHeroku() {
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
	 * Metodo que retorne a conex�o
	 * @return
	 */
	public static Connection getConnection() {
		return connection;
	}

}
