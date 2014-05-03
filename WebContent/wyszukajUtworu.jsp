<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<body>

	<div id="container" style="width: 650px">

		<div id="header" style="background-color: #FFA500;">
			<h1 style="margin-bottom: 0;">Znajdz swoj utwor muzyczny</h1>

		</div>
		<%@include file="menu.jsp"%>

		<div id="content"
			style="background-color: #EEEEEE; height: 400px; width: 500px; float: left;">
			<form name="szukaj_utworu" action="szukajMuzyki" method="get"></form>
			<br /> <br /> Wyszukaj utworu muzycznego: <br /> (wypelnij
			przynajmniej jedno pole) <br />
			<form name="szukajPiosenki" action="znajdz_utor" method="post">
				<strong>Tytul:</strong> <input type="text" name="tytul"><br />
				<strong>Wykonawca:</strong> <input type="text" name="wykonawca"><br />
				<strong>Gatunek:</strong><input type="text" name="gatunek"><br />
				<input type="submit">
			</form>

		</div>

		<div id="footer"
			style="background-color: #FFA500; clear: both; text-align: center;">Jesli
			potrzeba jest matka wynalazkow, to zapewne zagrozenie jest matka
			kryptoanalizy.</div>

	</div>

</body>
</html>