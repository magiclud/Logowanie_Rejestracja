package pl.logowanie.net;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

/**
 * Servlet implementation class Login
 */
public class Logowanie extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// snippet
	// private final static Logger LOGGER = Logger.getLogger(Logowanie.class
	// .getName());

	public Logowanie() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
	}

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("We are in service method of selvlet");

		String un = request.getParameter("username");
		String pw = request.getParameter("password");

		String msg = null;

		if (un == null || un.equals("") || pw == null || pw.equals("")) {
			msg = "Wrong user name or password";
		}
		if (msg == null) {
			Connection con = (Connection) getServletContext().getAttribute(
					"DBConnection");

			String zapytanie = "SELECT login from stronainternetowa.UZYTKOWNICY where login= login and haslo = password";
			Statement statement;
			try {
				statement = con.createStatement();

				ResultSet result = statement.executeQuery(zapytanie);
				if (result.next() && (result.getString(1)).equals(un)
						&& (result.getString(3)).equals(pw)) {
//					String iloscProbk = (String) request.getSession()
//							.getAttribute("iloscProb");
					int ilosc = Integer.valueOf((String) request.getSession()
							.getAttribute("iloscProb"));
					//if (iloscProbk != null && !iloscProbk.isEmpty()) {
						if (ilosc != 0) {
						System.out.println("udalo sie wejsc do ifa");
						
						ilosc++;
						request.getSession().setAttribute("iloscProb", ilosc);
					} else {
						request.getSession().setAttribute("iloscProb", 1);
					}
					msg = "Czesc " + un + "! Twoje logowanie jest niepoprawne";
				} else {
					request.getSession().setAttribute("userZalogowany", un);
					msg = "Czesc " + un + "! Zostales poprawnie zalogowany";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			request.setAttribute("aga", "spróbuj jeszcze raz");
		}

		request.setAttribute("wynikLogowania", msg);

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);
	}

}
