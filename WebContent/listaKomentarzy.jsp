<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<body>

	<div id="container" style="width: 650px">

		<div id="header" style="background-color: #FFA500;">
			<h1 style="margin-bottom: 0;">${tytul}</h1>

		</div>
		<%@include file="menu.jsp"%>

		<div id="content"
			style="background-color: #EEEEEE; height: 400px; width: 500px; float: left;">
			<div id="comments">
				<form id="comments-form" name="comments-form"
					action="wyslijKomentarz">
					<textarea id="comments-form-comment" name="comment" rows="4"
						tabindex="5"
						style="width: 80%; margin-left: 20px; margin-top: 10px;"></textarea>
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