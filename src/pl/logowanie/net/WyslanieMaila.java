package pl.logowanie.net;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class WyslanieMaila
 */
public class WyslanieMaila extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public WyslanieMaila() {
		super();
	}

	RequestDispatcher requestDispatcher;
	// Adres email osby która wysyła maila
	private static String FROM = "agnieszkalud@op.pl";
	// Hasło do konta osoby która wysyła maila
	private static String PASSWORD = "senga1";
	// Adres email osoby do której wysyłany jest mail
	private static String adresat;
	// Temat wiadomości
	private static String temat = "Nowe haslo do serwisu Zaszyfrowana muzyka";
	// Treść wiadomości
	private static String wiadomosc;

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Jestem w  doPost method w WyslanieMaila");

		String email = request.getParameter("email");
		String uzytkownik = request.getParameter("username");

		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager
					.getConnection("jdbc:mysql://localhost/stronainternetowa?"
							+ "user=root");

		/*	String zapytanie = "SELECT email from stronainternetowa.UZYTKOWNICY_strony where login = \""
					+ uzytkownik + "\" and email = \""+email+"\" ";
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery(zapytanie);*/
			String zapytanie = "SELECT email from stronainternetowa.UZYTKOWNICY_strony where login = ? and email = ? ";
			PreparedStatement  statement = con.prepareStatement(zapytanie);
			statement.setString(1, uzytkownik);
			statement.setString(2, email);
			ResultSet result = statement.executeQuery();

			if (result.next()) {
				adresat = email;
				
				System.out.println("email: " + email);

				Properties props = new Properties();
				props.put("mail.smtp.host", "smtp.poczta.onet.pl");
				props.put("mail.smtp.socketFactory.port", "465");
				props.put("mail.smtp.socketFactory.class",
						"javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.port", "465");

				// Get Session
				Session session = Session.getInstance(props,
						new javax.mail.Authenticator() {
							protected PasswordAuthentication getPasswordAuthentication() {
								return new PasswordAuthentication(FROM,
										PASSWORD);
							}
						});

				// Tworzenie wiadomości email
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(FROM));

				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(adresat));// adres uzytkownika
				message.setSubject(temat);
				long dataLogowania = new Date().getTime();
				String dataString = String.valueOf(dataLogowania);
				wiadomosc = Szyfrowanie.hashString(dataString);
				message.setText(dataString);

				// wysłanie wiadomości
				Transport.send(message);
				// transport.close();
				
				//aktalizacja w bazie danych 
				statement = con.prepareStatement("update stronainternetowa.UZYTKOWNICY SET `HASLO` =?  where uzytkownicy.login =?");
				statement.setString(1,wiadomosc );
				statement.setString(2, uzytkownik);
				// wykonanie polecenia
				//TODO
				statement.executeUpdate();
//				statement.executeUpdate("update stronainternetowa.UZYTKOWNICY SET `HASLO` =\""
//								+ wiadomosc
//								+ "\"  where uzytkownicy.login = \""
//								+ uzytkownik
//								+ "\"");
//				
				System.out.println("Wyslano e-maila");

				request.setAttribute("mail",
						"Na dany adres e-mail zostal wyslana wiadomosc.");

			} else {
				request.getSession().setAttribute("mail",
						"Nie ma takiego uzytkownika o takim adresie e-mail w bazie");
			}
			requestDispatcher = request.getRequestDispatcher("index.jsp");
			requestDispatcher.forward(request, response);
		} catch (ClassNotFoundException | MessagingException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}


}
