package pl.logowanie.net;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class PrzesylaniePliku extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// private final String UPLOAD_DIRECTORY =
	// "D:\\Programy\\eclipseEE\\wokspace\\Logowanie\\pobranePliki\\";
	private static final String UPLOAD_DIRECTORY = "upload";
	private static final int THRESHOLD_SIZE = 1024 * 1024 * 3; // 3MB
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB
	private Connection connection;

	public PrzesylaniePliku() {
		super();
	}

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String uzytkownik = (String) request.getSession().getAttribute(
				"userZalogowany");
		String grupaUzytkownika = null;
		System.out.println("****  " + uzytkownik
				+ "   **** chce przeslac do serwera plik ");

		connection = (Connection) getServletContext().getAttribute(
				"DBConnection");

		String zapytanie = "SELECT grupa from stronainternetowa.UZYTKOWNICY_strony where login = \""
				+ uzytkownik + "\"";
		Statement statement;
		try {
			statement = connection.createStatement();

			ResultSet result = statement.executeQuery(zapytanie);
			if (result.next()) {
				grupaUzytkownika = result.getString("grupa");
				System.out.println("Grupa uzytkownika to: " + grupaUzytkownika);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (grupaUzytkownika!=null &&grupaUzytkownika.equals("admin")) {
			// checks if the request actually contains upload file
			if (!ServletFileUpload.isMultipartContent(request)) {
				PrintWriter writer = response.getWriter();
				writer.println("Request does not contain upload data");
				writer.flush();
				return;
			}// configures upload settings
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(THRESHOLD_SIZE);
			factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setFileSizeMax(MAX_FILE_SIZE);
			upload.setSizeMax(MAX_REQUEST_SIZE);

			// constructs the directory path to store upload file
			String uploadPath = getServletContext().getRealPath("")
					+ File.separator + UPLOAD_DIRECTORY;
			System.out.println("Plik jest w " + uploadPath);
			// creates the directory if it does not exist
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) {
				uploadDir.mkdir();
			}

			try {
				// parses the request's content to extract file data
				List formItems = upload.parseRequest(request);// wymuszone
																// rzutowanie
				Iterator iter = formItems.iterator();

				// iterates over form's fields
				while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next();
					// processes only fields that are not form fields
					if (!item.isFormField()) {
						String fileName = new File(item.getName()).getName();
						String filePath = uploadPath + File.separator
								+ fileName;
						File storeFile = new File(filePath);

						// saves the file on disk
						item.write(storeFile);
					}
				}
				request.setAttribute("message",
						"Przeslanie pliku zakonczylo sie sukcesem!");
			} catch (Exception ex) {
				ex.printStackTrace();
				System.err.println(ex);
				request.setAttribute("message",
						"Blad przesylania: " + ex.getMessage());
			}
		} else {
			request.setAttribute("message",
					"Uzytkownik nie ma odpowiednich uprawnien do dodania utworu do serwisu");
		}
		getServletContext().getRequestDispatcher("/rezultPrzeslaniaPliku.jsp")
				.forward(request, response);
	}

}
