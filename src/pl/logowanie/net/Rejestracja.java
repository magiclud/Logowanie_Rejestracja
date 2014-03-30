package pl.logowanie.net;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Rejestracja extends HttpServlet {

	PreparedStatement preparedStatement = null;
	Connection con;
	RequestDispatcher rd;
	String poprawnyEMail = "^(.[A-Za-z0-9\\-]*\\w)+@+([A-Za-z0-9\\-]*\\w)+(\\.[A-Za-z]*\\w)+$";
	String poprawnyNrKartyKredytowej = "[0-9]{16}";

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String haslo = request.getParameter("password");
		String hasloPtwierdzenie = request.getParameter("conf_password");
		String login = request.getParameter("username");
		String kartaKredytowa = request.getParameter("creditCard");
		String msg = null;
		if (email == null || email.equals("")) {
			msg = "Email ID can't be null or empty.";
		}
		if (!email.matches(poprawnyEMail)) {
			msg = "Wrong e-mail adress.";
		}
		if (haslo == null || haslo.equals("")) {
			msg = "Password can't be null or empty.";
		}
		if (hasloPtwierdzenie == null || !hasloPtwierdzenie.equals(haslo)) {
			msg = "Password can't be null or empty and have to be the same with confirm password.";
		}
		if (login == null || login.equals("")) {
			msg = "Name can't be null or empty.";
		}
		if (kartaKredytowa == null || kartaKredytowa.equals("")) {
			msg = "Country can't be null or empty.";
		}
		if (!kartaKredytowa.matches(poprawnyNrKartyKredytowej)) {
			msg = "Wrong numer credit card.";
		}
		if (msg == null) {

			con = (Connection) getServletContext().getAttribute("DBConnection");
			try {
				String zapytanie = "SELECT login from stronainternetowa.UZYTKOWNICY where login = \""
						+ login + "\"";
				Statement statement = con.createStatement();
				ResultSet result = statement.executeQuery(zapytanie);
				if (result.next()) {
					msg = "The user " + login
							+ " - is in date base, use another login name";

				} else {
					hashString(haslo);
					preparedStatement = con
							.prepareStatement("insert into  stronainternetowa.UZYTKOWNICY values (default, ?, ?, ?, ?)");
					// parameters start with 1
					preparedStatement.setString(1, login);
					preparedStatement.setString(2, email);
					preparedStatement.setString(3, hashString(haslo));
					preparedStatement.setString(4, kartaKredytowa);
					preparedStatement.executeUpdate();

					request.getSession().setAttribute("userZarejestrowany",
							login);

					msg = "Witaj " + login
							+ "! Zostales poprawnie zarejestrowany";

					request.setAttribute(
							"Registration successful, please login below.",
							"registrationm");
					// rd = request.getRequestDispatcher("login.jsp");

					preparedStatement.close();

				}

			} catch (SQLException e) {
				e.printStackTrace();
				// logger.error("Database connection problem");
				throw new ServletException("DB Connection problem.");
			}
		} else {
			request.setAttribute(
					"Registration unsuccessful, please register one more time.",
					"registrationm");
		}
		request.setAttribute("wynikRejestracji", msg);
		rd = request.getRequestDispatcher("index.jsp");
		rd.forward(request, response);

	}

	private String hashString(String haslo) {

		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");

			byte[] hash = md.digest(haslo.getBytes("UTF-8"));
			// converting byte array to Hexadecimal String
			StringBuilder sb = new StringBuilder(2 * hash.length);
			for (byte b : hash) {
				sb.append(String.format("%02x", b & 0xff));
			}
			String hashString = sb.toString();
			return hashString;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
}
