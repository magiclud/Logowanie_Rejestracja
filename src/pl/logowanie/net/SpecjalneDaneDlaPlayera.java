package pl.logowanie.net;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SpecjalneDaneDlaPlayera extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SpecjalneDaneDlaPlayera() {
		super();
	}

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out
				.println("jestem w sevice method w specjalne dane dla klienta  ");

		String login = (String) request.getSession().getServletContext()
				.getAttribute("user");

		Connection connection = (Connection) getServletContext().getAttribute(
				"DBConnection");

		String zapytanie = "SELECT LiczbaLogowan from stronainternetowa.UZYTKOWNICY where login = \""
				+ login + "\"";
		Statement statement;
		int liczba = 0;
		try {
			statement = connection.createStatement();
			ResultSet result = statement.executeQuery(zapytanie);
			if (result.next()) {
				liczba = result.getInt("LiczbaLogowan");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ServletOutputStream con = response.getOutputStream();
		DataOutputStream dstream = new DataOutputStream(con);
		System.out.println("liczba " + liczba);
		dstream.writeInt(liczba);
		System.out.println("login " + login);
		String hasloDoKeystorea = "muzyka";
		dstream.writeBytes(login + "#" + hasloDoKeystorea);
		// dstream.writeLong(mybytearray.length);
		// dstream.write(mybytearray, 0, mybytearray.length);
		// dstream.flush();
		// System.out.println("!!!!!File " + fileName + " sent to Server.");
		dstream.close();
		request.getSession().getServletContext().setAttribute("hasloDoKeystorea",
				hasloDoKeystorea);
		request.setAttribute("liczbaLogowan", "#" + liczba + "#");
		request.setAttribute("uzytkownik", "#" + login + "#");
		// RequestDispatcher requestDispatcher = request
		// .getRequestDispatcher("specjalneDaneDlaKienta.jsp");
		// requestDispatcher.forward(request, response);
	}
}
