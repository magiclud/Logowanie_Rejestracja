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
			<h3 >Dane utworu</h3>
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

			<form name="pobierzPlikMuzyczny" action="pobierzMuzyczke"
				method="post">

				<br /> *Pobierz <input type="submit" name="tytul" value="${tytul}">
				<h6>* Zostanie pobrana oplata z kart kredytowej o nr: ${fragmentNrKarty} </h6>

			</form>
			<br />
			<a href="/Logowanie/listaKomentarzy">Zobacz komentarze/skomentuj</a>
			

		</div>
		<div id="footer"
			style="background-color: #FFA500; clear: both; text-align: center;">Jesli
			potrzeba jest matka wynalazkow, to zapewne zagrozenie jest matka
			kryptoanalizy.</div>

	</div>
</body>
</html>