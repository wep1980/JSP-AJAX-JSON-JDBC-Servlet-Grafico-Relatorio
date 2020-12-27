package br.com.waldirep.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.waldirep.DAO.LoginDAO;
import br.com.waldirep.util.LogUtil;

/**
 * Classe servlet que intercepta os dados do login
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final LoginDAO loginDAO = new LoginDAO();

	public LoginServlet() {
		super();
	}
	
	
	/**
	 * Intercepta o envio do formulario
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String login = request.getParameter("login"); //Pega o login digitado na frontend
			String senha = request.getParameter("senha"); //Pega a senha digitado na frontend

			if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {
				if (loginDAO.validarLogin(login, senha)) {
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("acessoliberado.jsp");
					requestDispatcher.forward(request, response);
				} else {
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("acessonegado.jsp");
					requestDispatcher.forward(request, response);
				}
			} else {
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
				requestDispatcher.forward(request, response);
			}
		} catch (Exception e) {
			LogUtil.getLogger(LoginServlet.class).error(e.getCause().toString());
		}
	}

}
