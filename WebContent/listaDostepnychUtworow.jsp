<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
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

			${wynikWyszukiwania} <br /> <br />
			<c:forEach var="piosenka" items="${piosenki}">
				<c:out value="${piosenka}" />
				<form name="dane_piosenki" action="piosenka" method="get">

					<br /> Zobacz <input type="submit" name="tytul"
						value="${piosenka}"><br /> <br />

				</form>
				<br />
			</c:forEach>

		</div>

		<div id="footer"
			style="background-color: #FFA500; clear: both; text-align: center;">"Jesli
			potrzeba jest matka wynalazkow, to zapewne zagrozenie jest matka
			kryptoanalizy." Simon Singh -Ksiega szyfrow-</div>

	</div>

</body>
</html>
