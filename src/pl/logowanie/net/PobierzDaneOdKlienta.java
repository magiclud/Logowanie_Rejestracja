package pl.logowanie.net;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

		String uzytkownik = (String) request.getSession().getServletContext()
				.getAttribute("user");
		String hasloDokeyStorea = (String) request.getSession()
				.getServletContext().getAttribute("hasloDoKeystorea");
		System.out.println("user: " + uzytkownik + ", haslo do keystore: "
				+ hasloDokeyStorea);

		int bytesRead;
		InputStream in = request.getInputStream();
		DataInputStream clientData = new DataInputStream(in);
		String fileName = clientData.readUTF();
		System.out.println("nazwa pobranego pliku " + fileName);
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
		in.close();

		String message = "pobrano klucz do klienta ";

		Key kluczUzytkownika = pobierzKlucz(newPath, uzytkownik,
				hasloDokeyStorea);
		// pobrano klucz z keystora od klienta

		String sciezkaDoKeystoreaSerwera = "D:\\Programy\\eclipseEE\\wokspace\\Logowanie\\keystoreSerwera.ks";
		String hasloDoKeystoreaSerwera = "zamek";
		// zapisane klucz do keystorea serwera
		KeyStore keyStore;
		try {
			keyStore = KeyStore.getInstance("UBER", "BC");
			InputStream inputStream = null;
			keyStore.load(inputStream, hasloDoKeystoreaSerwera.toCharArray());
			keyStore.setKeyEntry(uzytkownik, kluczUzytkownika,
					hasloDoKeystoreaSerwera.toCharArray(), null);
			// zapisz keyStore
			FileOutputStream fos = new FileOutputStream(
					sciezkaDoKeystoreaSerwera);
			keyStore.store(fos, hasloDoKeystoreaSerwera.toCharArray());
			fos.close();
			// zappisano klucz do keystorea serwera
		} catch (KeyStoreException | NoSuchProviderException
				| NoSuchAlgorithmException | CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Zapisano klucz do keystorea serwera");

		request.setAttribute("wynikLogowania", message);
		getServletContext().getRequestDispatcher("/index.jsp").forward(request,
				response);

	}

	static Key pobierzKlucz(String sciezkaDoKeyStore, String aliasHasla,
			String hasloDoKeystora) {

		try {
			KeyStore ks = KeyStore.getInstance("UBER", "BC");
			InputStream inputStream = new FileInputStream(sciezkaDoKeyStore);
			ks.load(inputStream, hasloDoKeystora.toCharArray());

			return ks.getKey(aliasHasla, hasloDoKeystora.toCharArray());
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
