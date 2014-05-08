package pl.logowanie.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PobierzDaneOdKlienta extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PobierzDaneOdKlienta() {
		super();
	}

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("jestem w sevice method w pobieranie od klienta  ");
		InputStream in = request.getInputStream();
		BufferedReader r = new BufferedReader(new InputStreamReader(in));
		StringBuffer buf = new StringBuffer();
		String line;
		while ((line = r.readLine()) != null) {
			System.out.println("line " + line);
			buf.append(line);
			System.out.println("buf " + buf.toString());
		}
		String fileName = "D:\\Programy\\eclipseEE\\wokspace\\Logowanie\\pobraneOdPlayera\\dane.txt";
		String s = buf.toString();
		System.out.println("Dane od klienta pobrane " + s);

		String message = "pobrano od klienta jakies dane.";

		request.setAttribute("wynikLogowania", message);
		getServletContext().getRequestDispatcher("/index.jsp").forward(request,
				response);
	}

}
