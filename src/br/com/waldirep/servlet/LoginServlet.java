package br.com.waldirep.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.waldirep.DAO.LoginDAO;

/**
 * Classe servlet que intercepta os dados
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	
	private LoginDAO loginDAO = new LoginDAO();
	
    public LoginServlet() {
        super();
      
    }

    
    /**
     * Intercepta o formulario pegando pela URL os dados
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
		
	}
	

	/**
	 * Intercepta o envio do formulario
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		try {
			
		 String login = request.getParameter("login"); //Pega o login digitado na frontend
		 String senha = request.getParameter("senha"); //Pega a senha digitado na frontend
		 
		 if(loginDAO.validarLogin(login, senha)) {
			 RequestDispatcher dispatcher = request.getRequestDispatcher("acessoLiberado.jsp");
			 dispatcher.forward(request, response);
		 }else {
			 RequestDispatcher dispatcher = request.getRequestDispatcher("acessoNegado.jsp");
			 dispatcher.forward(request, response);
		 } 
		 } catch (Exception e) {
			
			}
	}
}
