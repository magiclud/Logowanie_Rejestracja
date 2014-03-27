<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<body>

	<div id="container" style="width: 500px">

		<div id="header" style="background-color: #FFA500;">
			<h1 style="margin-bottom: 0;">Main Title of Web Page</h1>
		</div>
		<%@include file="menu.jsp" %>

		<div id="content">
			<form name="loginform" action="login_sprawdz" method="post">
				Enter User Name: <input type="text" name="username"><br />
				Enter Password: <input name="password" type="password"><br />
				<input type="submit" >
			</form>	
			
		</div>

		<div id="footer"
			style="background-color: #FFA500; clear: both; text-align: center;">Copyright
			© W3Schools.com</div>

	</div>

</body>
</html>