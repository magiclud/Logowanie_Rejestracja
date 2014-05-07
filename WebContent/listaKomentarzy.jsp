<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<body>

	<div id="container" style="width: 850px">

		<div id="header" style="background-color: #FFA500;">
			<h1 style="margin-bottom: 0;">${tytul}</h1>

		</div>
		<%@include file="menu.jsp"%>
		<div id="content"
			style="background-color: #EEEEEE; height: 650px; width: 700px; float: left;">
			<form name="dane_komentarzy" action="wyslijKomentarz" method="get">
				<div id="oldComments">
					<h4>Poprzedni komentarz:</h4>
					<c:forEach var="oldKom" items="${komentarzeWbazie}">
						<c:out value="${oldKom}" />
						<br />
					</c:forEach>
				</div>
			</form>
			<div id="comments">
				<h4>Skomentuj</h4>
				<form name="dane_piosenki" action="komentarzUzytkownika"
					method="get">
					<textarea id="comments-form-comment" name="komentarz" rows="4"
						tabindex="5"
						style="width: 80%; margin-left: 20px; margin-top: 10px;"></textarea>
					<br /> <input type="submit" name="tytul" value="Wyslij"><br />
				</form>
			</div>
		</div>
		<div id="footer"
			style="background-color: #FFA500; clear: both; text-align: center;">Jesli
			potrzeba jest matka wynalazkow, to zapewne zagrozenie jest matka
			kryptoanalizy.</div>

	</div>
</body>
</html>