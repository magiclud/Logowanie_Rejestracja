package testowanieBazyDanych;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

public class TestZapytaniaDoBazyDanych {

	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet result = null;

	@Test
	public void test() {

		// zaladowanie sterownika do bazy danych
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// nawiazywanie polaczenia z baza danych , mozna jeszcze dodać haslo
			// jesli potrzeba
			connect = DriverManager
					.getConnection("jdbc:mysql://localhost/stronainternetowa?"
							+ "user=root");
			String login = "111";

			String zapytanie = "SELECT login from stronainternetowa.UZYTKOWNICY where login= "+ login;
			statement = connect.createStatement();
			result = statement.executeQuery(zapytanie);
			if (result.next()&& (result.getString(1)).equals(login)) {
					System.out
							.println("Jestem w ifie, czyli isnieje taki uzytkownik w bazie");
					writeResultSet(result);
				} else {
					System.out.println("nie ma takiego uzytkownika w bazie");
					writeResultSet(result);
				}
			

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void writeResultSet(ResultSet resultSet) throws SQLException {
		// zapisanie i wyswietlanie wynikow
		//while (resultSet.next()) {
			String uzytkownik = resultSet.getString("login");
//			String e_mail = resultSet.getString("email");
//			String hasslo = resultSet.getString("haslo");
//			String nrKarty = resultSet.getString("kartak_redytowa");
			System.out.println("User: " + uzytkownik);
//			System.out.println("Website: " + e_mail);
//			System.out.println("Summary: " + hasslo);
//			System.out.println("Comment: " + nrKarty);
		//}
	}
}
