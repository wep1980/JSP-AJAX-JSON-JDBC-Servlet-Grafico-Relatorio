<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <!-- JSTL -> Ajuda no desenvolvimento -->
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<!--====================================  CADASTRO  ==============================================-->

	<link rel="icon" type="image/png" href="images/icons/favicon.ico"/>
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="resources/css/cadastro-bootstrap.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="resources/css/cadastro-material-design-iconic-font.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="resources/css/cadastro-animate.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="resources/css/cadastro-hamburgers.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="resources/css/cadastro-animsition.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="resources/css/cadastro-select2.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="resources/css/cadastro-daterangepicker.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="resources/css/cadastro-nouislider.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="resources/css/cadastro-util.css">
	<link rel="stylesheet" type="text/css" href="resources/css/cadastro-main.css">
	
<!--==================================== FIM CADASTRO ==============================================-->

</head>

		<style>
		
		   .content-table
		   {
		     border-collapse: collapse;
		     font-family: monospace;
		     margin:0;
		     font-size: 1.0em;
		     width: 100%;
		     border-radius: 5px 5px 0 0;
		     overflow: hidden;
		     box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
		   }
		   
		   .content-table thead tr 
		   {
		     background-color: #57b846;
		     color: #fff;
		     text-align: left;
		     font-weight: bold;
		     text-transform: uppercase;
		   }
		   
		   .content-table th,
		   .content-table td
		   {
		      padding: 12px 15px;
		   }
		   
		   .content-table tbody tr
		   {
		     border-bottom: 1px solid #dddddd;
		   }
		   
		   .content-table tbody tr:nth-of-type(even)
		   {
		     background-color: #FFF0F5;
		   }
		   
		   .content-table tbody tr:last-of-type
		   {
		     border-bottom: 15px solid #57b846;
		   }
		   
		   .content-table tbody tr.active-row
		   {
		     font-weight: bold;
		     color:  #57b846;
		   }
		   
		</style>

<body>
   <div class="container-contact100">
     <div class="wrap-contact100">
     
      <form action="salvarUsuario" method="post" class="contact100-form validate-form" id="formUser">
         <span class="contact100-form-title" style="text-transform: uppercase;
		       font-size: 30px; font-weight: bold;">
		   Cadastro de Usuários
		</span> 
		
		<span class="contact100-form-title">
		   <h3 style="
		       background-color: #d40000; 
		       color: #ffffff;
		       text-transform: uppercase;
		       font-size: 20px;
		       ">${msg}</h3>
		</span>
		
		
		<div>
            <input type="hidden" id="id" name="id" value="${userEdit.id}" readonly="readonly"/>
        </div>
        
        <div class="wrap-input100 validate-input bg1 rs1-wrap-input100"> 
         <span class="label-input100">Nome :</span>
         <input type="text" id="nome" name="nome" value="${userEdit.nome}" class="input100" placeholder="Digite seu nome "/>
       </div>  
     
       <div class="wrap-input100 validate-input bg1 rs1-wrap-input100"> 
         <span class="label-input100">Login :</span>
         <input type="text" id="login" name="login" value="${userEdit.login}" class="input100" placeholder="Digite seu login "/>
       </div>  
     
       <div class="wrap-input100 validate-input bg1 rs1-wrap-input100"> 
         <span class="label-input100">Senha :</span>
         <input type="text" id="senha" name="senha" value="${userEdit.senha}" class="input100" placeholder="Digite sua senha "/>
       </div> 
       
        <div class="wrap-input100 validate-input bg1 rs1-wrap-input100"> 
         <span class="label-input100">Telefone :</span>
         <input type="text" id="telefone" name="telefone" value="${userEdit.telefone}" class="input100" placeholder="Digite seu telefone "/>
       </div> 
        
       <div class="container-contact100-form-btn">
			<button type="submit" class="contact100-form-btn">
			   <span>Salvar<i class="fa fa-long-arrow-right m-l-7" aria-hidden="true"></i></span>
			</button>
	   </div>
	   
	   <div class="container-contact100-form-btn">
			<button type="submit" class="contact100-form-btn" style="background-color: #FF0000;" onclick="document.getElementById('formUser').action = 'salvarUsuario?acao=reset'">
			   <span style="">Cancelar<i class="fa fa-long-arrow-right m-l-7" aria-hidden="true"></i></span>
			</button>
	   </div>
	   
   </form>
  
 </div>
 </div>

       <table class="content-table">
             <thead>
		           <tr>
				      <th>Código</th>
				      <th>Nome</th>
				      <th>Telefone</th>
				      <th>Login</th>
				      <th>Senha</th>
				      <th>Editar</th>
				      <th>Excluir</th>
		           </tr>
             </thead>
             
             <tbody>
	               <c:forEach items="${usuarios}" var="user">
	                <tr >
	                   <td ><c:out value="${user.id}"></c:out></td>
		               <td ><c:out value="${user.nome}"></c:out></td>
		               <td ><c:out value="${user.telefone}"></c:out></td>
		               <td ><c:out value="${user.login}"></c:out></td>
		               <td ><c:out value="${user.senha}"></c:out></td>
		               
		               <td > <a style="text-transform: uppercase; font-size: 12px; color: #1E90FF; font-family: monospace;" href="salvarUsuario?acao=editar&usuario=${user.id}">Editar</a> </td>
	                   <td > <a style="text-transform: uppercase; font-size: 12px; color: #FF0000; font-family: monospace;" href="salvarUsuario?acao=delete&usuario=${user.id}">Excluir</a> </td>
	                </tr>
	               </c:forEach> 
             </tbody>
       </table>

  
  
 <!--===============================================================================================-->	
	<script src="resources/js/table-jquery-3.2.1.min.js"></script>
<!--===============================================================================================-->
	<script src="resources/js/table-popper.js"></script>
	<script src="resources/js/table-bootstrap.min.js"></script>
<!--===============================================================================================-->
	<script src="resources/js/table-select2.min.js"></script>
<!--===============================================================================================-->
	<script src="resources/js/table-perfect-scrollbar.min.js"></script>
	<script>
		$('.js-pscroll').each(function(){
			var ps = new PerfectScrollbar(this);

			$(window).on('resize', function(){
				ps.update();
			})
		});
			
		
	</script>
<!--===============================================================================================-->
	<script src="resources/js/table-main.js"></script>
   
</body>
</html>