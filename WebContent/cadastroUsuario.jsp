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
         <td>Código:</td>
         <!-- readonly="readonly" -> Não permite que o campo seja editado -->
         <td><input type="text" id="id" name="id" value="${userEdit.id}" readonly="readonly"/></td>
       </tr>
     
       <tr>
         <td>Login:</td>
         <td><input type="text" id="login" name="login" value="${userEdit.login}"/></td>
       </tr>
     
        <tr>
         <td>Senha:</td>
         <td><input type="password" id="senha" name="senha" value="${userEdit.senha}"/></td>
       </tr>
       
     </table>
     <input type="submit" value="Salvar"/>
   </form>
   
   <table>
      <c:forEach items="${usuarios}" var="usuario">
         <tr>
           <td style="width: 150px"> <c:out value="${usuario.id}"></c:out> </td>
           <td style="width: 150px"> <c:out value="${usuario.login}"></c:out> </td>
           <td > <c:out value="${usuario.senha}"></c:out> </td>
           
           
           <td> <a href="salvarUsuario?acao=delete&usuario=${usuario.login}">Excluir</a> </td>
           <td> <a href="salvarUsuario?acao=editar&usuario=${usuario.login}">Editar</a> </td>
         </tr>
      </c:forEach>
      
   </table>
   
</body>
</html>