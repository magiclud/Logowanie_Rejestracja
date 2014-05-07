 <!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Rejestracja</title>
</head>
<body>

	<div id="container" style="width: 850px">

		<div id="header" style="background-color: #FFA500;">
			<h1 style="margin-bottom: 0;">Rejestracja</h1>
		</div>
		<%@include file="menu.jsp"%>
		
		<div id="content" style="background-color: #EEEEEE; height: 650px; width:700px; float: left;">
			<form name="registerform" action="rejestracja_sprawdz" method="post">
				<br /><strong>User Name:</strong> <input type="text" name="username"><br />
				<br /><strong>Password:</strong> <input type="password" name="password"><br />
				<br /><strong>Confirm Password:</strong><input type="password"
					name="conf_password"><br /> <br /><strong>Email: </strong> <input
					type="text" name="email"><br /><br /> <strong>Number
					credit card:</strong> <input type="text" name="creditCard"><br /><br /> <input
					type="submit">
			</form>

		</div>

		<div id="footer"
			style="background-color: #FFA500; clear: both; text-align: center;">Jesli potrzeba jest matka wynalazkow, to zapewne zagrozenie jest matka kryptoanalizy.</div>

	</div>

</body>
</html>