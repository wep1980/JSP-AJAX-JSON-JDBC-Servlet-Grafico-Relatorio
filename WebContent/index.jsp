<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <!-- JSTL -> Ajuda no desenvolvimento -->
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

   <form action="LoginServlet" method="post">
   
     <label>Login : </label> <br/>
     <input type="text" id="login" name="login"/>
     <br/>
     <label>Senha : </label> <br/>
     <input type="password" id="senha" name="senha"/>
     <br/>
     <br/>
     <input type="submit" value="Logar"/>
   </form>
</body>
</html>