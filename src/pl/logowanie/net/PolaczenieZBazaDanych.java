package pl.logowanie.net;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PolaczenieZBazaDanych {

	 private Connection connection;
     
	    public PolaczenieZBazaDanych(String dbURL) throws ClassNotFoundException, SQLException{
	        Class.forName("com.mysql.jdbc.Driver");
	    this.connection = DriverManager
					.getConnection("jdbc:mysql://localhost/stronainternetowa?"
							+ "user=root");
	    }
	     
	    public Connection getConnection(){
	        return this.connection;
	    }
	    
}
