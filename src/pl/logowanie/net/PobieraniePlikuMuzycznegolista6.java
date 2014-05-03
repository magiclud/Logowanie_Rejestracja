package pl.logowanie.net;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.farng.mp3.TagException;

public class PobieraniePlikuMuzycznegolista6 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection connect = null;
	private Statement statement = null;
	private ResultSet result = null;

	static String sciezkaDoKeyStore = "D:\\Programy\\eclipseEE\\wokspace\\Logowanie\\keyStore.ks";
	static String aliasHasla = "mojAlias";

	public PobieraniePlikuMuzycznegolista6() {
		super();
	}

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("jestem w sevice method w pobieraniu muzyki z listy 6 ");

		String tytul = (String) request.getAttribute("tytul");
		System.out.println("Pobrano z sesji tytul muzyki: "+ tytul);
		// informuje przegladarke ze system ma zamiar zwrocic plik zamiast
		// normalnej strony html
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition",
				"attachment;filename=music.mp3");

		String sciezka = "D:\\Programy\\eclipseEE\\wokspace\\Logowanie\\muzyka";
		File katalog = new File(sciezka);
		WyszukanieUtworu plikMuzyczny = new WyszukanieUtworu();
		String sciezkaDoPliku;
		try {
			sciezkaDoPliku = plikMuzyczny.znajdzPlikiPasujaceDoTytulu(katalog, tytul);
	
		byte[] zdekodowanyPlik = Szyfrowanie.deszyfrowaniePliku(Szyfrowanie
				.pobierzKlucz(sciezkaDoKeyStore, new String(aliasHasla)),
				sciezkaDoPliku);
		ServletOutputStream out = response.getOutputStream();
		out.write(zdekodowanyPlik);

		// AudioInputStream outSteream;
		// try {
		// outSteream = AudioSystem
		// .getAudioInputStream(new ByteArrayInputStream(
		// zdekodowanyTekst));
		// Clip clip = AudioSystem.getClip();
		// clip.open(outSteream);
		// clip.start();
		// } catch (UnsupportedAudioFileException | LineUnavailableException e)
		// {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// JFrame a = new JFrame();
		// // a.setVisible(true);
		//
		// }
		//
		out.flush();
		out.close();
		} catch (TagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String polaczZbazaIZnajdzPlik() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// nawiazywanie polaczenia z baza danych , mozna jeszcze dodać haslo
			// jesli potrzeba
			connect = DriverManager
					.getConnection("jdbc:mysql://localhost/stronainternetowa?"
							+ "user=root");

			String nazwaPliku = "\"soft\"";
			String lokalizacjaPliku = null;

			String zapytanie = "SELECT sciezka from stronainternetowa.PLIKI where nazwa = "
					+ nazwaPliku;
			statement = connect.createStatement();
			result = statement.executeQuery(zapytanie);
			if (result.next()) {
				System.out.println("Jestem w ifie, czyli isnieje taki plik");
				lokalizacjaPliku = result.getString("sciezka");
				System.out.println("Plik jest tutaj: " + lokalizacjaPliku);
			} else {
				System.out.println("nie ma takiego pliku w bazie");
			}
			return lokalizacjaPliku;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
