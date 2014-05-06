package pl.logowanie.net;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Komentarze extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Komentarze() {
        super();
    }

    protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Jestem w  service method w DanePlikuMuzycznego");

		String tytul = (String) request.getSession().getAttribute("tytul");
		String uzytkownik = (String) request.getSession().getAttribute(
				"userZalogowany");

		System.out.println("Uzytkownik " + uzytkownik);
		System.out.println("Tytul " + tytul);
		
    
	}

}
