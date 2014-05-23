package pl.logowanie.net;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 * Servlet implementation class Login
 */
public class Logowanie extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private int dozwolonaIloscProbLogowan = 3;
	private int czasCzekaniaGdyNieprawneLog = 30000; // w
														// milisekundach

	public Logowanie() {
		super();
	}

	protected synchronized void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Jestem w  service method of selvlet");

		// InputStream in = request.getInputStream();
		// BufferedReader r = new BufferedReader(new InputStreamReader(in));
		// StringBuffer buf = new StringBuffer();
		// String line;
		// while ((line = r.readLine())!=null) {
		// buf.append(line);
		// }
		// String s = buf.toString();
		// System.out.println("!!!!!!     "+s+ "   !!!!!!!!!");
		//
		String uzytkownik = request.getParameter("username");
		String hasloUzytkownika = request.getParameter("password");

		String message = null;
		Statement statement;
		Connection con;
		String zapytanie;
		ResultSet result;

		if (uzytkownik == null || uzytkownik.equals("")
				|| hasloUzytkownika == null || hasloUzytkownika.equals("")) {
			message = "Zla nazwa uzytkownika lub haslo";
		}
		if (message == null) {
			Long czasT = (Long) request.getSession().getAttribute(
					"czasOczekiwania");
			long dataLogowania = new Date().getTime();
			if (czasT == null || !(czasT >= dataLogowania)) {

				try {
					Class.forName("com.mysql.jdbc.Driver");
					// nawiazywanie polaczenia z baza danych , mozna jeszcze
					// dodać haslo jesli potrzeba
					con = DriverManager
							.getConnection("jdbc:mysql://localhost/stronainternetowa?"
									+ "user=root");

					zapytanie = "SELECT login from stronainternetowa.UZYTKOWNICY_strony where login = \""
							+ uzytkownik
							+ "\" and haslo = \""
							+ Szyfrowanie.hashString(hasloUzytkownika) + "\"";
					statement = con.createStatement();
					result = statement.executeQuery(zapytanie);

					if (!result.next()) {
						Integer iloscProbk = (Integer) request.getSession()
								.getAttribute("iloscProb");

						if (iloscProbk != null) {

							int ilosc = iloscProbk;
							if (ilosc >= dozwolonaIloscProbLogowan - 1) {

								long time = dataLogowania
										+ czasCzekaniaGdyNieprawneLog;
								request.getSession().setAttribute(
										"czasOczekiwania", time);
								request.getSession().setAttribute("iloscProb",
										1);
							}
							ilosc++;
							request.getSession().setAttribute("iloscProb",
									ilosc);
						} else {
							request.getSession().setAttribute("iloscProb", 1);
						}
						message = "Czesc " + uzytkownik
								+ "! Twoje logowanie jest niepoprawne";
					} else {
						request.getSession().getServletContext()
								.setAttribute("user", uzytkownik);
						request.getSession().setAttribute("userZalogowany",
								uzytkownik);
						message = "Czesc " + uzytkownik
								+ "! Zostales poprawnie zalogowany";

						int liczbalogowan = 0;
						zapytanie = "SELECT LiczbaLogowan from stronainternetowa.UZYTKOWNICY_strony where login = \""
								+ uzytkownik + "\" ";
						statement = con.createStatement();
						result = statement.executeQuery(zapytanie);

						if (result.next()) {
							liczbalogowan = result.getInt("LiczbaLogowan");
						}
						zapytanie = "UPDATE  stronainternetowa.UZYTKOWNICY_strony SET LiczbaLogowan = ? where login = \""
								+ uzytkownik + "\" ";
						PreparedStatement preparedStatement = con
								.prepareStatement(zapytanie);
						preparedStatement.setInt(1, liczbalogowan + 1);
						preparedStatement.executeUpdate();

						preparedStatement.close();
					}

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();

				}
			} else {
				long czekaj = czasT - dataLogowania;
				message = "Nieporpawne logowanie, musisz poczekac " + czekaj
						+ " milisekund";

			}
		} else {
			request.setAttribute("aga", "spróbuj jeszcze raz");
		}

		request.setAttribute("wynikLogowania", message);

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);
	}

}
