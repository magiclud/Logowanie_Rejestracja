package pl.logowanie.net;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

//servletContextListener umozliwa m inasluchwianie zdarzeń kontekstu
public class MyServletContextListener implements ServletContextListener{

//	@Override
//	public void contextDestroyed(ServletContextEvent arg0) {
//		// TODO kod zamykajacy polaczenie z baza danych 
//		
//	}
//
//	@Override
//	public void contextInitialized(ServletContextEvent arg0) {
//		// TODO kod inicjalizujacy polaczenie z baza danych i umieszczajacy odpowiedni obiekt w atrybucei kontekstu
//	}
	
	public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext ctx = servletContextEvent.getServletContext();
         
        //initialize DB Connection
        String dbURL = ctx.getInitParameter("dbURL");
         
        try {
        	Class.forName("com.mysql.jdbc.Driver");
            DBConnectionManager connectionManager = new DBConnectionManager(dbURL);
            ctx.setAttribute("DBConnection", connectionManager.getConnection());
            System.out.println("DB Connection initialized successfully.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        Connection con = (Connection) servletContextEvent.getServletContext().getAttribute("DBConnection");
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
