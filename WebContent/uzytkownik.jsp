<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<body>

	<div id="container" style="width: 850px">

		<div id="header" style="background-color: #FFA500;">
			<h1 style="margin-bottom: 0;">Uzytkownik</h1>

		</div>
		<%@include file="menu.jsp"%>

		<div id="content"
			style="background-color: #EEEEEE; height: 650px; width: 700px; float: left;">
			<form name="dane_uzytkownika" action="uzytkownik" method="get"></form>
			<br />
			Dane uzytkownika:
			<table>
				<TR>
					<TD>Login:</TD>
					<TD>${sessionScope.userZalogowany }</TD>
				</TR>
				<TR>
					<TD>E-mail:</TD>
					<TD>${email}</TD>
				</TR>
				<TR>
					<TD>Nr karty kredyt.:</TD>
					<TD>${nrKaty}</TD>
				</TR>
			
			</table>
			<br />
			<br /> Formularz do zmiany danych: <br />
			(wypelnij wszystkie pola) <br />
			<form name="zmianaDanych" action="zmiana_danych" method="post">
				<strong>User Name:</strong> <input type="text" name="username"><br />
				<strong>Password:</strong> <input type="password" name="password"><br />
				<strong>Confirm Password:</strong><input type="password"
					name="conf_password"><br /> <strong>Email: </strong> <input
					type="text" name="email"><br /> <strong>Number
					credit card:</strong> <input type="text" name="creditCard"><br /> <input
					type="submit">
			</form>

		</div>

		<div id="footer"
			style="background-color: #FFA500; clear: both; text-align: center;">Jesli
			potrzeba jest matka wynalazkow, to zapewne zagrozenie jest matka
			kryptoanalizy.</div>

	</div>

</body>
</html>