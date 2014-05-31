package testowanie;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;
import net.tanesha.recaptcha.ReCaptcha;

 import net.tanesha.recaptcha.ReCaptchaFactory;
public class TestZapytaniaDoBazyDanych {

	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet result = null;

	@Test
	public void test() {
		 ReCaptcha captcha = ReCaptchaFactory.newReCaptcha("6Lfge_QSAAAAAFhyWqrSC3aafCFAFLRM9ZL1-Y0K", "6Lfge_QSAAAAAM2UICmv7mb_8eNd7V4yDwetUSgC", false);
	      
		// zaladowanie sterownika do bazy danych
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// nawiazywanie polaczenia z baza danych , mozna jeszcze dodać haslo
			// jesli potrzeba
			connect = DriverManager
					.getConnection("jdbc:mysql://localhost/stronainternetowa?"
							+ "user=root");
			String login = "\"Sandra\"";

			String zapytanie = "SELECT login from stronainternetowa.UZYTKOWNICY where login = "+login;
			statement = connect.createStatement();
			result = statement.executeQuery(zapytanie);
			if (result.next()) {
					System.out
							.println("Jestem w ifie, czyli isnieje taki uzytkownik w bazie");
					String uzytkownik = result.getString("login");
					System.out.println("User: " + uzytkownik);
				} else {
					System.out.println("nie ma takiego uzytkownika w bazie");
//					String uzytkownik = result.getString("login");
//					System.out.println("User: " + uzytkownik);
				}
			

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Test
	public void testWyszukiwaniaUzytkownikaZHaslem() {

		// zaladowanie sterownika do bazy danych
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// nawiazywanie polaczenia z baza danych , mozna jeszcze dodać haslo
			// jesli potrzeba
			connect = DriverManager
					.getConnection("jdbc:mysql://localhost/stronainternetowa?"
							+ "user=root");
			String login = "\"agnieszka\"";
String haslo = "\"aaa\"";
			String zapytanie = "SELECT login, haslo from stronainternetowa.UZYTKOWNICY where login = "+ login+" and haslo = "+haslo ;
			statement = connect.createStatement();
			result = statement.executeQuery(zapytanie);
			if (result.next()) {
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
			String hasslo = resultSet.getString("haslo");
//			String nrKarty = resultSet.getString("kartak_redytowa");
			System.out.println("User: " + uzytkownik);
//			System.out.println("Website: " + e_mail);
			System.out.println("Haslo: " + hasslo);
//			System.out.println("Comment: " + nrKarty);
		//}
	}
}
