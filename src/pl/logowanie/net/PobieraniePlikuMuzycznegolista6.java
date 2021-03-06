package pl.logowanie.net;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.farng.mp3.TagException;

public class PobieraniePlikuMuzycznegolista6 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static String sciezkaDoKeyStore = "D:\\Programy\\eclipseEE\\wokspace\\Logowanie\\keystoreSerwera.ks";
	static String aliasHasla;
	static String hasloDoKeystoreaSerwera;

	public PobieraniePlikuMuzycznegolista6() {
		super();
	}

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out
				.println("jestem w sevice method w pobieraniu muzyki z listy 6 ");

		
		//TODO przekierowani do innej klasy i wysyaniem danych do klienta - albo zbudowanie servera i zaleznie od wyniku przekierowanie na odpowiednia strone
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition",
				"attachment;filename=music.mp3");

		String tytul = (String) request.getSession().getAttribute("tytul");
		String uzytkownik = (String) request.getSession().getServletContext()
				.getAttribute("user");
		hasloDoKeystoreaSerwera = (String) request.getSession()
				.getServletContext().getAttribute("hasloDoKeystoreaSerwera");

		aliasHasla = uzytkownik;
		
		String message = null;
		
		String login = (String) request.getSession().getServletContext()
				.getAttribute("user");

		Connection connection = (Connection) getServletContext().getAttribute(
				"DBConnection");

		String zapytanie = "SELECT LiczbaLogowan from stronainternetowa.UZYTKOWNICY_strony where login = \""
				+ login + "\"";
		Statement statement;
		int liczba = 0;
		try {
			statement = connection.createStatement();
			ResultSet result = statement.executeQuery(zapytanie);
			if (result.next()) {
				liczba = result.getInt("LiczbaLogowan");
				if (liczba > 1) {
					System.out
							.println("Pobrano z sesji tytul muzyki: " + tytul);
					// informuje przegladarke ze system ma zamiar zwrocic plik
					// zamiast
					// normalnej strony html

					String sciezka = "D:\\Programy\\eclipseEE\\wokspace\\Logowanie\\muzyka";
					File katalog = new File(sciezka);
					WyszukanieUtworu plikMuzyczny = new WyszukanieUtworu();
					String sciezkaDoPliku;
					try {
						sciezkaDoPliku = plikMuzyczny
								.znajdzPlikiPasujaceDoTytulu(katalog, tytul);
						System.out.println("plik muzyczny " + sciezkaDoPliku);
						System.out.println("sciezka do keystorea "
								+ sciezkaDoKeyStore);
						Key klucz = pobierzKlucz(sciezkaDoKeyStore, aliasHasla);
						byte[] zdekodowanyPlik = przygotujPlikDoPrzeslania(sciezkaDoPliku);
						byte[] zakodowanyPlik = zakoduj(new String(
								zdekodowanyPlik), klucz);
						// byte[] zaszyfrowanyPlik=
						// Szyfrowanie.zaszyfrowaniePliku(klucz,
						// sciezkaDoPliku);
						ServletOutputStream out = response.getOutputStream();
						out.write(zakodowanyPlik);

						out.flush();
						out.close();
						message = "Pobrano plik muzyczny";
					} catch (TagException e) {
						e.printStackTrace();
					}
				}else{
					message = "Niemozliwe pobranie pliku muzycznego - nalezy uruchomic Playera";
				}
			}else{
				message = "Nieudane  polaczenie z baza danych";
			}

			request.setAttribute("wynikPobierania", message);
		} catch (SQLException e) {
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

			while (fileIn.read(outputByte, 0, 4096) != -1) {
				outStr.write(outputByte, 0, 4096);
			}
			byte[] cipherText = bOut.toByteArray();

			fileIn.close();
			outStr.close();
			return cipherText;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static Key pobierzKlucz(String sciezkaDoKeyStore, String aliasHasla) {

		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		try {
			KeyStore ks = KeyStore.getInstance("UBER", "BC");
			InputStream inputStream = new FileInputStream(sciezkaDoKeyStore);
			ks.load(inputStream, hasloDoKeystoreaSerwera.toCharArray());
			Key klucz = ks.getKey(aliasHasla,
					hasloDoKeystoreaSerwera.toCharArray());
			inputStream.close();
			return klucz;
		} catch (UnrecoverableKeyException | IOException | KeyStoreException
				| NoSuchAlgorithmException | NoSuchProviderException
				| CertificateException e) {
			e.printStackTrace();
		}
		return null;
	}

	static byte[] zakoduj(String wiadomosc, Key key) {
		try {
			Cipher aesCipher = Cipher.getInstance("RC4");
			aesCipher.init(Cipher.ENCRYPT_MODE, key);
			return aesCipher.doFinal(wiadomosc.getBytes());
		} catch (InvalidKeyException | NoSuchAlgorithmException
				| NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
