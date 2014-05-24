package pl.logowanie.net;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.security.KeyStore;
import java.security.Security;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SpecjalneDaneDlaPlayera extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final int PORT_NO = 2000;

	public SpecjalneDaneDlaPlayera() {
		super();
	}

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out
				.println("jestem w sevice method w specjalne dane dla klienta  ");

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
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		try {
//			int port = 8443;
//			SSLContext sslContext = createSSLContext();
//			SSLServerSocketFactory fact = sslContext.getServerSocketFactory();
//			SSLServerSocket sSock = (SSLServerSocket) fact
//					.createServerSocket(port);
//			sSock.setWantClientAuth(true);
//			for (;;) {
//				SSLSocket sslSock = (SSLSocket) sSock.accept();
//				sslSock.startHandshake();
//
//				readRequest(sslSock.getInputStream());
//				SSLSession session = sslSock.getSession();
//			
////			Principal clientID = session.getPeerPrincipal();
//			sslSock.getOutputStream();
//			
//			sslSock.close();
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		 SSLServerSocketFactory sslSrvFact = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		  SSLServerSocket        sSock = (SSLServerSocket)sslSrvFact.createServerSocket(PORT_NO);
		  SSLSocket sslSock = (SSLSocket)sSock.accept();
			sslSock.getSession();
		  
		DataOutputStream dstream = new DataOutputStream( sslSock.getOutputStream());
		System.out.println("liczba " + liczba);
		dstream.writeInt(liczba);
		System.out.println("login " + login);
		String hasloDoKeystorea = "muzyka";
		dstream.writeBytes(login + "#" + hasloDoKeystorea);
		// dstream.writeLong(mybytearray.length);
		// dstream.write(mybytearray, 0, mybytearray.length);
		// dstream.flush();
		// System.out.println("!!!!!File " + fileName + " sent to Server.");
		dstream.close();
		sslSock.close();
		request.getSession().getServletContext()
				.setAttribute("hasloDoKeystorea", hasloDoKeystorea);
		request.setAttribute("liczbaLogowan", "#" + liczba + "#");
		request.setAttribute("uzytkownik", "#" + login + "#");
		// RequestDispatcher requestDispatcher = request
		// .getRequestDispatcher("specjalneDaneDlaKienta.jsp");
		// requestDispatcher.forward(request, response);
	}


	private void readRequest(InputStream in) throws IOException {
		System.out.println("Zadanie");
		int ch = 0;
		int lastCh = 0;
		while ((ch = in.read()) >= 0 && ch != '\n' && lastCh != '\n') {
			System.out.print((char) ch);
			if (ch != '\r') {
				lastCh = ch;
			}
		}
		System.out.println();
	}

	private SSLContext createSSLContext() throws Exception {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

		String hasloSerwera = "hasloSerwera";
		String hasloDoRepozytorium = "hasloRepozytorium";
		// przegotowanie menadzera kluczy z lokalnymi danymi identyfikacyjnymi
		KeyManagerFactory mgrFact = KeyManagerFactory.getInstance("SunX509");

		KeyStore keyStore = KeyStore.getInstance("UBER", "BC");
		InputStream inputStream = new FileInputStream("serwer.jks");

		keyStore.load(inputStream, hasloSerwera.toCharArray());

		mgrFact.init(keyStore, hasloSerwera.toCharArray());

		// przygotowanie menedzera certyfikatow zaufanych pozwalajacego
		// uwiezytelnic serwer

		TrustManagerFactory trustFact = TrustManagerFactory
				.getInstance("SunX509");
		KeyStore trustStore = KeyStore.getInstance("UBER", "BC");

		trustStore.load(new FileInputStream("zaufaneRepozytorium.jks"),
				hasloDoRepozytorium.toCharArray());
		trustFact.init(trustStore);

		// utowrzenie kontekstu i przygotowanie obiektu fabrykujacego gniazda
		SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(mgrFact.getKeyManagers(), trustFact.getTrustManagers(),
				null);// (KeyManager, TrustManager [],SecureRandom )

		return sslContext;
	}
}

