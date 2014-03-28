<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Rejestracja</title>
</head>
<body>

	<div id="container" style="width: 500px">

		<div id="header" style="background-color: #FFA500;">
			<h1 style="margin-bottom: 0;">Rejestracja</h1>
		</div>
		<%@include file="menu.jsp"%>
		
		<div id="content">
			<form name="registerform" action="rejestracja_sprawdz" method="post">
				<strong>User Name:</strong> <input type="text" name="username"><br />
				<strong>Password:</strong> <input type="password" name="password"><br />
				<strong>Confirm Password:</strong><input type="password"
					name="conf_password"><br /> <strong>Email: </strong> <input
					type="text" name="email"><br /> <strong>Number
					credit card:</strong> <input type="text" name="creditCard"><br /> <input
					type="submit">
			</form>

		</div>

		<div id="footer"
			style="background-color: #FFA500; clear: both; text-align: center;">Jesli potrzeba jest matka wynalazkow, to zapewne zagrozenie jest matka kryptoanalizy.</div>

	</div>

</body>
</html>