<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="net.tanesha.recaptcha.ReCaptcha"%>

<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory"%>

<!DOCTYPE html>
<html>
<body>

	<div id="container" style="width: 850px">

		<div id="header" style="background-color: #FFA500;">
			<h1 style="margin-bottom: 0;">Zaszyfrowane pliki muzyczne</h1>
		</div>
		<%@include file="menu.jsp"%>

		<div id="content"
			style="background-color: #EEEEEE; height: 650px; width: 700px; float: left;">

			<form action="reCapatcheValidate.jsp" method="post">
				<%
        //ReCaptcha c = ReCaptchaFactory.newReCaptcha("your_public_key", "your_private_key", false);
        ReCaptcha captcha = ReCaptchaFactory.newReCaptcha("6Lc0e_QSAAAAAMzvm3zJWWzbkcJ4TwmZvwxJK6fT", "6Lc0e_QSAAAAABtMBUcCRfDHDATJWZ8TD104tzVr", false);
        out.print(captcha.createRecaptchaHtml(null, null));
      %>
				<input type="submit" value="submit" />
			</form>

		</div>

		<div id="footer"
			style="background-color: #FFA500; clear: both; text-align: center;">"Jesli
			potrzeba jest matka wynalazkow, to zapewne zagrozenie jest matka
			kryptoanalizy." Simon Singh -Ksiega szyfrow-</div>

	</div>

</body>
</html>
