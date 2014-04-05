package pl.logowanie.net;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DaneUzytkownika
 */
@WebServlet("/DaneUzytkownika")
public class DaneUzytkownika extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DaneUzytkownika() {
        super();
    }
  


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  System.out.println("Jestem w  service method w DaneUzytkownika");

			String uzytkownik =  request; //TODO
			System.out.println("Uzytkownik "+ uzytkownik); 
			request.setAttribute("login", uzytkownik);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doGet(request, response);
	}

}
