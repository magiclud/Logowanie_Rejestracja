<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Wylogowanie</title>
</head>
<body>

	<div id="container" style="width: 650px">

		<div id="header" style="background-color: #FFA500;">
			<h1 style="margin-bottom: 0;">Wyslij haslo</h1>
		</div>
		<%@include file="menu.jsp"%>

		<div id="content" style="background-color: #EEEEEE; height: 400px; width:500px; float: left;">
			<form name="wyslijNoweHaslo" action="new_haslo" method="post">
				<br /> <br />Na address email zostanie wyslane nowe haslo.<br />
				Enter e-mail
				address: <input type="text" name="email"><br /> <input
					type="submit">
			</form>
			<br />Po zalgowaniu zalecana jest zmiana hasla. <br /> 
			<br /> <a href="login.jsp">Logowanie</a>

		</div>

		<div id="footer"
			style="background-color: #FFA500; clear: both; text-align: center;">Jesli
			potrzeba jest matka wynalazkow, to zapewne zagrozenie jest matka
			kryptoanalizy.</div>

	</div>

</body>
</html>