<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<body>

	<div id="container" style="width: 650px">

		<div id="header" style="background-color: #FFA500;">
			<h1 style="margin-bottom: 0;">Logowanie</h1>
		</div>
		<%@include file="menu.jsp"%>

		<div id="content"
			style="background-color: #EEEEEE; height: 400px; width: 500px; float: left;">
			<form name="loginform" action="login_sprawdz" method="post">
				<br />Enter User Name: <input type="text" name="username"><br />
				<br />Enter Password: <input name="password" type="password"><br /><br />
				<input type="submit">
			</form>
			<br /> <br /> Zapomniales hasla? <br /> -> <a
				href="/Logowanie/zapomnianeHaslo.jsp">kliknij </a>a otrzymasz nowe
			haslo na poczcie

		</div>

		<div id="footer"
			style="background-color: #FFA500; clear: both; text-align: center;">Jesli
			potrzeba jest matka wynalazkow, to zapewne zagrozenie jest matka
			kryptoanalizy.</div>

	</div>

</body>
</html>