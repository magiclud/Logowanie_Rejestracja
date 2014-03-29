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
import java.sql.DriverManager;
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

		String uzytkownik = request.getParameter("username");
		String hasloUzytkownika = request.getParameter("password");

		String msg = null;

		if (uzytkownik == null || uzytkownik.equals("")
				|| hasloUzytkownika == null || hasloUzytkownika.equals("")) {
			msg = "Wrong user name or password";
		}
		if (msg == null) {
			Connection con;
			try {
				Class.forName("com.mysql.jdbc.Driver");

				// nawiazywanie polaczenia z baza danych , mozna jeszcze dodać
				// haslo jesli potrzeba
				con = DriverManager
						.getConnection("jdbc:mysql://localhost/stronainternetowa?"
								+ "user=root");

				String zapytanie = "SELECT login from stronainternetowa.UZYTKOWNICY where login = \""
						+ uzytkownik
						+ "\" and haslo = \""
						+ hasloUzytkownika
						+ "\"";
				Statement statement;

				statement = con.createStatement();

				ResultSet result = statement.executeQuery(zapytanie);
				if (!result.next()) {
					// String iloscProbk = (String) request.getSession()
					// .getAttribute("iloscProb");
					Integer iloscProbk = (Integer) request.getSession()
							.getAttribute("iloscProb");
					// if (iloscProbk != null && !iloscProbk.isEmpty()) {
					if (iloscProbk != null) {
						System.out.println("udalo sie wejsc do ifa");
						int ilosc = iloscProbk;
						ilosc++;
						request.getSession().setAttribute("iloscProb", ilosc);
					} else {
						request.getSession().setAttribute("iloscProb", 1);
					}
					msg = "Czesc " + uzytkownik
							+ "! Twoje logowanie jest niepoprawne";
				} else {
					request.getSession().setAttribute("userZalogowany",
							uzytkownik);
					msg = "Czesc " + uzytkownik
							+ "! Zostales poprawnie zalogowany";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
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
