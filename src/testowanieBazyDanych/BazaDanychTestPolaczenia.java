package testowanieBazyDanych;

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
		      // this will load the MySQL driver, each DB has its own driver
		      Class.forName("com.mysql.jdbc.Driver");
		      // setup the connection with the DB.
		      connect = DriverManager
		          .getConnection("jdbc:mysql://localhost/stronainternetowa?"
		              + "user=root");

		      // statements allow to issue SQL queries to the database
		      statement = connect.createStatement();
		      // resultSet gets the result of the SQL query
		      resultSet = statement
		          .executeQuery("select * from stronainternetowa.UZYTKOWNICY");
//		      writeResultSet(resultSet);

		      // preparedStatements can use variables and are more efficient
		      preparedStatement = connect
		          .prepareStatement("insert into  stronainternetowa.UZYTKOWNICY values (default, ?, ?, ?, ?)");
		      // "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
		      // parameters start with 1
		      preparedStatement.setString(1, "Test");
		      preparedStatement.setString(2, "TestEmail");
		      preparedStatement.setString(3, "TestHaslo");
		      preparedStatement.setString(4, "1234567890123456");
		      preparedStatement.executeUpdate();

		      preparedStatement = connect
		          .prepareStatement("SELECT login, email,haslo, kartak_redytowa from stronainternetowa.UZYTKOWNICY");
		      resultSet = preparedStatement.executeQuery();
//		      writeResultSet(resultSet);

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
		    // resultSet is initialised before the first data set
		    while (resultSet.next()) {
		      // it is possible to get the columns via name
		      // also possible to get the columns via the column number
		      // which starts at 1
		      // e.g., resultSet.getSTring(2);
		      String user = resultSet.getString("login");
		      String e_mail = resultSet.getString("email");
		      String hasslo = resultSet.getString("haslo");
		      Date date = resultSet.getDate("datum");
		      String nrKarty = resultSet.getString("summary");
		      System.out.println("User: " + user);
		      System.out.println("Website: " + e_mail);
		      System.out.println("Summary: " + hasslo);
		      System.out.println("Date: " + date);
		      System.out.println("Comment: " + nrKarty);
		    }
		  }


}
