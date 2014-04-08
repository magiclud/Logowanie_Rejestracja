package testowanie;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;

import org.junit.Test;

public class SzyfrowanieKartyKredytowej {

	static String hasloDoKeystora = "ala ma kota";
	private PreparedStatement preparedStatement = null;
	private Statement statement = null;
	private ResultSet result = null;

	@Test
	public void test() {
		Connection conn;

		// zaladowanie sterownika do bazy danych
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// nawiazywanie polaczenia z baza danych , mozna jeszcze dodać haslo
			// jesli potrzeba
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost/stronainternetowa?"
							+ "user=root");

			String uzytkownik = "Wieskaa";
			String aliasHasla = uzytkownik;
			String nrKartyKredytowej = "1234432112344321";
			String sciezkaDoKeyStore = "D:\\Programy\\eclipseEE\\wokspace\\Logowanie\\keyStore.ks";

			byte[] zaszyfrowanaWiadomosv = zaszyfrowanieWiadomosci(
					dodajKlucz(sciezkaDoKeyStore, aliasHasla),
					nrKartyKredytowej);
			preparedStatement = conn
					.prepareStatement("insert into  stronainternetowa.UZYTKOWNICY values (default, ?, ?, ?, ?)");
			// "login, e-mail, haslo, katakredyt");
			// parameters start with 1
			preparedStatement.setString(1, uzytkownik);
			preparedStatement.setString(2, "kunegunda@o2.pl");
			preparedStatement.setString(3, "TestHaslo");
			preparedStatement.setBytes(4, zaszyfrowanaWiadomosv);
			preparedStatement.execute();

			// dodajKlucz(sciezkaDoKeyStore, aliasHasla);
			String zapytanie = "SELECT KARTA_KREDYTOWA from stronainternetowa.UZYTKOWNICY where login = \""
					+ uzytkownik + "\"";
			statement = conn.createStatement();
			result = statement.executeQuery(zapytanie);
			if (result.next()) {
				System.out
						.println("Jestem w ifie, czyli isnieje taki uzytkownik w bazie");
				byte[] numer = result.getBytes("KARTA_KREDYTOWA");
				Key klucz = pobierzKlucz(sciezkaDoKeyStore, aliasHasla);
				System.out.println("klucz " + klucz);
				byte[] odszyfrowanyTekst = dekoduj(numer, klucz);

				System.out.println("KARTA_KREDYTOWA: " + odszyfrowanyTekst);
			} else {
				System.out.println("nie ma takiego uzytkownika w bazie");
				// String uzytkownik = result.getString("login");
				// System.out.println("User: " + uzytkownik);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static Key dodajKlucz(String sciezkaDoKeyStore, String aliasHasla) {
		try {
			System.out.println("aliash Hasla w dodaj klucz: " + aliasHasla);
			KeyStore keyStore = KeyStore.getInstance("UBER", "BC");
			InputStream inputStream = new FileInputStream(sciezkaDoKeyStore);

			keyStore.load(inputStream, hasloDoKeystora.toCharArray());

			KeyGenerator keyGen = KeyGenerator.getInstance("ARC4", "BC");
			Key secretKey = keyGen.generateKey();
			keyStore.setKeyEntry(aliasHasla, secretKey,
					hasloDoKeystora.toCharArray(), null);
			// ProtectionParameter protParam = new KeyStore.PasswordProtection(
			// hasloDoKeystora.toCharArray());
			// keyStore.setEntry(aliasHasla, entry, protParam);
			// inputStream.close();
			return secretKey;
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

	static Key pobierzKlucz(String sciezkaDoKeyStore, String aliasHasla) {
		System.out.println("aliash Hasla w pobierz klucz: " + aliasHasla);
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

	public static byte[] zaszyfrowanieWiadomosci(Key klucz, String wiadomosc) {
		IvParameterSpec ivSpec = new IvParameterSpec(new byte[16]);
		Cipher aesCipher;
		try {
			aesCipher = Cipher.getInstance("AES/CTR/NoPadding", "BC");
			aesCipher.init(Cipher.ENCRYPT_MODE, klucz, ivSpec);
			return aesCipher.doFinal(wiadomosc.getBytes());
		} catch (NoSuchAlgorithmException | NoSuchProviderException
				| NoSuchPaddingException | InvalidKeyException
				| InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	static byte[] dekoduj(byte[] kryptogram, Key klucz) {

		IvParameterSpec ivSpec = new IvParameterSpec(new byte[16]);
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("AES/CTR/NoPadding", "BC");
			cipher.init(Cipher.DECRYPT_MODE, klucz, ivSpec);
			return cipher.doFinal(kryptogram);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return kryptogram;
	}
}
