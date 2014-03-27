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

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String haslo = request.getParameter("password");
		String hasloPtwierdzenie = request.getParameter("conf_password");
		String login = request.getParameter("name");
		String kartaKredytowa = request.getParameter("creditCard");
		int nrTel = Integer.parseInt(login);
		String errorMsg = null;
		if (email == null || email.equals("")) {
			errorMsg = "Email ID can't be null or empty.";
		}
		if (haslo == null || haslo.equals("")) {
			errorMsg = "Password can't be null or empty.";
		}
		if (hasloPtwierdzenie == null || !hasloPtwierdzenie.equals(haslo)) {
			errorMsg = "Password can't be null or empty, or not the same.";
		}
		if (login == null || login.equals("")) {
			errorMsg = "Name can't be null or empty.";
		}
		if (kartaKredytowa == null || kartaKredytowa.equals("") || nrTel == 0) {
			errorMsg = "Country can't be null or empty.";
		}

		if (errorMsg != null) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/register.html");
			PrintWriter out = response.getWriter();
			out.println("<font color=red>" + errorMsg + "</font>");
			rd.include(request, response);
		} else {

			Connection con = (Connection) getServletContext().getAttribute(
					"DBConnection");
			PreparedStatement ps = null;
			try {
				ps = con.prepareStatement("insert into Users(name,email,creditCard, password) values (?,?,?,?)");
				ps.setString(1, login);
				ps.setString(2, email);
				ps.setString(3, kartaKredytowa);
				ps.setString(4, haslo);

				ps.execute();

				// logger.info("User registered with email="+email);

				// forward to login page to login
				RequestDispatcher rd = getServletContext()
						.getRequestDispatcher("/login.html");
				PrintWriter out = response.getWriter();
				out.println("<font color=green>Registration successful, please login below.</font>");
				rd.include(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
				// logger.error("Database connection problem");
				throw new ServletException("DB Connection problem.");
			} finally {
				try {
					ps.close();
				} catch (SQLException e) {
					// logger.error("SQLException in closing PreparedStatement");
				}
			}
		}

	}

}
