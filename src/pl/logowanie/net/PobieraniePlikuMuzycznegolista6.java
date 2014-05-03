package pl.logowanie.net;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.farng.mp3.TagException;

public class PobieraniePlikuMuzycznegolista6 extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
	
		byte[] zdekodowanyPlik = przygotujPlikDoPrzeslania(sciezkaDoPliku);
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
		String uzytkownik = (String) request.getSession().getAttribute(
				"userZalogowany");
		
		System.out.println("Uzytkownik " + uzytkownik);
		
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// nawiazywanie polaczenia z baza danych , mozna jeszcze
			// dodać haslo jesli potrzeba
			Connection con = DriverManager
					.getConnection("jdbc:mysql://localhost/stronainternetowa?"
							+ "user=root");

			String zapytanie = "SELECT  KARTA_KREDYTOWA from stronainternetowa.UZYTKOWNICY where login = \""
					+ uzytkownik + "\" ";
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery(zapytanie);
			byte[] nrKarty = null;
			if (result.next()) {
				nrKarty = result.getBytes("karta_kredytowa");
			}
			String aliasHasla = uzytkownik;
			String sciezkaDoKeyStore = "D:\\Programy\\eclipseEE\\wokspace\\Logowanie\\keyStore.ks";
			byte[] odszyfrowanyNumer = Szyfrowanie.dekodujWiadomosc(nrKarty, Szyfrowanie.pobierzKlucz(sciezkaDoKeyStore, aliasHasla));
			String numerKartyKredytowej =  new String(odszyfrowanyNumer);
			
			char[] nrKartyKred = numerKartyKredytowej.toCharArray();
			String message = null;
			for (int i=0; i<nrKartyKred.length; i++){
				if(i>=11){
					message +=nrKartyKred[i];
				}
				 message +="*"; 
			}
			
			request.setAttribute("fragmentNrKarty", message);
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("pobieraniePliku.jsp");
			dispatcher.forward(request, response);
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
	}

	private byte[] przygotujPlikDoPrzeslania(String sciezkaDoPliku) {
		try {
			File file = new File(sciezkaDoPliku);
			FileInputStream fileIn = new FileInputStream(file);
			ByteArrayOutputStream bOut = new ByteArrayOutputStream();
			OutputStream outStr = new DataOutputStream(bOut);
			byte[] outputByte = new byte[4096];

			while(fileIn.read(outputByte, 0, 4096) != -1)
			{
				outStr.write(outputByte, 0, 4096);
			}
			
			byte[]cipherText = bOut.toByteArray();

			fileIn.close();
			outStr.close();
			return cipherText;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;

	}

}
