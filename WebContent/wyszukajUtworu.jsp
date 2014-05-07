<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<body>

	<div id="container" style="width: 850px">

		<div id="header" style="background-color: #FFA500;">
			<h1 style="margin-bottom: 0;">Znajdz swoj utwor muzyczny</h1>

		</div>
		<%@include file="menu.jsp"%>

		<div id="content"
			style="background-color: #EEEEEE; height: 650px; width: 700px; float: left;">
			<br /> Wyszukaj utworu muzycznego: <br /> (wypelnij przynajmniej
			jedno pole, <br />pisz prosze nazwy wlasne z duzej litery i bez
			polskich znakow) <br /> <br />
			<form name="szukajPiosenki" action="znajdz_utor" method="post">
				<strong>Tytul:</strong> <input type="text" name="tytul"><br />
				<strong>Wykonawca:</strong> <input type="text" name="wykonawca"><br />
				<strong>Gatunek:</strong><input type="text" name="gatunek"><br />
				<br /> <input type="submit">
			</form>

		</div>

		<div id="footer"
			style="background-color: #FFA500; clear: both; text-align: center;">Jesli
			potrzeba jest matka wynalazkow, to zapewne zagrozenie jest matka
			kryptoanalizy.</div>

	</div>

</body>
</html>