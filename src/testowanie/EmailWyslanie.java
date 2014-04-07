package testowanie;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.servlet.RequestDispatcher;

import org.junit.Test;

public class EmailWyslanie {

	RequestDispatcher requestDispatcher;
	
	// Adres email osby która wysyła maila
	private static String FROM = "agnieszkalud@op.pl";
	// Hasło do konta osoby która wysyła maila
	private static String PASSWORD = "senga1";
	// Adres email osoby do której wysyłany jest mail
//	private static String TO = "agnieszkalud@gmail.com";
	// Temat wiadomości
	private static String temat = "Nowe haslo - hash ";
	// Treść wiadomości
	private static String wiadomosc;

	public static void main(String[] args) {
		try {
			new EmailWyslanie().sendTest();
			System.out.println("Wiadomość wysłana");
		} catch (MessagingException | NoSuchAlgorithmException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void sendTest() throws MessagingException, NoSuchAlgorithmException, UnsupportedEncodingException {

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.poczta.onet.pl");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		// Get the default Session object.
		Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(FROM, PASSWORD);
					}
				  });

		// Tworzenie wiadomości email
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress("agnieszkalud@op.pl"));
		message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("agnieszkalud@op.pl"));//adres dostawcy//TODO
		message.setSubject(temat);
		message.setText(getWiadomosc());

		// wysłanie wiadomości
		Transport.send(message);
		//transport.close();
		System.out.println("Wyslano e-maila");
	}
	private String getWiadomosc() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		long dataLogowania = new Date().getTime();
		String message = String.valueOf(dataLogowania);
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] hash = md.digest(message.getBytes("UTF-8"));
		// converting byte array to Hexadecimal String
		StringBuilder sb = new StringBuilder(2 * hash.length);
		for (byte b : hash) {
			sb.append(String.format("%02x", b & 0xff));
		}
		String hashString = sb.toString();
		System.out.println(hashString);
		return hashString;
		
	}
}
