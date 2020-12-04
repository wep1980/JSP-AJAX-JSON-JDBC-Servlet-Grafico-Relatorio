<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <!-- JSTL -> Ajuda no desenvolvimento -->
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
   <h3>Cadastro de usuario</h3>
      <form action="salvarUsuario" method="post">
     <table>
     
       <tr>
         <td>Login:</td>
         <td><input type="text" id="login" name="login"/></td>
       </tr>
     
        <tr>
         <td>Senha:</td>
         <td><input type="password" id="senha" name="senha"/></td>
       </tr>
       
     </table>
     <input type="submit" value="Salvar"/>
   </form>
   
   <table>
      <c:forEach items="${usuarios}" var="usuario">
         <tr>
           <td> <c:out value="${usuario.login}"></c:out> </td>
           <td> <c:out value="${usuario.senha}"></c:out> </td>
           <td> <a href="salvarUsuario?acao=delete&usuario=${usuario.login}">Excluir</a> </td>
         </tr>
      </c:forEach>
      
   </table>
   
</body>
</html>