package br.com.waldirep.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.waldirep.DAO.TelefoneDAO;
import br.com.waldirep.beans.Telefone;
import br.com.waldirep.beans.Usuario;
import br.com.waldirep.service.UsuarioService;
import br.com.waldirep.util.LogUtil;

@WebServlet("/salvarTelefone")
public class TelefoneServlet extends HttpServlet {
	
	
	private static final long serialVersionUID = 1L;      
	
	private static final UsuarioService usuarioService = new UsuarioService();
	
	private final TelefoneDAO telefoneDAO = new TelefoneDAO();    
	
	private static final String USER_ESCOLHIDO = "userEscolhido";
	
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String user = request.getParameter("user"); // Captura o usuario que esta realizando a acão
		String acao = request.getParameter("acao");	// Captura a acao executada na tela	
		
		try {
			Usuario usuario = acao.equalsIgnoreCase("deletarFone") 
				? (Usuario) request.getSession().getAttribute(USER_ESCOLHIDO) // Pega o usuario logado na sessão
				: usuarioService.consultarPorId(user); // Consulta por id 
			redirecionarUsuario(acao, usuario, request, response);	
		} catch (Exception e) {
			try {
				listarTodosUsuarios(request, response);
			} catch (Exception e1) {				
				LogUtil.getLogger(TelefoneServlet.class).error(e1.getCause().toString());
			} 
		}	
	}	
	
	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			String numero = request.getParameter("numero"); // Capturando por parametro o numero digitado na tela
			String tipo = request.getParameter("tipo");	
			Usuario usuario = (Usuario) request.getSession().getAttribute(USER_ESCOLHIDO);	// recuperando o usuario logado na sessão	
			String acao = request.getParameter("acao"); // Capturando a acao de acordo com o botão selecionado na tela 
			
			// O campo de voltar esta no POST que o formulario esta sendo submetido
			if (acao != null && acao.equalsIgnoreCase("voltar")) { // Se a acao for diferente de null e for voltar, retorna para a tela anterior e carrega os usuarios
				listarTodosUsuarios(request, response); // Redireciona para tela de usuarios e lista todos eles
				return;
			}
			
			if (numero == null || numero.isEmpty()) { // Se o numero do telefone for nulo ou vazio a mensagem é exibida e a lista dos telefones do usuario logado
				request.setAttribute("msg", "O campo telefone é de preenchimento obrigatório!"); 
				listarTodosTelefonesUsuario(usuario, request, response);
				return;
			}
			
			Telefone telefone = criarTelefone(numero, tipo, usuario.getId()); // Cria o telefone
			
			telefoneDAO.salvar(telefone); // salva np BD
			request.setAttribute("msgSucesso", "Telefone registrado com sucesso!!!"); // Mostra na tela a messagem de sucesso		
			
			listarTodosTelefonesUsuario(usuario, request, response); // Apos salvar o telefone os numeros do usuario são listados na tela 
			
		} catch (Exception e) {
			LogUtil.getLogger(TelefoneServlet.class).error(e.getCause().toString());
		} 				
	}
	
	
	/**
	 * Metodo que redireciona e carrega a lista de usuarios 
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void listarTodosUsuarios(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {	
		
		RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp"); // redireciona para a tela de cadastro de usuários
		request.setAttribute("usuarios", usuarioService.listarTodos()); // carrega a lista de usuarios
		view.forward(request, response);		
	}
	
	
	/**
	 * Metodo que cria um novo Telefone
	 * @param numero
	 * @param tipo
	 * @param usuario
	 * @return
	 */
	private Telefone criarTelefone(String numero, String tipo, Long usuario) {
		Telefone telefone = new Telefone();		
		telefone.setNumero(numero);
		telefone.setTipo(tipo);
		telefone.setUsuario(usuario);
		return telefone;
	}
	
	
	
	private void redirecionarUsuario(String acao, Usuario usuario, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {	
		
		if (acao != null) {
			
			if (acao.equalsIgnoreCase("addFone")) {  // Se a ação for para adicionar um telefone
				listarTodosTelefonesUsuario(usuario, request, response);
			} 
			
			if (acao.equalsIgnoreCase("deletarFone")) { // se a ação for para deletar um telefone
				deletarTelefone(usuario, request, response);
			} 
		}
		
		listarTodosTelefonesUsuario(usuario, request, response);			
	}
	
	
	/**
	 * Metodo que deleta o telefone do usuario logado
	 * @param usuario
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void deletarTelefone(Usuario usuario, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {	
		String foneId = request.getParameter("foneId"); // Pega o o id do telefone selecionado na tela
		telefoneDAO.deletar(foneId); // deleta o telefone selecionado
		request.setAttribute("msgSucesso", "Telefone excluído com sucesso!"); // envia a messagem para o atributo "msgSucesso" e exibe na tela
		listarTodosTelefonesUsuario(usuario, request, response); // Lista todos os telefones do usuario que esta logado
	}	
	
	
	
	private void listarTodosTelefonesUsuario(Usuario usuario, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {		
		RequestDispatcher view = request.getRequestDispatcher("/telefones.jsp"); // redireciona para a tela de telefones
		request.getSession().setAttribute(USER_ESCOLHIDO, usuario);	// Pega o usuario logado na sessão
		request.setAttribute(USER_ESCOLHIDO, usuario); // Mantem o mesmo usuario logado após a deleção
		request.setAttribute("telefones", telefoneDAO.listarTelefonesUsuario(usuario.getId())); // lista na tela todos os telefones do usuario logado
		view.forward(request, response); 		
	}
	

}
