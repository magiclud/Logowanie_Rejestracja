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

//	public static boolean checkUser(String email, String pass) {
//		boolean st = false;
//		try {
//
//			// loading driver
//			Class.forName("com.mysql.jbdc.Driver");
//
//			// creating connection with the database
//			Connection connect = DriverManager
//			          .getConnection("jdbc:mysql://localhost/stronainternetowa?"
//				              + "user=root");
//			PreparedStatement ps = connect
//					.prepareStatement("select * from register where email =? and pass=?");
//			ps.setString(1, email);
//			ps.setString(2, pass);
//			ResultSet rs = ps.executeQuery();
//			st = rs.next();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return st;
//	}
	
	public static boolean sprawdzEmail(String email)
	{
		String testEmail = "^([A-Za-z0-9]w*)@([A-Za-z0-9]w*(.[A-Za-z]w*)+)$";
		if (!email.matches(testEmail))
		{
			//alert("Proszę wpisać poprawny adres e-mail!");
			return false;
		}
			return true;
	}
}
