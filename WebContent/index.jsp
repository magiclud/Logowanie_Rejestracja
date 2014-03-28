<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<body>

	<div id="container" style="width: 500px">

		<div id="header" style="background-color: #FFA500;">
			<h1 style="margin-bottom: 0;">Zaszyfrowany plik muzyczny</h1>
		</div>
		<%@include file="menu.jsp"%>

		<div id="content"
			style="background-color: #EEEEEE; height: 200px; width: 400px; float: left;">
			${wynikLogowania} ${aga} ${wynikRejestracji} <br />

			<c:if test="${!empty sessionScope.userZalogowany}">
				<a href="link_do_servleta_ktory_zwroci_plik">Link do pliku</a>
				<br>
				http://www.mkyong.com/java/how-to-download-file-from-website-java-jsp/
			</c:if>
			
			<c:if test="${empty sessionScope.userZalogowany}">
				${wynikLogowania}<br /> <br /> 
				<a href="login.jsp">Logowanie</a>
				<br /> 
				ilosc prob: ${sessionScope.iloscProb}
			</c:if>

			<c:if test="${empty sessionScope.userZarejestrowany}">
				 ${sessionScope.wynikRejestracji} <br /><br /><br />
				<a href="rejestracja.jsp">Zarejestruj sie</a>
			</c:if>

		</div>

		<div id="footer"
			style="background-color: #FFA500; clear: both; text-align: center;">Jesli potrzeba jest matka wynalazkow, to zapewne zagrozenie jest matka kryptoanalizy.</div>

	</div>

</body>
</html>
