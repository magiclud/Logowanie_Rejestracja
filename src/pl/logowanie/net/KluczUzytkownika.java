package pl.logowanie.net;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/KluczUzytkownika")
// do rejestracji servleta w tomcacie
public class KluczUzytkownika extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
	private String uzytkownik;
	private String kluczUzytkownika;
	private PreparedStatement preparedStatement;

	public KluczUzytkownika() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		// wydobycie parametrow wraz z żądaniem (requestem) HTTP
		kluczUzytkownika = request.getParameter("klucz");
		System.out.println("KLucz " + kluczUzytkownika);
		out.print("Klucz: ");
		out.println(kluczUzytkownika);
		
		
		uzytkownik = (String) request.getSession().getAttribute(
				"userZalogowany");
		System.out.println("****  " + uzytkownik);

		String wiadomosc = null;

		connection = (Connection) getServletContext().getAttribute(
				"DBConnection");

		try {
			preparedStatement = connection
					.prepareStatement("UPDATE  stronainternetowa.UZYTKOWNICY SET klucz = ? where login = \""
						+ uzytkownik + "\"");

			preparedStatement.setString(1, kluczUzytkownika);

			preparedStatement.executeUpdate();
			request.getSession().setAttribute("kluczUzytkownika",
					kluczUzytkownika);

			wiadomosc = "Twoj nowy klucz: " + kluczUzytkownika + "\n";
			preparedStatement.close();
			System.out.println("dodano do bazy bezproblemowo");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {


	}

}
