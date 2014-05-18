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

public class ZmianaDanych extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PreparedStatement preparedStatement = null;
	Connection connection;
	RequestDispatcher requestDispatcher;
	String poprawnyEMail = "^(.[A-Za-z0-9\\-]*\\w)+@+([A-Za-z0-9\\-]*\\w)+(\\.[A-Za-z]*\\w)+$";
	String poprawnyNrKartyKredytowej = "[0-9]{16}";

	public ZmianaDanych() {
		super();
	}

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String uzytkownik = (String) request.getSession().getAttribute(
				"userZalogowany");
		System.out.println("****  "+ uzytkownik);
		
		String email = request.getParameter("email");
		String haslo = request.getParameter("password");
		String hasloPtwierdzenie = request.getParameter("conf_password");
		String login = request.getParameter("username");
		String kartaKredytowa = request.getParameter("creditCard");
		String wiadomosc = null;

		connection = (Connection) getServletContext().getAttribute(
				"DBConnection");
		try {
			if ( !login.equals("") && !login.equals(uzytkownik)) {
				String zapytanie = "SELECT login from stronainternetowa.UZYTKOWNICY_strony where login = \""
						+ login + "\"";
				Statement statement = connection.createStatement();
				ResultSet result = statement.executeQuery(zapytanie);
				if (!result.next()) {
					wiadomosc = "Uzytkownik " + login
							+ " - jest juz w bazie, uzyj innej nazwy";
				} else {
					preparedStatement = connection
							.prepareStatement("UPDATE  stronainternetowa.UZYTKOWNICY_strony SET login = ?");
					preparedStatement.setString(1, login);
					preparedStatement.executeUpdate();

					request.getSession().setAttribute("userZarejestrowany",
							login);
					wiadomosc = "Twoj nowy login: " + login + "\n";
					preparedStatement.close();
				}
			}
			if (email != null && !email.equals("")) {
				if (!email.matches(poprawnyEMail)) {
					wiadomosc += "Niepoprawny e-mail adres.";
				} else {
					preparedStatement = connection
							.prepareStatement("UPDATE  stronainternetowa.UZYTKOWNICY_strony SET email = ?");
					preparedStatement.setString(2, email);
					preparedStatement.executeUpdate();

					wiadomosc = wiadomosc + "Twoj nowy adres e-mail: " + email
							+ "\n";
					preparedStatement.close();
				}
			}
			if (haslo != null && !haslo.equals("")) {
				if ( hasloPtwierdzenie.equals(haslo)) {
					preparedStatement = connection
							.prepareStatement("UPDATE  stronainternetowa.UZYTKOWNICY_strony SET haslo = ?");
					preparedStatement.setString(3, Szyfrowanie.hashString(haslo));
					preparedStatement.executeUpdate();

					wiadomosc = wiadomosc + "Haslo zostalo zmienione\n";
					preparedStatement.close();
				} else {
					wiadomosc += "Haslo potwerdzajace nie moze byc null lub puste lub inne niz haslo.";
				}
			}
			if (kartaKredytowa != null) {
				if (!kartaKredytowa.matches(poprawnyNrKartyKredytowej)) {
					wiadomosc += "Nieprawidlowy numer karty kredytowej, ma ona 16 cyfr.";
				} else {
					preparedStatement = connection
							.prepareStatement("UPDATE  stronainternetowa.UZYTKOWNICY_strony SET karta_kredytowa = ?");
					preparedStatement.setString(4,
							Szyfrowanie.hashString(kartaKredytowa));
					preparedStatement.executeUpdate();

					wiadomosc = wiadomosc
							+ "Numer karty kredytowej został zmieniony \n";
					preparedStatement.close();
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			// logger.error("Database connection problem");
			throw new ServletException("Problem z polaczeniem z baza danych .");
		}
		request.setAttribute("wynikRejestracji", wiadomosc);
		requestDispatcher = request.getRequestDispatcher("index.jsp");
		requestDispatcher.forward(request, response);

	}

}
