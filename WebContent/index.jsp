<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<body>

	<div id="container" style="width: 650px">

		<div id="header" style="background-color: #FFA500;">
			<h1 style="margin-bottom: 0;">Zaszyfrowane pliki muzyczne</h1>
		</div>
		<%@include file="menu.jsp"%>

		<div id="content"
			style="background-color: #EEEEEE; height: 400px; width: 500px; float: left;">

			${aga}${wynikRejestracji} <br > ${wynikLogowania}
			<c:if test="${!empty sessionScope.userZalogowany}">

				<br />
				Pobierz i zobacz co jest w zaszyfrowanym pliku muzycznym
				<br />
				<a href="/Logowanie/pobierz">Pobierz Plik</a>
				<br />
				<br />
				Przeslij wlasne pliki 
				<br />
				<a href="przesylaniePliku.jsp">Przeslij wlasne pliki</a>
				<br />
				<br />
				Przejrzyj piosenki
				<br />
				<a href="danePiosenki.jsp">Dane piosenki</a>

			</c:if>

			<c:if test="${empty sessionScope.userZalogowany}">
				
					 ${wynikLogowania}<br />
				<br />
				<br />
				<a href="login.jsp">Logowanie</a>
				<br />
				ilosc prob: ${sessionScope.iloscProb}
				<c:if test="${empty sessionScope.czasOczekiwania}">

					<c:if test="${empty sessionScope.userZarejestrowany}">
				 ${sessionScope.wynikRejestracji} <br />
						<br />
						<a href="rejestracja.jsp">Zarejestruj sie</a>
					</c:if>
				</c:if>
			</c:if>





		</div>

		<div id="footer"
			style="background-color: #FFA500; clear: both; text-align: center;">"Jesli
			potrzeba jest matka wynalazkow, to zapewne zagrozenie jest matka
			kryptoanalizy." Simon Singh -Ksiega szyfrow-</div>

	</div>

</body>
</html>
