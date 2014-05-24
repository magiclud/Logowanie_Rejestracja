package pl.logowanie.net;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 * Servlet implementation class Login
 */
public class Logowanie extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private int dozwolonaIloscProbLogowan = 3;
	private int czasCzekaniaGdyNieprawneLog = 30000; // w
														// milisekundach

	public Logowanie() {
		super();
	}

	protected synchronized void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Jestem w  service method of selvlet");

		// InputStream in = request.getInputStream();
		// BufferedReader r = new BufferedReader(new InputStreamReader(in));
		// StringBuffer buf = new StringBuffer();
		// String line;
		// while ((line = r.readLine())!=null) {
		// buf.append(line);
		// }
		// String s = buf.toString();
		// System.out.println("!!!!!!     "+s+ "   !!!!!!!!!");
		//
		String uzytkownik = request.getParameter("username");
		String hasloUzytkownika = request.getParameter("password");

		String message = null;
		Statement statement;
		Connection con;
		String zapytanie;
		ResultSet result;

		if (uzytkownik == null || uzytkownik.equals("")
				|| hasloUzytkownika == null || hasloUzytkownika.equals("")) {
			message = "Zla nazwa uzytkownika lub haslo";
		}
		if (message == null) {
			Long czasT = (Long) request.getSession().getAttribute(
					"czasOczekiwania");
			long dataLogowania = new Date().getTime();
			if (czasT == null || !(czasT >= dataLogowania)) {

				try {
					Class.forName("com.mysql.jdbc.Driver");
					// nawiazywanie polaczenia z baza danych , mozna jeszcze
					// dodać haslo jesli potrzeba
					con = DriverManager
							.getConnection("jdbc:mysql://localhost/stronainternetowa?"
									+ "user=root");

					zapytanie = "SELECT login from stronainternetowa.UZYTKOWNICY_strony where login = \""
							+ uzytkownik
							+ "\" and haslo = \""
							+ Szyfrowanie.hashString(hasloUzytkownika) + "\"";
					statement = con.createStatement();
					result = statement.executeQuery(zapytanie);

					if (!result.next()) {
						Integer iloscProbk = (Integer) request.getSession()
								.getAttribute("iloscProb");

						if (iloscProbk != null) {

							int ilosc = iloscProbk;
							if (ilosc >= dozwolonaIloscProbLogowan - 1) {

								long time = dataLogowania
										+ czasCzekaniaGdyNieprawneLog;
								request.getSession().setAttribute(
										"czasOczekiwania", time);
								request.getSession().setAttribute("iloscProb",
										1);
							}
							ilosc++;
							request.getSession().setAttribute("iloscProb",
									ilosc);
						} else {
							request.getSession().setAttribute("iloscProb", 1);
						}
						message = "Czesc " + uzytkownik
								+ "! Twoje logowanie jest niepoprawne";
					} else {
						request.getSession().getServletContext()
								.setAttribute("user", uzytkownik);
						request.getSession().setAttribute("userZalogowany",
								uzytkownik);
						message = "Czesc " + uzytkownik
								+ "! Zostales poprawnie zalogowany";

						int liczbalogowan = 0;
						zapytanie = "SELECT LiczbaLogowan from stronainternetowa.UZYTKOWNICY_strony where login = \""
								+ uzytkownik + "\" ";
						statement = con.createStatement();
						result = statement.executeQuery(zapytanie);

						if (result.next()) {
							liczbalogowan = result.getInt("LiczbaLogowan");
						}
						zapytanie = "UPDATE  stronainternetowa.UZYTKOWNICY_strony SET LiczbaLogowan = ? where login = \""
								+ uzytkownik + "\" ";
						PreparedStatement preparedStatement = con
								.prepareStatement(zapytanie);
						preparedStatement.setInt(1, liczbalogowan + 1);
						preparedStatement.executeUpdate();

						preparedStatement.close();

						// SpecjalneDaneDlaPlayera infoDlaPlayera = new
						// SpecjalneDaneDlaPlayera();
						// infoDlaPlayera.service(request, response);

						try {
							int N = 50, L = 100, R = 2;
							PrintWriter out = new PrintWriter(System.out);

							String port = "8004";
							int portint = (new Integer(port)).intValue();
							byte buffer[] = new byte[L];
							out.println("SERVER: started.");

							SSLContext sslContext = SSLContext
									.getInstance("SSL");
							sslContext.init(null, null, null);
							SSLContext.getDefault();
							/*****************************************************/

							out.println("ServerJsse: SSL server context created.");

							SSLServerSocketFactory factory = sslContext
									.getServerSocketFactory();

							// Get and print the list of supported enabled
							// cipher suites
							String enabled[] = factory
									.getSupportedCipherSuites();
							out.println("ServerJsse: enabled cipher suites");
//							for (int i = 0; i < enabled.length; i++)
//								out.println(enabled[i]);
//							out.flush();

							// Create an SSL session over port 8050
							SSLServerSocket ssl_server_sock = (SSLServerSocket) factory
									.createServerSocket(portint);
							SSLSocket ssl_sock;
							InputStream istr;
							OutputStream ostr;
							long t;
							int i;
							while (buffer[0] != -1) {
								for (int n = 0; n < R; n++) {
									try {
										ssl_sock = (SSLSocket) (ssl_server_sock
												.accept());
										ssl_sock.setEnabledCipherSuites(enabled);

										ssl_sock.startHandshake();
										SSLSession session = ssl_sock
												.getSession();
										out.println(" ");
										out.println("\nServerJsse: SSL connection established");
										out.println("   cipher suite:       "
												+ session.getCipherSuite());
									} catch (IOException se) {
										//System.err.println(se);
										out.println("ServerJsse: client connection refused\n"
												+ se);
										break;
									}

									if (L > 0) {
										istr = ssl_sock.getInputStream();
										ostr = ssl_sock.getOutputStream();

										t = System.currentTimeMillis();
										for (int j = 0; j < N; j++) {
											for (int len = 0;;) {
												try {
													if ((i = istr.read(buffer,
															len, L - len)) == -1) {
														out.println("ServerJsse: connection dropped by partner.");
														ssl_sock.close();
														return;
													}
												} catch (InterruptedIOException e) {
													out.println("waiting");
													continue;
												}
												if ((len += i) == L)
													break;
											}
											ostr.write(buffer, 0, L);
										}
										out.println("Messages = "
												+ N
												* 2
												+ "; Time = "
												+ (System.currentTimeMillis() - t));
									}
									ssl_sock.close();
								}
							}
							ssl_server_sock.close();
							out.println("ServerJsse: SSL connection closed.");
							out.flush();
							Thread.sleep(1000);

							// Example using plain sockets
							if (L > 0) {
								ServerSocket server_sock = new ServerSocket(
										portint);
								Socket sock = server_sock.accept();
								out.println(" ");
								out.println("\nServerJsse: Plain Socket connection accepted.");
								istr = sock.getInputStream();
								ostr = sock.getOutputStream();

								t = System.currentTimeMillis();
								for (int j = 0; j < N; j++) {
									for (int len = 0;;) {
										if ((i = istr
												.read(buffer, len, L - len)) == -1) {
											out.println("ServerJsse: connection dropped by partner.");
											return;
										}
										if ((len += i) == L)
											break;
									}
									ostr.write(buffer, 0, L);
								}
								out.println("Messages = " + N * 2 + "; Time = "
										+ (System.currentTimeMillis() - t));
								sock.close();
								server_sock.close();
								out.println("ServerJsse: Plain Socket connection closed.");
								Thread.sleep(1000);
							}
						} catch (InterruptedException | KeyManagementException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();

				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				long czekaj = czasT - dataLogowania;
				message = "Nieporpawne logowanie, musisz poczekac " + czekaj
						+ " milisekund";

			}
		} else {
			request.setAttribute("aga", "spróbuj jeszcze raz");
		}

		request.setAttribute("wynikLogowania", message);

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);
	}

	private void sendResponse(OutputStream outputStream) {
		PrintWriter pWrt = new PrintWriter(new OutputStreamWriter(outputStream));
		pWrt.print("HTTP/1.1 200 OK\r\n");
		pWrt.print("Content-Type: text/html\r\n");
		pWrt.print("\r\n");
		pWrt.print("<html>\r\n");
		pWrt.print("<body>\r\n");
		pWrt.print("Hello World!\r\n");
		pWrt.print("</body>\r\n");
		pWrt.print("</html>\r\n");
		pWrt.flush();
	}

	private static void readRequest(InputStream in) throws IOException {
		System.out.print("Żądanie: ");
		int ch = 0;
		int lastCh = 0;
		while ((ch = in.read()) >= 0 && (ch != '\n' && lastCh != '\n')) {
			System.out.print((char) ch);
			if (ch != '\r')
				lastCh = ch;
		}

		System.out.println();
	}

}
