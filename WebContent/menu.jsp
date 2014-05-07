<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="menu"
	style="background-color: #FFD700; height: 650px; width: 150px; float: left;">
	<c:if test="${empty sessionScope.userZalogowany}">
		<br />
		<br />
		<a href="/Logowanie/login.jsp">Logowanie</a>
		<br />
		<c:if test="${empty sessionScope.userZarejestrowany}">
				 ${sessionScope.wynikRejestracji} <br />
			<br />
			<a href="rejestracja.jsp">Zarejestruj sie</a>
		</c:if>
	</c:if>


	<br />
	<c:if test="${!empty sessionScope.userZalogowany}">
		<a href="/Logowanie/wyloguj">Wylogowanie</a>
		<br />
		<br />
		<a href="/Logowanie/uzytkownik">Dane uzytkownika</a>
		<br />
		<br />
		<a href="wyszukajUtworu.jsp">Szukaj utoru</a>
	</c:if>
</div>