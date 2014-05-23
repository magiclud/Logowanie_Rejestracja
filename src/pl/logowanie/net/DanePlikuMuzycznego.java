package pl.logowanie.net;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DanePlikuMuzycznego extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DanePlikuMuzycznego() {
		super();
	}

	protected synchronized void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Jestem w  service method w DanePlikuMuzycznego");

		String tytul = (String) request.getParameter("tytul");
		String uzytkownik = (String) request.getSession().getAttribute(
				"userZalogowany");

		System.out.println("Uzytkownik " + uzytkownik);
		System.out.println("Tytul " + tytul);
		request.getSession().setAttribute("tytul", tytul);

		ArrayList<String> poprzednieKomentarze = new ArrayList<String>();

		if (tytul != null && uzytkownik != null) {
			try {
				Class.forName("com.mysql.jdbc.Driver");

				// nawiazywanie polaczenia z baza danych , mozna jeszcze
				// dodać haslo jesli potrzeba
				Connection con = DriverManager
						.getConnection("jdbc:mysql://localhost/stronainternetowa?"
								+ "user=root");

				String zapytanie = "SELECT WYKONAWCA, gatunek from stronainternetowa.pliki_muzyczne where tytul = \""
						+ tytul + "\" ";
				Statement statement = con.createStatement();
				ResultSet result = statement.executeQuery(zapytanie);
				String wykonawca = null;
				String gatunek = null;
				if (result.next()) {
					wykonawca = result.getString("wykonawca");
					gatunek = result.getString("gatunek");
				}

				request.getSession().setAttribute("wykonawca", wykonawca);
				request.getSession().setAttribute("gatunek", gatunek);

				zapytanie = "SELECT  KARTA_KREDYTOWA from stronainternetowa.UZYTKOWNICY_strony where login = \""
						+ uzytkownik + "\" ";
				statement = con.createStatement();
				result = statement.executeQuery(zapytanie);
				byte[] nrKarty = null;
				if (result.next()) {
					nrKarty = result.getBytes("karta_kredytowa");
				}
				String aliasHasla = uzytkownik;
				String sciezkaDoKeyStore = "D:\\Programy\\eclipseEE\\wokspace\\Logowanie\\keystorePasswords.ks";
				byte[] odszyfrowanyNumer = Szyfrowanie
						.dekodujWiadomosc(nrKarty, Szyfrowanie.pobierzKlucz(
								sciezkaDoKeyStore, aliasHasla));
				String numerKartyKredytowej = new String(odszyfrowanyNumer);
				System.out.println("Nr karty kredyt. " + numerKartyKredytowej);

				char[] nrKartyKred = numerKartyKredytowej.toCharArray();
				String message = "";
				for (int i = 0; i < nrKartyKred.length; i++) {
					if (i >= 11) {
						message += nrKartyKred[i];
					} else {
						message += "*";
					}
				}
				System.out.println("Nr karty kredyt. z * " + message);
				request.setAttribute("fragmentNrKarty", message);

				zapytanie = "SELECT uzytkownik, komentarz from stronainternetowa.KOMENTARZE where tytul = \""
						+ tytul + "\"";
				statement = con.createStatement();
				result = statement.executeQuery(zapytanie);
				while (result.next()) {
					String login = result.getString("uzytkownik") + ": ";
					String wczesniejszyKomentarz = "      ~"
							+ result.getString("komentarz");
					System.out.println("Uzytk. " + login + " komen.: "
							+ wczesniejszyKomentarz);
					poprzednieKomentarze.add(login);
					poprzednieKomentarze.add(wczesniejszyKomentarz);
				}
				if (poprzednieKomentarze.size() != 0) {
					for (int i = 0; i < poprzednieKomentarze.size(); i++) {
						request.getSession().setAttribute("komentarzeWbazie",
								poprzednieKomentarze);
					}
				}

				RequestDispatcher dispatcher = request
						.getRequestDispatcher("danePiosenki.jsp");
				dispatcher.forward(request, response);

			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
