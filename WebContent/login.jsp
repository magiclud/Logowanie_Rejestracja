<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="net.tanesha.recaptcha.ReCaptcha"%>

<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<body>

	<div id="container" style="width: 850px">

		<div id="header" style="background-color: #FFA500;">
			<h1 style="margin-bottom: 0;">Logowanie</h1>
		</div>
		<%@include file="menu.jsp"%>

		<div id="content"
			style="background-color: #EEEEEE; height: 650px; width: 700px; float: left;">
			<c:if test="${empty sessionScope.bledneLogowanie}">
				<form name="loginform" action="login_sprawdz" method="post">
					<br />Enter User Name: <input type="text" name="username"><br />
					<br />Enter Password: <input name="password" type="password"><br />
					<br /> <input type="submit"> <br />
				</form>
			</c:if>

			<c:if test="${!empty sessionScope.bledneLogowanie}">
				<form name="loginform" action="login_sprawdz" method="post">
					<br />Enter User Name: <input type="text" name="username"><br />
					<br />Enter Password: <input name="password" type="password"><br />
					<br /> <input type="submit"> 
					captcha:
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
			</c:if>
			<br /> <br /> Zapomniales hasla? <br /> <a
				href="/Logowanie/zapomnianeHaslo.jsp">kliknij </a>a otrzymasz nowe
			haslo na poczcie

		</div>

		<div id="footer"
			style="background-color: #FFA500; clear: both; text-align: center;">Jesli
			potrzeba jest matka wynalazkow, to zapewne zagrozenie jest matka
			kryptoanalizy.</div>

	</div>

</body>
</html>