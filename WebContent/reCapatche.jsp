<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="net.tanesha.recaptcha.ReCaptcha"%>

<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


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
			<br /> ${wynikLogowania} <br />
		//	<form action="reCapatcheValidate.jsp" method="post">
		
				<br />Enter User Name: <input type="text" name="username"><br />
				<br />Enter Password: <input name="password" type="password"><br />
				<br /> <input type="submit"> captcha:
				<%
						ReCaptcha captcha = ReCaptchaFactory.newReCaptcha(
									"6Lfge_QSAAAAAFhyWqrSC3aafCFAFLRM9ZL1-Y0K",
									"6Lfge_QSAAAAAM2UICmv7mb_8eNd7V4yDwetUSgC", false);
							//					 Object err = request.getParameter("error");
							String captchaScript = captcha.createRecaptchaHtml(null, null);
							captchaScript = captchaScript.replace("http", "https");
							out.print(captchaScript);
							out.print(captchaScript);
							out.print("test");
					%>
			</form>

		</div>

		<div id="footer"
			style="background-color: #FFA500; clear: both; text-align: center;">"Jesli
			potrzeba jest matka wynalazkow, to zapewne zagrozenie jest matka
			kryptoanalizy." Simon Singh -Ksiega szyfrow-</div>

	</div>

</body>
</html>