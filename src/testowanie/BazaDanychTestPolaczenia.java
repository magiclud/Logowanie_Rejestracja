package testowanie;

import static org.junit.Assert.*;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.junit.Test;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class BazaDanychTestPolaczenia {
	
	private Connection connect = null;
	  private Statement statement = null;
	  private PreparedStatement preparedStatement = null;
	  private ResultSet resultSet = null;


	  @Test
	public void wczytajBazeDanych() throws Exception{
		 try {
		      // zaladowanie sterownika do bazy danych 
		      Class.forName("com.mysql.jdbc.Driver");
		      // nawiazywanie polaczenia z baza danych , mozna jeszcze dodać haslo jesli potrzeba
		      connect = DriverManager
		          .getConnection("jdbc:mysql://localhost/stronainternetowa?"
		              + "user=root");

		      // stworzenie obiektu polecenia
		      statement = connect.createStatement();
		      // wykonanie polecenia
		      resultSet = statement
		          .executeQuery("select * from stronainternetowa.UZYTKOWNICY");
		      writeResultSet(resultSet);

		      // preparedStatements can use variables and are more efficient
		      preparedStatement = connect
		          .prepareStatement("insert into  stronainternetowa.UZYTKOWNICY values (default, ?, ?, ?, ?)");
		      // "login, e-mail, haslo, katakredyt");
		      // parameters start with 1
		      preparedStatement.setString(1, "Test");
		      preparedStatement.setString(2, "TestEmail");
		      preparedStatement.setString(3, "TestHaslo");
		      preparedStatement.setString(4, "TestCarta");
		      preparedStatement.executeUpdate();

		      preparedStatement = connect
		          .prepareStatement("SELECT login, email,haslo, kartak_redytowa from stronainternetowa.UZYTKOWNICY");
		      resultSet = preparedStatement.executeQuery();
		      writeResultSet(resultSet);

		      // remove again the insert comment
		      preparedStatement = connect
		      .prepareStatement("delete from stronainternetowa.UZYTKOWNICY where login= ? ; ");
		      preparedStatement.setString(1, "Test");
		      preparedStatement.executeUpdate();		   
		      
		    } catch (Exception e) {
		      throw e;
		    } finally {
		      connect.close();
		    }

		  }

		  private void writeMetaData(ResultSet resultSet) throws SQLException {
		    // now get some metadata from the database
		    System.out.println("The columns in the table are: ");
		    System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
		    for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
		      System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
		    }
		  }

		  private void writeResultSet(ResultSet resultSet) throws SQLException {
		    // zapisanie i wyswietlanie wynikow
		    while (resultSet.next()) {
		      String uzytkownik = resultSet.getString("login");
		      String e_mail = resultSet.getString("email");
		      String hasslo = resultSet.getString("haslo");
		      String nrKarty = resultSet.getString("kartak_redytowa");
		      System.out.println("User: " + uzytkownik);
		      System.out.println("Website: " + e_mail);
		      System.out.println("Summary: " + hasslo);
		      System.out.println("Comment: " + nrKarty);
		    }
		  }


}
