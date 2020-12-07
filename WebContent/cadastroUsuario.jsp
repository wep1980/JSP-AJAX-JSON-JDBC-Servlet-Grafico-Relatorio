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

<!--==================================== INICIO DA TABELA ==========================================-->	

	<link rel="stylesheet" type="text/css" href="resources/css/table-bootstrap.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="resources/css/table-animate.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="resources/css/table-select2.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="resources/css/table-perfect-scrollbar.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="resources/css/table-util.css">
	<link rel="stylesheet" type="text/css" href="resources/css/table-main.css">
	
<!--==================================== FIM DA TABELA ============================================-->
</head>
<body>
   <div class="container-contact100">
     <div class="wrap-contact100">
     
      <form action="salvarUsuario" method="post" class="contact100-form validate-form">
         <span class="contact100-form-title">
		   Cadastro de Usuários
		</span>
		
		<div class="wrap-input100 validate-input bg1">
          <span class="label-input100">Código : </span>
            <input class="input100" type="text" id="id" name="id" value="${userEdit.id}" readonly="readonly"/>
        </div>
        
        <div class="wrap-input100 validate-input bg1 rs1-wrap-input100" data-validate = "Enter Your name"> 
         <span class="label-input100">Nome :</span>
         <input type="text" id="nome" name="nome" value="${userEdit.nome}" class="input100" placeholder="Enter Your Name "/>
       </div>  
     
       <div class="wrap-input100 validate-input bg1 rs1-wrap-input100" data-validate = "Enter Your Login"> 
         <span class="label-input100">Login :</span>
         <input type="text" id="login" name="login" value="${userEdit.login}" class="input100" placeholder="Enter Your Login "/>
       </div>  
     
       <div class="wrap-input100 validate-input bg1 rs1-wrap-input100" data-validate = "Enter Your Login"> 
         <span class="label-input100">Senha :</span>
         <input type="text" id="senha" name="senha" value="${userEdit.senha}" class="input100" placeholder="Enter Your Senha "/>
       </div> 
        
       <div class="container-contact100-form-btn">
			<button type="submit" class="contact100-form-btn">
			   <span>Salvar<i class="fa fa-long-arrow-right m-l-7" aria-hidden="true"></i></span>
			</button>
	   </div>
   </form>
  
 </div>
 </div>

<div class="limiter">
 <div class="container-table100">
   <div class="wrap-table100">
     <div class="table100 ver5 m-b-110">
	  <div class="table100-head">
	    <table>
	       <thead>
			 <tr class="row100 head">
				<th class="cell100 column1">Nome</th>
				<th class="cell100 column2">Login</th>
				<th class="cell100 column3">Senha</th>
				<th class="cell100 column4">Editar</th>
				<th class="cell100 column5">Excluir</th>
			 </tr>
		  </thead>
	    </table>
	  </div> 
					
   <div class="table100-body js-pscroll">
	   <table>
	     <tbody>
	      <c:forEach items="${usuarios}" var="usuario">
	         <tr class="row100 body">
	           <td class="cell100 column1"><c:out value="${usuario.nome}"></c:out></td>
	           <td class="cell100 column2"><c:out value="${usuario.login}"></c:out></td>
	           <td class="cell100 column3"><c:out value="${usuario.senha}"></c:out></td>
	           
	           <td class="cell100 column5"> <a href="salvarUsuario?acao=editar&usuario=${usuario.login}">Editar</a> </td>
	           <td class="cell100 column4"> <a  href="salvarUsuario?acao=delete&usuario=${usuario.login}">Excluir</a> </td>
	         </tr>
	      </c:forEach>
	     </tbody> 
	   </table>
   </div>
  </div>
  </div>
  </div>
  </div>
 
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