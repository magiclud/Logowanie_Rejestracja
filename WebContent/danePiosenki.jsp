<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<body>

	<div id="container" style="width: 650px">

		<div id="header" style="background-color: #FFA500;">
			<h1 style="margin-bottom: 0;">Piosenka</h1>

		</div>
		<%@include file="menu.jsp"%>

		<div id="content"
			style="background-color: #EEEEEE; height: 400px; width: 500px; float: left;">
			<form name="dane_piosenki" action="piosenka" method="get"></form>
			<br />
			Dane utworu:
			<table>
				<TR>
					<TD>Tytul:</TD>
					<TD>${tytul}</TD>
				</TR>
				<TR>
					<TD>Wykonawca:</TD>
					<TD>${wykonawca}</TD>
				</TR>
				<TR>
					<TD>Gartnek:</TD>
					<TD>${gatunek}</TD>
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