package pl.logowanie.net;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
		if (msg != null) {
			request.setAttribute(
					"Registration unsuccessful, please register one more time.",
					"registrationm");
			rd = request.getRequestDispatcher("login.jsp");
		} else {
			request.getSession().setAttribute("userZarejestrowany", login);

			msg = "Hello " + login + "! Zostales poprawnie zarejestrowany";
			// RequestDispatcher rd = getServletContext().getRequestDispatcher(
			// "/rejestracja.jsp");
			// PrintWriter out = response.getWriter();
			// out.println("<font color=red>" + msg + "</font>");
			// rd.include(request, response);

			con = (Connection) getServletContext().getAttribute("DBConnection");

			try {
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

				request.setAttribute(
						"Registration successful, please login below.",
						"registrationm");
				rd = request.getRequestDispatcher("index.jsp");
				

			} catch (SQLException e) {
				e.printStackTrace();
				// logger.error("Database connection problem");
				throw new ServletException("DB Connection problem.");
			}
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
