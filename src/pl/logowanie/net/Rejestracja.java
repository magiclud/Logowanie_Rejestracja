package pl.logowanie.net;

import java.io.IOException;
import java.io.PrintWriter;
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

	// static Logowanie logger = Logowanie.getLogger( Rejestracja.class);

	PreparedStatement preparedStatement = null;
	Connection con;
	RequestDispatcher rd;

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
		if (haslo == null || haslo.equals("")) {
			msg = "Password can't be null or empty.";
		}
		if (hasloPtwierdzenie == null || !hasloPtwierdzenie.equals(haslo)) {
			msg = "Password can't be null or empty, or not the same.";
		}
		if (login == null || login.equals("")) {
			msg = "Name can't be null or empty.";
		}
		if (kartaKredytowa == null || kartaKredytowa.equals("")) {
			msg = "Country can't be null or empty.";
		}
		if (msg == null) {

			con = (Connection) getServletContext().getAttribute("DBConnection");
			try {
				String zapytanie = "SELECT login from stronainternetowa.UZYTKOWNICY where login= "+login;
				Statement statement = con.createStatement();
				ResultSet result = statement.executeQuery(zapytanie);
				if ((result.getString(1)).equals(login)) {
					msg = "The user " + login
							+ " - is in date base, use another login name";
				}

				// preparedStatements can use variables and are more efficient
				preparedStatement = con
						.prepareStatement("insert into  stronainternetowa.UZYTKOWNICY values (default, ?, ?, ?, ?)");
				// "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
				// parameters start with 1
				preparedStatement.setString(1, login);
				preparedStatement.setString(2, email);
				preparedStatement.setString(3, haslo);
				preparedStatement.setString(4, kartaKredytowa);
				preparedStatement.executeUpdate();

				request.getSession().setAttribute("userZarejestrowany", login);

				msg = "Hello " + login + "! Zostales poprawnie zarejestrowany";

				request.setAttribute(
						"Registration successful, please login below.",
						"registrationm");
				rd = request.getRequestDispatcher("index.jsp");

			} catch (SQLException e) {
				e.printStackTrace();
				// logger.error("Database connection problem");
				throw new ServletException("DB Connection problem.");
			}
		} else {
			request.setAttribute(
					"Registration unsuccessful, please register one more time.",
					"registrationm");
			rd = request.getRequestDispatcher("login.jsp");
		}
		request.setAttribute("wynikRejestracji", msg);

		rd.forward(request, response);

		try {
			preparedStatement.close();

			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
