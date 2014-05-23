<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<body>

	<div id="container" style="width: 850px">

		<div id="header" style="background-color: #FFA500;">
			<h1 style="margin-bottom: 0;">Piosenka</h1>

		</div>
		<%@include file="menu.jsp"%>

		<div id="content"
			style="background-color: #EEEEEE; height: 650px; width: 700px; float: left;">
			<form name="dane_piosenki" action="piosenka" method="get"></form>
			<h3>Dane utworu</h3>
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
				${wynikPobierania}
				<br /> *Pobierz: <input type="submit" name="tytul" value="${tytul}">
				<br /> * Zostanie pobrana oplata z kart kredytowej o nr:
				${fragmentNrKarty}

			</form>
			<br /> Komentarze: <br />
			<c:forEach var="kom" items="${komentarzeWbazie}">

				<c:out value="${kom}" />
					<br />
			</c:forEach>
			<br />
			<br />
			<a href="wyslijKomentarz">Skomentuj</a>


		</div>
		<div id="footer"
			style="background-color: #FFA500; clear: both; text-align: center;">Jesli
			potrzeba jest matka wynalazkow, to zapewne zagrozenie jest matka
			kryptoanalizy.</div>

	</div>
</body>
</html>