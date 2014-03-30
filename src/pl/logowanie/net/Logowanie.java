package pl.logowanie.net;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Servlet implementation class Login
 */
public class Logowanie extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private int dozwolonaIloscProbLogowan = 3;
	private int czasOczekiwaniaPoNieporrawnymLogowaniu = 3000; // w milisekundach

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
			try {
				Class.forName("com.mysql.jdbc.Driver");

				// nawiazywanie polaczenia z baza danych , mozna jeszcze dodać
				// haslo jesli potrzeba
				Connection con = DriverManager
						.getConnection("jdbc:mysql://localhost/stronainternetowa?"
								+ "user=root");

				String zapytanie = "SELECT login from stronainternetowa.UZYTKOWNICY where login = \""
						+ uzytkownik
						+ "\" and haslo = \""
						+ hashString(hasloUzytkownika)
						+ "\"";
				Statement statement = con.createStatement();
				ResultSet result = statement.executeQuery(zapytanie);

				Long czasT = (Long) request.getSession().getAttribute(
						"czasOczekiwania");
				long czasSesji = request.getSession().getCreationTime();
				if (czasT==null || !(czasT >= czasSesji)) {
					if (!result.next()) {
						Integer iloscProbk = (Integer) request.getSession()
								.getAttribute("iloscProb");

						if (iloscProbk != null) {

							int ilosc = iloscProbk;
							 if(ilosc==dozwolonaIloscProbLogowan-1){
							// TODO czekaj T minut
							
							long time = czasSesji + 3000;
							request.getSession().setAttribute(
									"czasOczekiwania", time);
							request.getSession().setAttribute("iloscProb",
									0);
							 }
							ilosc++;
							request.getSession().setAttribute("iloscProb",
									ilosc);
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
				} else {
					long czekaj = czasT - czasSesji;
					msg = "Nieporpawne logowanie, musisz poczekac " + czekaj
							+ " milisekund";
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
