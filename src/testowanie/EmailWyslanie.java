package testowanie;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.servlet.RequestDispatcher;

import org.junit.Test;

public class EmailWyslanie {

	RequestDispatcher requestDispatcher;
	private static String HOST = "smtp.poczta.onet.pl";
	private static int PORT = 465;
	// Adres email osby która wysyła maila
	private static String FROM = "agnieszkalud@op.pl";
	// Hasło do konta osoby która wysyła maila
	private static String PASSWORD = "senga1";
	// Adres email osoby do której wysyłany jest mail
	private static String TO = "agnieszkalud@gmail.com";
	// Temat wiadomości
	private static String SUBJECT = "Hello World";
	// Treść wiadomości
	private static String CONTENT = "To mój pierwszy mail wysłany za pomocą JavaMailAPI.";

	public static void main(String[] args) {
		try {
			new EmailWyslanie().sendTest();
			System.out.println("Wiadomość wysłana");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void sendTest() throws MessagingException {

		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtps");
		props.put("mail.smtps.auth", "true");

		// Get the default Session object.
		Session session = Session.getDefaultInstance(props);

		// Tworzenie wiadomości email
		MimeMessage message = new MimeMessage(session);

		message.setSubject(SUBJECT);
		message.setContent(CONTENT, "text/plain; charset=ISO-8859-2");
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(TO));

		Transport transport;

		transport = session.getTransport();

		transport.connect(HOST, PORT, FROM, PASSWORD);

		// wysłanie wiadomości
		transport.sendMessage(message,
				message.getRecipients(Message.RecipientType.TO));
		transport.close();
	}
}
