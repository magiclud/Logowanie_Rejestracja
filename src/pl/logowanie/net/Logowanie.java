package pl.logowanie.net;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;


/**
 * Servlet implementation class Login
 */
public class Logowanie extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	//snippet
	private final static Logger LOGGER = Logger.getLogger(Logowanie.class .getName()); 
	
	public Logowanie() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
	}

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("We are in service method of selvlet");
		String username = "user";
		String password = "root";

		String un = request.getParameter("username");
		String pw = request.getParameter("password");

		String msg = " ";

		if (un.endsWith(username) && pw.equals(password)) {
			msg = "Hello " + un + "! Your login is sucessful";
		} else {
			msg = "Hello " + un + "! Your login is failed";
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<font size='6' color= red>" + msg + "</font>");

	}

}
