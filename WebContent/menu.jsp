<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="menu"
			style="background-color: #FFD700; height: 200px; width: 100px; float: left;">
			<c:if test="${empty sessionScope.userZalogowany}">
				<a href="/Logowanie/login.jsp">Logowanie</a> <br>
			</c:if>
			<c:if test="${!empty sessionScope.userZalogowany}">
				<a href="/Logowanie/wylogowanie.jsp">Wylogowanie</a> <br>
			</c:if>
			 
			
			HTML<br> 
			CSS<br> J
			avaScript
		</div>