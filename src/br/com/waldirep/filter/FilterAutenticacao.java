package br.com.waldirep.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import br.com.waldirep.connection.SingleConnection;
import br.com.waldirep.util.LogUtil;

/**
 * Classe que filtra as requisi��es e respostas(Intercepta)
 * @author wepbi
 *
 */
@WebFilter(urlPatterns = {"/*"})
public class FilterAutenticacao implements Filter{
	
	
	private Connection connection;	

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		try {
			chain.doFilter(request, response); // Intercepta os requiso��es e envia as respostas
			connection.commit();
		} catch (Exception e) {
			LogUtil.getLogger(FilterAutenticacao.class).error(e.getCause().toString());	
			try {
				connection.rollback();
			} catch (SQLException e1) {
				LogUtil.getLogger(FilterAutenticacao.class).error(e1.getCause().toString());	
			}
		}
	}

	/**
	 * Metodo que e sempre invocado, e chama o getConnection
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		connection = SingleConnection.getConnection(); // Recebe a conexão que foi iniciada
	}

}
