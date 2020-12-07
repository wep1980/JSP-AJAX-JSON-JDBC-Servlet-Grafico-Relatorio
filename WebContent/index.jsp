<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <!-- JSTL -> Ajuda no desenvolvimento -->
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<link rel="icon" type="image/png" href="images/icons/favicon.ico"/>
<link rel="stylesheet" type="text/css" href="resources/css/login-bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/css/login-animate.css">
<link rel="stylesheet" type="text/css" href="resources/css/login-hamburgers.min.css">
<link rel="stylesheet" type="text/css" href="resources/css/login-animsition.min.css">
<link rel="stylesheet" type="text/css" href="resources/css/login-select2.min.css">
<link rel="stylesheet" type="text/css" href="resources/css/login-daterangepicker.css">
<link rel="stylesheet" type="text/css" href="resources/css/login-util.css">
<link rel="stylesheet" type="text/css" href="resources/css/login-main.css">

<title>Login</title>
</head>
<body>

 <div class="limiter">
     <div class="container-login100">
	   <div class="wrap-login100">
			   <form action="LoginServlet" method="post" class="login100-form validate-form p-l-55 p-r-55 p-t-178">
			   
			      <span class="login100-form-title">Sing In</span>
			      
			      <div class="wrap-input100 validate-input m-b-16" data-validate = "Please enter username">
         			   <input type="text" id="login" name="login" class="input100" placeholder="Login :"/>
			           <span class="focus-input100"></span>
			      </div>
			      
			      <div class="wrap-input100 validate-input" data-validate="Please enter password">     
			           <input type="password" id="senha" name="senha" class="input100" placeholder="Senha :"/>
			           <span class="focus-input100"></span>
			      </div>
			     <br/>
			     <br/>
			     <div class="container-login100-form-btn">
			         <button type="submit" value="Logar" class="login100-form-btn">
			           Logar
			         </button>
			     </div>
			      
			     <div class="flex-col-c p-t-170 p-b-40">
				     <a href="#" class="txt3">Sign up now</a>
				</div>   
			   </form>
	     </div>
	 </div>
 </div>
 
 
 <!--===============================================================================================-->
	<script src="resources/js/login-jquery-3.2.1.min.js"></script>
<!--===============================================================================================-->
	<script src="resources/js/login-animsition.min.js"></script>
<!--===============================================================================================-->
	<script src="resources/js/login-popper.js"></script>
	<script src="resources/js/login-bootstrap.min.js"></script>
<!--===============================================================================================-->
	<script src="resources/js/login-select2.min.js"></script>
<!--===============================================================================================-->
	<script src="resources/js/login-moment.min.js"></script>
	<script src="resources/js/login-daterangepicker.js"></script>
<!--===============================================================================================-->
	<script src="resources/js/login-countdowntime.js"></script>
<!--===============================================================================================-->
	<script src="resources/js/login-main.js"></script>
 
 
 
 
 
 
 
 
 
			   
</body>
</html>