package pl.logowanie.net;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * Servlet implementation class Walidacja
 */
@WebServlet("/Walidacja")
public class Walidacja extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Walidacja() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static boolean checkUser(String email, String pass) {
		boolean st = false;
		try {

			// loading driver
			Class.forName("com.mysql.jbdc.Driver");

			// creating connection with the database
			Connection con = DriverManager.getConnection(
					"jdbc:mysql:/localhost:8000/test", "root", "abhijit");
			PreparedStatement ps = con
					.prepareStatement("select * from register ehere email =? and pass=?");
			ps.setString(1, email);
			ps.setString(2, pass);
			ResultSet rs = ps.executeQuery();
			st = rs.next();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return st;
	}
}
