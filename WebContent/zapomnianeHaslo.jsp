<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<body>

	<div id="container" style="width: 650px">

		<div id="header" style="background-color: #FFA500;">
			<h1 style="margin-bottom: 0;"></h1>
		</div>
		<%@include file="menu.jsp" %>

		<div id="content">
			<br />Na address email zostanie wysłane nowe hasło, po zalgowaniu zalezana jest zmiana hasła.
			<br />
			<br />
			<form name="odzyskiwanieHasla" action="haslo_sprawdz" method="post">
				Enter e-mail address: <input type="text" name="email"><br />
				<input type="submit" >
			</form>	
			<br />
			<a href="login.jsp">Logowanie</a>
			
		</div>

		<div id="footer"
			style="background-color: #FFA500; clear: both; text-align: center;">Jesli potrzeba jest matka wynalazkow, to zapewne zagrozenie jest matka kryptoanalizy.</div>

	</div>

</body>
</html>