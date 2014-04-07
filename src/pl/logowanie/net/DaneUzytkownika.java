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
	
	public DaneUzytkownika() {
		super();
	}

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Jestem w  service method w DaneUzytkownika");
		


		String uzytkownik = (String) request.getSession().getAttribute(
				"userZalogowany");
		
		System.out.println("Uzytkownik " + uzytkownik);
		request.setAttribute("login", uzytkownik);

		try {
			Class.forName("com.mysql.jdbc.Driver");

			// nawiazywanie polaczenia z baza danych , mozna jeszcze
			// dodać haslo jesli potrzeba
			Connection con = DriverManager
					.getConnection("jdbc:mysql://localhost/stronainternetowa?"
							+ "user=root");

			String zapytanie = "SELECT email from stronainternetowa.UZYTKOWNICY where login = \""
					+ uzytkownik + "\" ";
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery(zapytanie);
			String e_mail = null;
			if (result.next()) {
				e_mail = result.getString("email");
			}
			System.out.println(e_mail);
			request.getSession().setAttribute("email", e_mail); // TODO
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("uzytkownik.jsp");
			dispatcher.forward(request, response);
			
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
	
	}
}
