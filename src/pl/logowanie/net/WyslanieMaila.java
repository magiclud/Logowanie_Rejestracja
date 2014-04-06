package pl.logowanie.net;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class WyslanieMaila
 */
public class WyslanieMaila extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public WyslanieMaila() {
		super();
	}

	RequestDispatcher requestDispatcher;
	private static String HOST = "smtp.poczta.onet.pl";
	private static int PORT = 465;
	// Adres email osby która wysyła maila
	private static String FROM = "agnieszkalud@op.pl";
	// Hasło do konta osoby która wysyła maila
	private static String PASSWORD = "senga1";
	// Adres email osoby do której wysyłany jest mail
	private static String TO;
	// Temat wiadomości
	private static String SUBJECT = "Hello World";
	// Treść wiadomości
	private static String CONTENT = "To mój pierwszy mail wysłany za pomocą JavaMailAPI.";

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Jestem w  doPost method w WyslanieMaila");

		String email = request.getParameter("email");
		this.TO = email;
		System.out.println("email: " + email);
		
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtps");
		props.put("mail.smtps.auth", "true");

		// Get the default Session object.
		Session session = Session.getDefaultInstance(props);

		// Tworzenie wiadomości email
		MimeMessage message = new MimeMessage(session);
		try {
			message.setSubject(SUBJECT);
			message.setContent(CONTENT, "text/plain; charset=ISO-8859-2");
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					TO));

			Transport transport;

			transport = session.getTransport();

			transport.connect(HOST, PORT, FROM, PASSWORD);

			// wysłanie wiadomości
			transport.sendMessage(message,
					message.getRecipients(Message.RecipientType.TO));
			transport.close();

			request.setAttribute("mail",
					"Na dany adres e-mail zostal wyslana wiadomosc.");

			requestDispatcher = request.getRequestDispatcher("index.jsp");
			requestDispatcher.forward(request, response);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	// SimpleEmail email = new
	// SimpleEmail("smtp.gmail.com",465,"myUsername","myPassword");
	// email.setHostName("mail.myserver.com");
	// email.addTo("jdoe@somewhere.org", "John Doe");
	// email.setFrom("me@apache.org", "Me");
	// email.setSubject("Test message");
	// email.setMsg("This is a simple test of commons-email");
	// email.send();

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}
