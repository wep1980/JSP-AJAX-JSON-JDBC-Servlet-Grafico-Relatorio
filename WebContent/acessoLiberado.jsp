<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
		<div style="padding-top: 10%;">		
			<h1 style="text-align: center;">Bem-vindo ao Sistema!</h1>
			<center>				
				<table>
					<tr>
						<td>
							<a href="salvarUsuario?acao=listarTodos">
								<img src="resources/img/user.png" alt="Cadastrar Usuário" title="Cadastrar Usuário" width="100px" height="100px">
							</a>
						</td>
						<td>
							<a href="salvarProduto?acao=listarTodos">
								<img src="resources/img/prod.png" alt="Cadastrar Produto" title="Cadastrar Produto" width="100px" height="100px">
							</a>
						</td>
					</tr>
					<tr>
						<td>Cad. Usuários</td>
						<td>Cad. Produtos</td>
					</tr>
				</table>			
			</center>
		</div>	
	</body>
</html>