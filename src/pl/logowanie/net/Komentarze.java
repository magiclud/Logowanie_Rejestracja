package pl.logowanie.net;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Komentarze extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
	private PreparedStatement preparedStatement;
	private ArrayList<String> poprzednieKomentarze;
	private Statement statement;
	private ResultSet result;
	private RequestDispatcher requestDispatcher;

	public Komentarze() {
		super();
	}

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Jestem w  service method w Komentarze");

		poprzednieKomentarze = new ArrayList<String>();

		String tytul = (String) request.getSession().getAttribute("tytul");
		String uzytkownik = (String) request.getSession().getAttribute(
				"userZalogowany");
		String komentarz = request.getParameter("komentarz");

		System.out.println("Uzytkownik " + uzytkownik);
		System.out.println("Tytul " + tytul);
		System.out.println("komentarz: " + komentarz);

		connection = (Connection) getServletContext().getAttribute(
				"DBConnection");
		try {

			String zapytanie = "SELECT uzytkownik, komentarz from stronainternetowa.KOMENTARZE where tytul = \""
					+ tytul + "\"";
			statement = connection.createStatement();
			result = statement.executeQuery(zapytanie);
			while (result.next()) {
				String login = result.getString("uzytkownik") + ": ";
				String wczesniejszyKomentarz = result.getString("komentarz");
				System.out.println("Uzytk. " + login + " komen.: "
						+ wczesniejszyKomentarz);
				poprzednieKomentarze.add(login);
				poprzednieKomentarze.add(wczesniejszyKomentarz);
			}
			if (poprzednieKomentarze.size() != 0) {
				for (int i = 0; i < poprzednieKomentarze.size(); i++) {
					request.getSession().setAttribute("komentarzeWbazie",
							poprzednieKomentarze.get(i));
				}
			}

			if (komentarz != null && tytul!=null && uzytkownik!= null ) {

				preparedStatement = connection
						.prepareStatement("insert into  stronainternetowa.komentarze values (default, ?, ?, ?)");
				preparedStatement.setString(1, tytul);
				preparedStatement.setString(2, uzytkownik);
				preparedStatement.setString(3, komentarz);
				System.out.println(preparedStatement);
				preparedStatement.executeUpdate();

				// TODO po co to nizej?
				request.getSession().setAttribute("nowyKomentarz", komentarz);

				preparedStatement.close();
				requestDispatcher = request
						.getRequestDispatcher("/listaDostepnychUtworow.jsp");
				requestDispatcher.forward(request, response);
			} else {

				requestDispatcher = request
						.getRequestDispatcher("/listaKomentarzy.jsp");
				requestDispatcher.forward(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
