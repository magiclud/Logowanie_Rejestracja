package pl.logowanie.net;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
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

		int bytesRead;
		InputStream in = request.getInputStream();
		DataInputStream clientData = new DataInputStream(in);
		String fileName = clientData.readUTF();
		String newPath = "D:\\Programy\\eclipseEE\\wokspace\\Logowanie\\pobraneOdPlayera\\"
				+ fileName;
		OutputStream output = new FileOutputStream(newPath);
		long size = clientData.readLong();
		byte[] buffer = new byte[1024];
		while (size > 0
				&& (bytesRead = clientData.read(buffer, 0,
						(int) Math.min(buffer.length, size))) != -1) {
			output.write(buffer, 0, bytesRead);
			size -= bytesRead;
		}
		output.close();
		//in.close();
		String message = "pobrano od klienta jakies dane.";

		request.setAttribute("wynikLogowania", message);
		getServletContext().getRequestDispatcher("/index.jsp").forward(request,
				response);
	}

}
