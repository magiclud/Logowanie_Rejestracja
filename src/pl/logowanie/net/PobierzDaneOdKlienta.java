package pl.logowanie.net;

import java.io.DataInputStream;
import java.io.File;
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
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
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

		SSLServerSocketFactory sslSrvFact = (SSLServerSocketFactory) SSLServerSocketFactory
				.getDefault();
		int PORT_NO = 8443;
		SSLServerSocket sSock = (SSLServerSocket) sslSrvFact
				.createServerSocket(PORT_NO);
		SSLSocket sslSock = (SSLSocket) sSock.accept();
		sslSock.getSession();

		int bytesRead;
		InputStream in = request.getInputStream();
		DataInputStream clientData = new DataInputStream(
				sslSock.getInputStream());
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
		sslSock.close();

		String message = "pobrano klucz do klienta ";

		Key kluczUzytkownika = pobierzKlucz(newPath, uzytkownik,
				hasloDokeyStorea);

		String sciezkaDoKeystoreaSerwera = "D:\\Programy\\eclipseEE\\wokspace\\Logowanie\\keystoreSerwera.ks";
		String hasloDoKeystoreaSerwera = "zamek";
		request.getSession()
				.getServletContext()
				.setAttribute("hasloDoKeystoreaSerwera",
						hasloDoKeystoreaSerwera);
		// zapisane klucz do keystorea serwera
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		try {
			KeyStore keyStore = KeyStore.getInstance("UBER", "BC");
			InputStream inputStream = new FileInputStream(
					sciezkaDoKeystoreaSerwera);
			keyStore.load(inputStream, hasloDoKeystoreaSerwera.toCharArray());
			// zaladuj zawartosc keyStore
			File keystoreFile = new File(sciezkaDoKeystoreaSerwera);
			FileInputStream in1 = new FileInputStream(keystoreFile);
			keyStore.load(in1, hasloDoKeystoreaSerwera.toCharArray());
			in1.close();
			// dodaj klucz
			keyStore.setKeyEntry(uzytkownik, kluczUzytkownika,
					hasloDoKeystoreaSerwera.toCharArray(), null);
			// zapisz nowy KeyStore
			// Save the new keystore contents
			FileOutputStream out = new FileOutputStream(keystoreFile);
			keyStore.store(out, hasloDoKeystoreaSerwera.toCharArray());
			out.close();
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
		System.out.println("Zapisano klucz do keystorea serwera");

		request.setAttribute("wynikLogowania", message);
		getServletContext().getRequestDispatcher("/index.jsp").forward(request,
				response);

	}

	static Key pobierzKlucz(String sciezkaDoKeyStore, String aliasHasla,
			String hasloDoKeystora) {

		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		try {
			KeyStore ks = KeyStore.getInstance("UBER", "BC");
			InputStream inputStream = new FileInputStream(sciezkaDoKeyStore);
			ks.load(inputStream, hasloDoKeystora.toCharArray());
			Key klucz = ks.getKey(aliasHasla, hasloDoKeystora.toCharArray());
			inputStream.close();
			return klucz;
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
