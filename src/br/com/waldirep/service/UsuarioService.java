package br.com.waldirep.service;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;


import br.com.waldirep.DAO.UsuarioDAO;
import br.com.waldirep.beans.Usuario;
import br.com.waldirep.exception.OrphanRemovalException;
import br.com.waldirep.util.LogUtil;

public class UsuarioService {

	
	private static final UsuarioDAO usuarioDAO = new UsuarioDAO();

	private static final String STRING_VAZIA = "";

	private static final String USUARIOS_ATTRIBUTE = "usuarios";
	
	private static final String PAGINA_CADASTRO_USUARIO = "cadastroUsuario.jsp";
	
	

	public void salvar(Usuario usuario) throws SQLException {
		usuarioDAO.salvar(usuario);		
	}
	

	private Boolean deletar(String id) throws OrphanRemovalException, SQLException {
		return usuarioDAO.deletar(id);		
	}
	
	
	private void validarUsuario(String user, HttpServletRequest request) {
		Usuario usuario = consultarPorId(user);
		if (usuario != null) {
			request.setAttribute("msg", "Existe telefones cadastrados para o usuário " + 
				usuario.getNome());
		}
	}	
	
	
	private void editarUsuarioRedirecionar(String user, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {		
		String[] attributes = {USUARIOS_ATTRIBUTE, "user"};
		Usuario usuario = consultarPorId(user);
		redirecionarUsuario(attributes, request, response, STRING_VAZIA, usuario, PAGINA_CADASTRO_USUARIO);						
	}
	
	
	public void listarTodosUsuariosRedirecionar(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {		
		String[] attributes = {USUARIOS_ATTRIBUTE};
		redirecionarUsuario(attributes, request, response, STRING_VAZIA, new Usuario(), PAGINA_CADASTRO_USUARIO);		
	}
	

	public void atualizar(Usuario usuario) throws SQLException {
		usuarioDAO.atualizar(usuario);
	}
	
	
	public Usuario criarUsuario(HttpServletRequest request, String[] dadosUsuario) {		
		Usuario usuario = new Usuario();			
		usuario.setId(!getValue(request, dadosUsuario[0]).isEmpty() 
			? Long.parseLong(getValue(request, dadosUsuario[0])) : null);
		usuario.setLogin(getValue(request, dadosUsuario[1]));
		usuario.setSenha(getValue(request, dadosUsuario[2]));
		usuario.setNome(getValue(request, dadosUsuario[3]));
		usuario.setCep(getValue(request, dadosUsuario[4]));
		usuario.setRua(getValue(request, dadosUsuario[5]));
		usuario.setBairro(getValue(request, dadosUsuario[6]));
		usuario.setCidade(getValue(request, dadosUsuario[7]));
		usuario.setEstado(getValue(request, dadosUsuario[8]));
		usuario.setIbge(getValue(request, dadosUsuario[9]));		
		usuario.setAtivo(verificarStatus(getValue(request, dadosUsuario[10])));
		usuario.setSexo(getValue(request, dadosUsuario[11]));
		usuario.setPerfil(getValue(request, dadosUsuario[12]));	
		return usuario;		
	}
	

	public Usuario consultarPorId(String id) {
		Usuario usuario = null;
		try {
			usuario = usuarioDAO.consultarPorId(id);
		} catch (Exception e) {
			LogUtil.getLogger(UsuarioService.class).error(e.getCause().toString());
		}
		return usuario;
	}
	
	
	public List<Usuario> listarPorNome(String descricaoConsulta) {
		List<Usuario> usuarios = null;
		try {
			usuarios = usuarioDAO.listarPorNome(descricaoConsulta);
		} catch (Exception e) {
			LogUtil.getLogger(UsuarioService.class).error(e.getCause().toString());
		}
		return usuarios;
	}
	

	public List<Usuario> listarTodos() {
		List<Usuario> usuarios = null;
		try {
			usuarios = usuarioDAO.listarTodos();
		} catch (Exception e) {
			LogUtil.getLogger(UsuarioService.class).error(e.getCause().toString());
		}
		return usuarios;
	}
	
	
	private void redirecionarUsuarioValidarCamposFormulario(Usuario usuario, String campo, 
			HttpServletRequest request, HttpServletResponse response) {	
		String[] attributes = {USUARIOS_ATTRIBUTE, "msg", "user"};
		try {
			redirecionarUsuario(attributes, request, response, campo, usuario, PAGINA_CADASTRO_USUARIO);			
		} catch (Exception e) {
			LogUtil.getLogger(UsuarioService.class).error(e.getCause().toString());
		}					
	}
	
	
	public String getValue(HttpServletRequest request, String valor) {
		return request.getParameter(valor);
	}
	
	
	public void validarCamposFormulario(String[] dadosUsuario,
			HttpServletRequest request, HttpServletResponse response, Usuario usuario) {		
		if(getValue(request, dadosUsuario[1]) == null || getValue(request, dadosUsuario[1]).isEmpty()) {
			redirecionarUsuarioValidarCamposFormulario(usuario, "login", request, response);
			return;
		} else if(getValue(request, dadosUsuario[2]) == null || getValue(request, dadosUsuario[2]).isEmpty()) {
			redirecionarUsuarioValidarCamposFormulario(usuario, "senha", request, response);
			return;
		} else if(getValue(request, dadosUsuario[3]) == null || getValue(request, dadosUsuario[3]).isEmpty()) {
			redirecionarUsuarioValidarCamposFormulario(usuario, "nome", request, response);
			return;
		} 
	}	
	


	/**
	 * Metodo que grava a imagem no banco de dados
	 * 
	 * @param request
	 * @param usuario
	 * @throws IOException
	 * @throws ServletException
	 */
	public void enviarImagem(HttpServletRequest request, Usuario usuario) throws IOException, ServletException {
		
		if (ServletFileUpload.isMultipartContent(request)) { // Valida se é um formulario de upload

			Part imagemFoto = request.getPart("foto"); // Pegando a imagem do campo da foto

			if (imagemFoto != null && imagemFoto.getInputStream().available() > 0) { // Se for uma imagem nova

				String fotoBase64 = Base64.encodeBase64String(converteStremParaByte(imagemFoto.getInputStream()));

				usuario.setFotoBase64(fotoBase64); 
				usuario.setContentType(imagemFoto.getContentType());
				usuario.setFotoBase64Miniatura(criarMiniaturaImagem(fotoBase64)); // Gravando a miniatura da imagem
				
			} else {
				usuario.setAtualizarImagem(Boolean.FALSE); // Se não for uma imagem nova mantem a mesma
			}
		}
	}
	
	
	
	/**
	 * Metodo que grava a PDF no banco de dados
	 * 
	 * @param request
	 * @param usuario
	 * @throws IOException
	 * @throws ServletException
	 */
	public void enviarCurriculo(HttpServletRequest request, Usuario usuario) throws IOException, ServletException {
			
			if (ServletFileUpload.isMultipartContent(request)) {
	
				Part curriculo = request.getPart("curriculo");
	
				if (curriculo != null && curriculo.getInputStream().available() > 0) {
					String curriculoBase64 = Base64.encodeBase64String(converteStremParaByte(curriculo.getInputStream()));
	
					usuario.setCurriculoBase64(curriculoBase64);
					usuario.setContentTypeCurriculo(curriculo.getContentType());
				} else {
					usuario.setAtualizarCurriculo(Boolean.FALSE);
				}
			}
		}
	

	// Converte a entrada de fluxo de dados da imagem para byte[]
	private byte[] converteStremParaByte(InputStream imagem) throws IOException {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int reads = imagem.read();
		while (reads != -1) {
			baos.write(reads);
			reads = imagem.read();
		}
		return baos.toByteArray(); // retorna(transforma) o fluxo em array de bytes
	}
	

	/**
	 * Metodo que cria as imagens em miniatura para serem carregadas na tela
	 * @param fotoBase64
	 * @return
	 * @throws IOException
	 */
	private String processarCriacaoMiniaturaImagem(String fotoBase64) throws IOException {
		
		String miniaturaBase64 = "";

		byte[] imageByteDecode = Base64.decodeBase64(fotoBase64); // Decodificando a imagem que veio do formulario 

		BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageByteDecode)); // Transformando a imagem em fluxo de entrada de arrays
		
		// Tipo da Imagem, se não tiver nenhum tipo definido da imagem ela sera do tipo YPE_INT_ARGB, senão pega o tipo da imagem que ja existe
		int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType(); 

		BufferedImage resizedImage = new BufferedImage(100, 100, type); // Define o tamanho da imagem, largura - Altura e Tipo

		Graphics2D g = resizedImage.createGraphics(); // Iniciando o processo de criação da miniatura

		g.drawImage(bufferedImage, 0, 0, 100, 100, null); // Transformando a imagem em miniatura

		g.dispose(); // Finaliza o processo

		ByteArrayOutputStream baos = new ByteArrayOutputStream(); // Escrevendo a imagem na saida
		ImageIO.write(resizedImage, "png", baos);

		// Gravando a miniatura no banco de dados pronta para ser exibida na tela
		miniaturaBase64 = "data:image/png;base64," + DatatypeConverter.printBase64Binary(baos.toByteArray()); 

		return miniaturaBase64;
	}
	

	private String criarMiniaturaImagem(String fotoBase64) {
		
		try {
			return processarCriacaoMiniaturaImagem(fotoBase64);
		} catch (IOException e) {
			LogUtil.getLogger(UsuarioService.class).error(e.getCause().toString());
		}
		return STRING_VAZIA;
	}
	

	
	/**
	 * Método que faz download do arquivo (foto , ou PDF)
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void fazerDownload(String user, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		Usuario usuario = consultarPorId(user); // Consulta o usuario por ID
		
		if (usuario != null) {
			
			String contentType = "";
			byte[] fileBytes = null; // array de bytes
			String tipo = request.getParameter("tipo"); // Pega o tipo de arquivo que usuario clicou na tela
			
			if (tipo.equalsIgnoreCase("foto")) {
				contentType = usuario.getContentType();
				fileBytes = Base64.decodeBase64(usuario.getFotoBase64()); // Colocando a foto em um array de bates
			}
			
			if (tipo.equalsIgnoreCase("curriculo")) {
				contentType = usuario.getContentTypeCurriculo();
				fileBytes = Base64.decodeBase64(usuario.getCurriculoBase64()); // Colocando o PDF em um array de bates
			}
			
			// Baixa a imagem diretamente. split("\\/")[1] -> regex que quebra o texto depois da / e armazena na posição do [1] do array a parte quebrada
			response.setHeader("Content-Disposition", "attachment;filename=arquivo." + contentType.split("\\/")[1]); // filename -> nome do arquivo
			
			InputStream is = new ByteArrayInputStream(fileBytes); // Colocando o arquivo que esta em forma de bytes em um inputStream(Objeto de entrada) para ser processado
			int read = 0; // Faz o controle do fluxo
			byte[] bytes = new byte[1024]; // Byte de saida com tamanho padrão
			
			
			OutputStream os = response.getOutputStream(); // Saida do fluxo(arquivo) para o navegador na resposta
			while ((read = is.read(bytes)) != -1) { // Enquanto tiver conteúdo o arquivo sera lido
				os.write(bytes, 0, read); // escreve no objeto de saida que começa em 0 e vai ate o fim do tamanho do read
			}
			os.flush(); // finaliza
			os.close();	// fecha o fluxo		
		}
	}
	

	private String declararMensagemParaUsuario(String campo) {
		return "O campo " + campo.toUpperCase() + "é de preenchimento obrigatório";
	}
	

	public boolean validarLoginInsert(String id, String login) throws SQLException {
		return id == null || id.isEmpty() && !usuarioDAO.validarLoginInsert(login);
	}
	

	public boolean validarLoginInsert(String login) throws SQLException {
		return usuarioDAO.validarLoginInsert(login);
	}
	

	public boolean validarSenhaInsert(String id, String senha) throws SQLException {
		return id == null || id.isEmpty() && !usuarioDAO.validarSenhaInsert(senha);
	}
	

	public boolean validarSenhaInsert(String senha) throws SQLException {
		return usuarioDAO.validarSenhaInsert(senha);
	}
	

	public boolean validarLoginUpdate(String id, String login) throws SQLException {
		return !usuarioDAO.validarLoginUpdate(login, id);
	}
	

	public boolean validarSenhaUpdate(String id, String senha) throws SQLException {
		return !usuarioDAO.validarSenhaUpdate(senha, id);
	}
	

	private void redirecionarUsuario(String[] attributes, HttpServletRequest request, HttpServletResponse response,
			String campo, Usuario usuario, String pagina) throws ServletException, IOException {
		
		RequestDispatcher view = request.getRequestDispatcher("/" + pagina);
		
		for (String attribute : attributes) {
			if (attribute.equals(USUARIOS_ATTRIBUTE)) {
				request.setAttribute(USUARIOS_ATTRIBUTE, listarTodos());
			}
			if (attribute.equals("msg")) {
				request.setAttribute("msg", declararMensagemParaUsuario(campo));
			}
			if (attribute.equals("user")) {
				request.setAttribute("user", usuario);
			}
		}
		view.forward(request, response);
	}
	
	
	public void executarAcao(String acao, String user, HttpServletRequest request, 
			HttpServletResponse response) throws IOException, ServletException, SQLException {		
		
		String[] attributesDeletarUsuario = {USUARIOS_ATTRIBUTE};
		
		if (acao != null) {
			
			if (acao.equalsIgnoreCase("deletar")) {
				deletarUsuarioRedirecionar(user, request, response, attributesDeletarUsuario);
				return;
			} 
			
			if (acao.equalsIgnoreCase("editar")) {
				editarUsuarioRedirecionar(user, request, response);				
				return;
			} 
			
			if (acao.equalsIgnoreCase("listarTodos")) {
				listarTodosUsuariosRedirecionar(request, response);
				return;
			} 
			
			if (acao.equalsIgnoreCase("download")) {
				fazerDownload(user, request, response);
			}
		}		
	}
	
	
	private Boolean deletarUsuario(String user) throws SQLException {		
		
		Boolean usuarioDeletado = Boolean.FALSE;
		try {
			usuarioDeletado = deletar(user);
		} catch (OrphanRemovalException e) {
			LogUtil.getLogger(UsuarioService.class).error(e.getCause().toString());
		}		
		return usuarioDeletado;
	}
	
	
	private void deletarUsuarioRedirecionar(String user, HttpServletRequest request, HttpServletResponse response,
			String[] attributesDeletarUsuario) throws ServletException, IOException, SQLException {
		
		if (deletarUsuario(user) != null) {
			redirecionarUsuario(attributesDeletarUsuario, request, response, STRING_VAZIA, new Usuario(), PAGINA_CADASTRO_USUARIO);			
		}
		validarUsuario(user, request);
	}	
	
	
	private Boolean verificarStatus(String ativo) {
		return ativo != null && ativo.equalsIgnoreCase("on");
	}
	
	
	public String definirMensagem(boolean login, boolean senha) {
		
		String msg = "";
		
		if (login && !senha) {
			msg = "O login informado já existe na base de dados para outro usuário!";
		}
		
		if (!login && senha) {
			msg = "A senha informada já existe na base de dados para outro usuário!";
		}
		
		if (login && senha) {
			msg = "A senha e login informados já existem na base de dados para outro usuário!";
		}		
		return msg;
	}
	

	
	

}
