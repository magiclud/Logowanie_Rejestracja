package pl.logowanie.net;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Wylogowanie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public Wylogowanie() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("jestem w sevice method w wylogowaniu ");
		request.getSession().invalidate();
		response.sendRedirect(request.getContextPath() + "/wylogowanie.jsp");
	}

}
