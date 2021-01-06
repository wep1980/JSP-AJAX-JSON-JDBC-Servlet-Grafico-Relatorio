package br.com.waldirep.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.waldirep.DAO.ProdutoDAO;
import br.com.waldirep.beans.Produto;
import br.com.waldirep.util.LogUtil;

/**
 * Servlet implementation class ProdutoServlet
 */
@WebServlet("/salvarProduto")
public class ProdutoServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;    
	
	private final ProdutoDAO produtoDAO = new ProdutoDAO();	   
	
	private static final String PRODUTOS = "produtos";
	
	private static final String CATEGORIAS = "categorias";
	
	private static final String PAGINA_CADASTRO_PRODUTO = "/cadastroProduto.jsp";
	
	
	
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			String acao = request.getParameter("acao");
			String prod = request.getParameter("prod");
			
			redirecionarUsuario(acao, prod, request, response);
		} catch (Exception e) {
			LogUtil.getLogger(ProdutoServlet.class).error(e.getCause().toString());
		} 
	}
	
	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		
		try {
			String acao = request.getParameter("acao");
			
			if (acao != null && acao.equalsIgnoreCase("reset")) {
				listarTodosProdutos(request, response);		
			} else {			
				String id = request.getParameter("id");
				String nome = request.getParameter("nome");
				String quantidade = request.getParameter("quantidade");
				String valor = request.getParameter("valor");
				String categoria = request.getParameter("categoria");
				
				Produto produto = criarProduto(id, nome, quantidade, valor, categoria);
				
				validarCamposFormulario(nome, quantidade, valor, request, response, produto);			
				
				boolean nomeExiste = false;			
				
				nomeExiste = validarNomeInsert(id, nome);			
				
				request.setAttribute("msg", msg(nomeExiste, produto.getNome()));
				request.setAttribute("prod", produto);			
				
				if (id.isEmpty() && produtoDAO.validarNomeInsert(nome)) {
					produtoDAO.salvar(produto);
					request.setAttribute("prod", null);
				} else if (!id.isEmpty()) {
					
					nomeExiste = validarNomeUpdate(id, nome);	
					
					boolean atualizou = false;
					
					if (!nomeExiste) {
						atualizou = true;
						produtoDAO.atualizar(produto);						
					}
					
					if (!atualizou) { 
						request.setAttribute("msg", msg(nomeExiste, produto.getNome()));
						request.setAttribute("prod", produto);
					} else {
						request.setAttribute("prod", null);
					}
				}					
				listarTodosProdutos(request, response);
			}
		} catch (Exception e) {
			LogUtil.getLogger(ProdutoServlet.class).error(e.getCause().toString());
		} 
	}
	
	
	
	
	private boolean validarNomeInsert(String id, String nome) {
		return id == null || id.isEmpty() && !produtoDAO.validarNomeInsert(nome);
	}
	
	
	
	
	private boolean validarNomeUpdate(String id, String nome) {
		return !produtoDAO.validarNomeUpdate(nome, id);
	}
	
	
	
	/**
	 * Metodo que cria produto
	 * 
	 * @param id
	 * @param nome
	 * @param quantidade
	 * @param valor
	 * @param categoria
	 * @return
	 */
	private Produto criarProduto(String id, String nome, String quantidade,
			String valor, String categoria) {
		
		Produto produto = new Produto();
		produto.setId(!id.isEmpty() ? Long.parseLong(id) : null);
		produto.setNome(nome);
		
		if((quantidade != null && !quantidade.isEmpty()) && (valor != null && !valor.isEmpty())) {
			
			/**
			 * replaceAll("\\.", "") -> Retira do valor o .(ponto) e não coloca nada "" -- EXP : 10.500,20 = 10500,20
			 * replaceAll("\\,", ".") -> Retira onde tem ,(virgula) e coloca .(ponto) -- EXP : 10.500,20 = 10500.20
			 */
			String valorParse = valor.replace("R$", "").replaceAll("\\.", "").replaceAll("\\,", ".");
			
			produto.setQuantidade(Double.parseDouble(quantidade)); //
			produto.setValor(Double.parseDouble(valorParse));
		}
		produto.setCategoria(Long.parseLong(categoria));
		return produto;
	}
	
	
	
	
	private String msg(boolean nome, String nomeProduto) {
		
		String msg = "";
		
		if (nome) {
			msg = "Já existe um produto cadastrado com o nome " + nomeProduto + " !";
		}	
			
		return msg;
	}
	
	
	
	
	private void redirecionarUsuario(String acao, String prod, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {		
		
		if (acao != null) {
			
			if (acao.equalsIgnoreCase("deletar")) {
				deletarProduto(prod, request, response);
			} 
			
			if (acao.equalsIgnoreCase("editar")) {
				editarProduto(prod, request, response);
			} 
			
			if (acao.equalsIgnoreCase("listarTodos")) {
				listarTodosProdutos(request, response);
			}			
		} else {			
			listarTodosProdutos(request, response);
		}
	}
	
	
	
	
	private void editarProduto(String prod, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {		
		Produto produto = produtoDAO.consultar(prod);			
		RequestDispatcher view = request.getRequestDispatcher(PAGINA_CADASTRO_PRODUTO);
		request.setAttribute("prod", produto);
		request.setAttribute(PRODUTOS, produtoDAO.listar());
		request.setAttribute(CATEGORIAS, produtoDAO.listarCategorias());
		view.forward(request, response);		
	}
	
	
	
	
	private void listarTodosProdutos(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {		
		RequestDispatcher view = request.getRequestDispatcher(PAGINA_CADASTRO_PRODUTO);
		request.setAttribute(PRODUTOS, produtoDAO.listar());
		request.setAttribute(CATEGORIAS, produtoDAO.listarCategorias());
		view.forward(request, response);	
	}
	
	
	
	
	private void deletarProduto(String prod, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {		
		produtoDAO.deletar(prod);				
		RequestDispatcher view = request.getRequestDispatcher(PAGINA_CADASTRO_PRODUTO);
		request.setAttribute(PRODUTOS, produtoDAO.listar());
		request.setAttribute(CATEGORIAS, produtoDAO.listarCategorias());
		view.forward(request, response);		
	}
	
	
	
	
	private void validarCamposFormulario(String nome, String quantidade, String valor, 
			HttpServletRequest request, HttpServletResponse response, Produto produto) 
			throws ServletException, IOException {		
		if(nome == null || nome.isEmpty()) {
			redirecionarUsuarioValidarCamposFormulario(produto, "nome", request, response);			
		} else if(quantidade == null || quantidade.isEmpty()) {
			redirecionarUsuarioValidarCamposFormulario(produto, "quantidade", request, response);
		} else if(valor == null || valor.isEmpty()) {
			redirecionarUsuarioValidarCamposFormulario(produto, "valor", request, response);
		} 
	}
	
	
	
	
	private void redirecionarUsuarioValidarCamposFormulario(Produto produto, String campo, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {		
		RequestDispatcher view = request.getRequestDispatcher(PAGINA_CADASTRO_PRODUTO);
		request.setAttribute(PRODUTOS, produtoDAO.listar());
		request.setAttribute(CATEGORIAS, produtoDAO.listarCategorias());
		request.setAttribute("msg", declararMensagemParaUsuario(campo));
		request.setAttribute("prod", produto);
		view.forward(request, response);	
		return;
	}
	
	
	
	
	private String declararMensagemParaUsuario(String campo) {
		return "O campo " + campo.toUpperCase() + "é de preenchimento obrigatório";
	}
	
	
	

}