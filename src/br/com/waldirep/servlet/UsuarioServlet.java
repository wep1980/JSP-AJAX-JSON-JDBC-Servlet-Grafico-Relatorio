package br.com.waldirep.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.waldirep.DAO.UsuarioDAO;
import br.com.waldirep.beans.Usuario;


@WebServlet("/salvarUsuario")
public class UsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	
   
    public UsuarioServlet() {
        super();
      
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		try {
			String acao = request.getParameter("acao");
			String usuario = request.getParameter("usuario");
			
			if(acao.equalsIgnoreCase("delete")) {
				usuarioDAO.deletar(usuario);
				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp"); // Mantem na mesma tela
				request.setAttribute("usuarios", usuarioDAO.listarTodos()); // Carrega a lista de usuarios e coloca na variavel usuarios para ser visualizado na tela
				view.forward(request, response);
			}
			else if (acao.equalsIgnoreCase("editar")) {
				Usuario usuarioEdit = usuarioDAO.consultar(usuario);
				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp"); // Mantem na mesma tela
				request.setAttribute("userEdit", usuarioEdit); // Carrega a lista de usuarios e coloca na variavel usuarios para ser visualizado na tela
				view.forward(request, response);
			}
			} catch (Exception e) {
				e.printStackTrace();
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String id = request.getParameter("id");
		String nome = request.getParameter("nome");
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		
		Usuario user = new Usuario();
		user.setId(!id.isEmpty() ? Long.parseLong(id) : 0); // Se existir um ID faz a conversão, senão vai colocar o valor 0
		user.setNome(nome);
		user.setLogin(login);
		user.setSenha(senha);
		
		if(id == null || id.isEmpty()) {
			usuarioDAO.salvar(user);
		} else {
			usuarioDAO.atualizar(user);
		}
		
		
		try {
			RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp"); // Mantem na mesma tela
			request.setAttribute("usuarios", usuarioDAO.listarTodos()); // Carrega a lista de usuarios e coloca na variavel usuarios para ser visualizado na tela
			view.forward(request, response);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}

}
