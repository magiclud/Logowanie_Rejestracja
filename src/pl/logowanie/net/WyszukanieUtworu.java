package pl.logowanie.net;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.id3.ID3v1;

public class WyszukanieUtworu extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RequestDispatcher requestDispatcher;
	private ArrayList<File> pasujacePlikiMuzyczne = new ArrayList<File>();

	public WyszukanieUtworu() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String tytul = request.getParameter("tytul");
		String wykonawca = request.getParameter("wykonawca");
		String gatunek = request.getParameter("gatunek");
		String wiadomosc = null;

		if (tytul.equals("") && wykonawca.equals("") && gatunek.equals("")) {
			wiadomosc = "Nie podales danych do wyszukania \n";
		} else {
			String sciezka = "D:\\Programy\\eclipseEE\\wokspace\\Logowanie\\muzyka";
			File katalog = new File(sciezka);
			// wszyszukaj wszystkie pliki dodaj je do wspolnej listy
			// znajdzWszystkiePlikiMuzyczne

			// przekieruj na strone gdzie wyswitlisz wyniki z listy oraz
			// uzmozliwisz pobranie pliku
			// zasymuluj pobranie oplaty ujawniajcac 4 ostatnie cyfry karty
			// kredytowej

			try { // znjaduj plki pasujace do jednego ze wzorca i dodaj do
					// wsolenj listy wynikow
				if (!tytul.equals("")) {
					znajdzPlikiPasujaceDoTytulu(katalog, tytul);
				}
				if (!wykonawca.equals("")) {
					znajdzPlikiPasujaceDoWykonawcy(katalog, wykonawca);
				}
				if(!gatunek.equals("")){
					znajdzPlikiPasujaceDoGatunku(katalog, gatunek);
				}
			} catch (TagException e) {
				e.printStackTrace();
			}
		}
		request.setAttribute("wynikRejestracji", wiadomosc);
		requestDispatcher = request.getRequestDispatcher("index.jsp");// TODO
																		// albo
																		// listaDOstepnychUtworow.jsp
		requestDispatcher.forward(request, response);
	}

	private void znajdzPlikiPasujaceDoGatunku(File katalog, String gatunek) throws IOException, TagException {
		File pliki[] = katalog.listFiles();
		for (int i = 0; i < pliki.length; i++) {
			if (pliki[i].isDirectory())
				znajdzPlikiPasujaceDoWykonawcy(new File(katalog.getName()
						+ File.separatorChar + pliki[i].getName()), gatunek);
			else if (pliki[i].isFile()) {
				String lokalizacja = pliki[i].getAbsolutePath();
				// System.out.println("Sciezka " + lokalizacja);
				MP3File mp3file = new MP3File(lokalizacja);
				ID3v1 tag = mp3file.getID3v1Tag();
				if (tag.getGenre().equals( GatunekUtworu.valueOf(gatunek))) {
					pasujacePlikiMuzyczne.add(pliki[i]);
					System.out.println("Plik" + i + ": " + pliki[i].getName());
				}
			}

		}		
		
	}

	private void znajdzPlikiPasujaceDoWykonawcy(File katalog, String wykonawca) throws IOException, TagException {
		File pliki[] = katalog.listFiles();
		for (int i = 0; i < pliki.length; i++) {
			if (pliki[i].isDirectory())
				znajdzPlikiPasujaceDoWykonawcy(new File(katalog.getName()
						+ File.separatorChar + pliki[i].getName()), wykonawca);
			else if (pliki[i].isFile()) {
				String lokalizacja = pliki[i].getAbsolutePath();
				// System.out.println("Sciezka " + lokalizacja);
				MP3File mp3file = new MP3File(lokalizacja);
				ID3v1 tag = mp3file.getID3v1Tag();
				if (tag.getArtist().equals(wykonawca)) {
					pasujacePlikiMuzyczne.add(pliki[i]);
					System.out.println("Plik" + i + ": " + pliki[i].getName());
				}
			}

		}		
	}

	private void znajdzPlikiPasujaceDoTytulu(File katalog, String tytul)
			throws IOException, TagException {
		File pliki[] = katalog.listFiles();
		for (int i = 0; i < pliki.length; i++) {
			if (pliki[i].isDirectory())
				znajdzPlikiPasujaceDoTytulu(new File(katalog.getName()
						+ File.separatorChar + pliki[i].getName()), tytul);
			else if (pliki[i].isFile()) {
				String lokalizacja = pliki[i].getAbsolutePath();
				// System.out.println("Sciezka " + lokalizacja);
				MP3File mp3file = new MP3File(lokalizacja);
				ID3v1 tag = mp3file.getID3v1Tag();
				if (tag.getTitle().equals(tytul)) {
					pasujacePlikiMuzyczne.add(pliki[i]);
					System.out.println("Plik" + i + ": " + pliki[i].getName());
				}
			}

		}
	}

}
