<jsp:useBean id="calcula" class="br.com.waldirep.beans.Usuario" type="br.com.waldirep.beans.Usuario" scope="page"/>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib prefix="myprefix" uri="WEB-INF/testetag.tld" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Página de Login</title>
		<link rel="stylesheet" href="resources/css/estilo.css">
	</head>
	<body>		
		<div class="login-page">
			<h2 style="text-align: center;">Para acesso ao sistema</h2>
			<div style="text-align: center;">
				<span><b>USUÁRIO:</b> admin<br/><b>SENHA:</b> admin</span>
			</div><br/>
  			<div class="form">
				<form action="LoginServlet" method="post" class="login-form">				
					Login:
					<input type="text" id="login" name="login" /><br>
					
					Senha:
					<input type="text" id="senha" name="senha"  /><br>
					
					<button type="submit">Logar</button>		
				</form>		
			</div>
			<h3 style="text-align: center;">
			    <p>© Desenvolvido por Waldir Escouto Pereira </p>
				<a style="text-decoration: none;" href="https://www.linkedin.com/in/wepdev/" target="none">
				  <img src="resources/img/linkedin.png"  alt="linkedin">
				</a>
			</h3>
		</div>
	</body>
</html>