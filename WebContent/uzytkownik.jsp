<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<body>

	<div id="container" style="width: 500px">

		<div id="header" style="background-color: #FFA500;">
			<h1 style="margin-bottom: 0;">Uzytkownik</h1>
			
		</div>
		<%@include file="menu.jsp"%>

		<div id="content"
			style="background-color: #EEEEEE; height: 200px; width: 400px; float: left;">
					 ${e_mail} ${nrKarty}  <br />
			<output form="text" name="${sessionScope.userZalogowany }" method="post"></output> 
			<form name="loginform" action="login_sprawdz" method="post">
				Enter User Name: <input type="text" name="username"><br />
				Enter Password: <input name="password" type="password"><br />
				<input type="submit" >
			</form>	

			<table>
				<TR>
					<TD>Login:</TD>
					<TD>${sessionScope.userZalogowany }</TD>
				</TR>
				<TR>
					<TD>E-mail:</TD>
					<TD>D</TD>
				</TR>
				<TR>
					<TD>Nr karty kredytowej:</TD>
					<TD>D</TD>
				</TR>
			</table>
		</div>

		<div id="footer"
			style="background-color: #FFA500; clear: both; text-align: center;">Jesli
			potrzeba jest matka wynalazkow, to zapewne zagrozenie jest matka
			kryptoanalizy.</div>

	</div>

</body>
</html>