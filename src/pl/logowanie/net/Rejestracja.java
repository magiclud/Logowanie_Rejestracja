package pl.logowanie.net;

import java.io.IOException;
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

	private static final long serialVersionUID = 1L;
	PreparedStatement preparedStatement = null;
	Connection connection;
	RequestDispatcher requestDispatcher;
	String poprawnyEMail = "^(.[A-Za-z0-9\\-]*\\w)+@+([A-Za-z0-9\\-]*\\w)+(\\.[A-Za-z]*\\w)+$";
	String poprawnyNrKartyKredytowej = "[0-9]+{16}";

	protected  synchronized void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String haslo = request.getParameter("password");
		String hasloPtwierdzenie = request.getParameter("conf_password");
		String login = request.getParameter("username");
		String kartaKredytowa = request.getParameter("creditCard");
		String wiadomosc = null;
		if (email == null || email.equals("") || !email.matches(poprawnyEMail)) {
			wiadomosc = "Niepoprawny e-mail adres.";
		}
		if (haslo == null || haslo.equals("")) {
			wiadomosc = "Halo nie moze być null lub puste.";
		}
		if (hasloPtwierdzenie == null || !hasloPtwierdzenie.equals(haslo)) {
			wiadomosc = "Haslo potwerdzajace nie moze byc null lub puste lub inne niz haslo.";
		}
		if (login == null || login.equals("")) {
			wiadomosc = "Login nie moze byc null lub pusty.";
		}
		if (kartaKredytowa == null || kartaKredytowa.equals("") || !kartaKredytowa.matches(poprawnyNrKartyKredytowej)) {
			wiadomosc = "Nieprawidlowy numer karty kredytowej, ma ona 16 cyfr.";
		}
		if (wiadomosc == null) {

			connection = (Connection) getServletContext().getAttribute(
					"DBConnection");
			try {
				String zapytanie = "SELECT login from stronainternetowa.UZYTKOWNICY_strony where login = \""
						+ login + "\"";
				Statement statement = connection.createStatement();
				ResultSet result = statement.executeQuery(zapytanie);
				if (result.next()) {
					wiadomosc = "Uzytkownik " + login
							+ " - jest juz w bazie, uzyj innej nazwy";

				} else {
					// hashString(haslo);

					preparedStatement = connection
							.prepareStatement("insert into  stronainternetowa.UZYTKOWNICY_strony values (default, ?, ?, ?, ?, ?,  default)");
					String aliasHasla = login;
					String sciezkaDoKeyStore = "D:\\Programy\\eclipseEE\\wokspace\\Logowanie\\keystorePasswords.ks";
					byte[] zaszyfrowanyNumer = Szyfrowanie
							.zaszyfrowanieWiadomosci(Szyfrowanie.dodajKlucz(
									sciezkaDoKeyStore, aliasHasla),
									kartaKredytowa);
					preparedStatement.setString(1, login);
					System.out.println("emmial  " + email);
					preparedStatement.setString(2, email);
					preparedStatement.setString(3, Szyfrowanie.hashString(haslo));// haslo
					preparedStatement.setBytes(4, zaszyfrowanyNumer);
					String grupa = "zwykla";
					preparedStatement.setString(5, grupa);
					// preparedStatement.setString(6, "");
					System.out.println(preparedStatement);
					preparedStatement.executeUpdate();

					request.getSession().setAttribute("userZarejestrowany",
							login);

					wiadomosc = "Witaj " + login
							+ "! Zostales poprawnie zarejestrowany";

					request.setAttribute(
							"Rejestracja zakonczona powodzeniem, mozesz sie teraz zalogowac.",
							"registrationm");
					// rd = request.getRequestDispatcher("login.jsp");

					preparedStatement.close();

				}

			} catch (SQLException e) {
				e.printStackTrace();
				// logger.error("Database connection problem");
				throw new ServletException(
						"Problem z polaczeniem z baza danych .");
			}
		} else {
			request.setAttribute(
					"Rejestracja zakonczona niepowodzeniem, sprobuj jeszcze raz.",
					"registrationm");
		}
		request.setAttribute("wynikRejestracji", wiadomosc);
		requestDispatcher = request.getRequestDispatcher("index.jsp");
		requestDispatcher.forward(request, response);

	}

}
