<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<body>

	<div id="container" style="width: 650px">

		<div id="header" style="background-color: #FFA500;">
			<h1 style="margin-bottom: 0;">Logowanie</h1>
		</div>
		<%@include file="menu.jsp" %>

		<div id="content">
			<form name="loginform" action="login_sprawdz" method="post">
				Enter User Name: <input type="text" name="username"><br />
				Enter Password: <input name="password" type="password"><br />
				<input type="submit" >
			</form>	
			<br />
			<br />
			Zapomniales hasla? <br />
			-> kliknij a otrzymasz nowe haslo na poczcie 
			<a href="/Logowanie/zapomnianeHaslo.jsp">KLIK</a>
			
		</div>

		<div id="footer"
			style="background-color: #FFA500; clear: both; text-align: center;">Jesli potrzeba jest matka wynalazkow, to zapewne zagrozenie jest matka kryptoanalizy.</div>

	</div>

</body>
</html>