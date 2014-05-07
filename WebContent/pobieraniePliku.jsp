<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PobieraniePliku</title>
</head>
<body>

	<div id="container" style="width: 850px">

		<div id="header" style="background-color: #FFA500;">
			<h1 style="margin-bottom: 0;">Pobieranie pliku</h1>
		</div>
		<%@include file="menu.jsp"%>

		<div id="content" style="background-color: #EEEEEE; height: 650px; width:700px; float: left;">
			<form name="pobierzPlik" action="pobierz" method="post">
				Dziekujemy za pobranie pliku <br /> 
				<br /> 
				Pobrano nalezna oplate z karty kredytowej o nr: <br />${fragmentNrKarty}  <br />
				<br /> 
			</form>

		</div>

		<div id="footer"
			style="background-color: #FFA500; clear: both; text-align: center;">Jesli
			potrzeba jest matka wynalazkow, to zapewne zagrozenie jest matka
			kryptoanalizy.</div>

	</div>
</body>
</html>