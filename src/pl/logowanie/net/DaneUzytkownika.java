package pl.logowanie.net;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DaneUzytkownika extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PreparedStatement preparedStatement = null;
	Connection connection;
	RequestDispatcher requestDispatcher;
	String poprawnyEMail = "^(.[A-Za-z0-9\\-]*\\w)+@+([A-Za-z0-9\\-]*\\w)+(\\.[A-Za-z]*\\w)+$";
	String poprawnyNrKartyKredytowej = "[0-9]{16}";

	public DaneUzytkownika() {
		super();
	}

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Jestem w  service method w DaneUzytkownika");

		String uzytkownik = (String) request.getSession().getAttribute(
				"userZalogowany");
		 // TODO
		System.out.println("Uzytkownik " + uzytkownik);
		request.setAttribute("login", uzytkownik);

		try {
			Class.forName("com.mysql.jdbc.Driver");

			// nawiazywanie polaczenia z baza danych , mozna jeszcze
			// dodać haslo jesli potrzeba
			Connection con = DriverManager
					.getConnection("jdbc:mysql://localhost/stronainternetowa?"
							+ "user=root");

			String zapytanie = "SELECT e-mail from stronainternetowa.UZYTKOWNICY where login = \""
					+ uzytkownik + "\" ";
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery(zapytanie);
			String e_mail = null;
			if (!result.next()) {
				e_mail = result.getString("email");
			}
			System.out.println(e_mail);
			request.setAttribute("e_mail", e_mail);

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// PreparedStatement updateUserInfo = (PreparedStatement) conn
		// .prepareStatement("insert into userInfo ( UserName, userPW) values(?,?)");
		// //since first column is AI, no need to include that for updates
		// updateUserInfo.setString(1, nUser);
		// updateUserInfo.setString(2, nPW);
		//
		// updateUserInfo.executeUpdate();
		//
		// lub
		//
		//
		// String sql ="update names set first_name=?, last_name=? where id=?";
		// PrearedStatement ps = oon.prepareStatement(sql);
		// Connnection.prepareStatement();
		// ps.setString(1,"firstName");
		// ps.setString(2,"lastName");
		// ps.setId(3,1);
		// ps.executeUpdate();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String haslo = request.getParameter("password");
		String hasloPtwierdzenie = request.getParameter("conf_password");
		String login = request.getParameter("username");
		String kartaKredytowa = request.getParameter("creditCard");
		String wiadomosc = null;

		connection = (Connection) getServletContext().getAttribute(
				"DBConnection");
		try {
			if (login != null && !login.equals("")) {
				String zapytanie = "SELECT login from stronainternetowa.UZYTKOWNICY where login = \""
						+ login + "\"";
				Statement statement = connection.createStatement();
				ResultSet result = statement.executeQuery(zapytanie);
				if (result.next()) {
					wiadomosc = "Uzytkownik " + login
							+ " - jest juz w bazie, uzyj innej nazwy";
				} else {
					preparedStatement = connection
							.prepareStatement("UPDATE  stronainternetowa.UZYTKOWNICY SET login = ?");
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
							.prepareStatement("UPDATE  stronainternetowa.UZYTKOWNICY SET email = ?");
					preparedStatement.setString(2, email);
					preparedStatement.executeUpdate();

					wiadomosc = wiadomosc + "Twoj nowy adres e-mail: " + email
							+ "\n";
					preparedStatement.close();
				}
			}
			if (haslo != null || !haslo.equals("")) {
				if (hasloPtwierdzenie != null
						|| hasloPtwierdzenie.equals(haslo)) {
					Szyfrowanie zakoduj = new Szyfrowanie();
					preparedStatement = connection
							.prepareStatement("UPDATE  stronainternetowa.UZYTKOWNICY SET haslo = ?");
					preparedStatement.setString(3, zakoduj.hashString(haslo));
					preparedStatement.executeUpdate();

					wiadomosc = wiadomosc + "Haslo zostalo zmienione\n";
					preparedStatement.close();
				} else {
					wiadomosc += "Haslo potwerdzajace nie moze byc null lub puste lub inne niz haslo.";
				}
			}
			if (kartaKredytowa != null || !kartaKredytowa.equals("")) {
				if (!kartaKredytowa.matches(poprawnyNrKartyKredytowej)) {
					wiadomosc += "Nieprawidlowy numer karty kredytowej, ma ona 16 cyfr.";
				} else {
					Szyfrowanie zakoduj = new Szyfrowanie();
					preparedStatement = connection
							.prepareStatement("UPDATE  stronainternetowa.UZYTKOWNICY SET karta_kredytowa = ?");
					preparedStatement.setString(4,
							zakoduj.hashString(kartaKredytowa));
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
