package testowanie;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import pl.logowanie.net.Kodowanie;

public class KodowanieTest {

	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet result = null;
	String login = "magda";
	String hashHasla = hashString("magda1");
	String aliasHasla = login + hashHasla;

	@Test
	public void test() {

		String sciezkaDoPlik = Kodowanie.zakoduj(hashHasla, aliasHasla, login);
		System.out.println("sciezkaDopliku to : " + sciezkaDoPlik);

		String hasloUzytkownika = Kodowanie.dekoduj(sciezkaDoPlik, aliasHasla);

		System.out.println("haslouzytkownika " + hasloUzytkownika);

		assertEquals(hashHasla, hasloUzytkownika);
	}

	private String hashString(String haslo) {

		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");

			byte[] hash = md.digest(haslo.getBytes("UTF-8"));
			// converting byte array to Hexadecimal String
			StringBuilder sb = new StringBuilder(2 * hash.length);
			for (byte b : hash) {
				sb.append(String.format("%02x", b & 0xff));
			}
			String hashString = sb.toString();
			return hashString;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
}
