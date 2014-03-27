package pl.logowanie.net;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionManager {

	 private Connection connection;
     
	    public DBConnectionManager(String dbURL) throws ClassNotFoundException, SQLException{
	        Class.forName("com.mysql.jdbc.Driver");
	        this.connection = DriverManager.getConnection(dbURL);
	    }
	     
	    public Connection getConnection(){
	        return this.connection;
	    }
	    
}
