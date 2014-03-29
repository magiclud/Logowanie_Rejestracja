package pl.logowanie.net;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Wylogowanie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public Wylogowanie() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("jestem w doGet method w wylogowaniu ");
		request.getSession().invalidate();
		//response.sendRedirect(request.getContextPath() + "Logowanie.login.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("jestem w sevice method w wylogowaniu ");
		request.getSession().invalidate();
		response.sendRedirect(request.getContextPath() + "/wylogowanie.jsp");
	}

}
