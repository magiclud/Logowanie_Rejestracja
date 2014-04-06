package pl.logowanie.net;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import simpleemail.SimpleEmail;



/**
 * Servlet implementation class WyslanieMaila
 */
public class WyslanieMaila extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public WyslanieMaila() {
		super();
	}

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Jestem w  service method w WyslanieMaila");

//		SimpleEmail email = new SimpleEmail("smtp.gmail.com",465,"myUsername","myPassword");
//		email.setHostName("mail.myserver.com");
//		email.addTo("jdoe@somewhere.org", "John Doe");
//		email.setFrom("me@apache.org", "Me");
//		email.setSubject("Test message");
//		email.setMsg("This is a simple test of commons-email");
//		email.send();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}
