package pl.logowanie.net;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DanePlikuMuzycznego extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DanePlikuMuzycznego() {
        super();
    }
    protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Jestem w  service method w DanePlikuMuzycznego");
		

//TODO tutaj co na ten wzor, ze jak niby klinknie na jakas nazwe pliku to tu to sie przekaze
		String tytul = (String) request.getSession().getAttribute(
				"tytul");
		
		System.out.println("Tytul " + tytul);
		request.setAttribute("tytul", tytul);

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
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("danePiosenki.jsp");
			dispatcher.forward(request, response);
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
