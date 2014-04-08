package testowanie;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletOutputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

import org.junit.Test;

import pl.logowanie.net.PobieraniePliku;
import pl.logowanie.net.Szyfrowanie;

public class PrzesylaniePliku {

	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet result = null;

	@Test
	public void test() {

		String sciezkaDoPliku = polaczZbazaIZnajdzPlik();

		String filename = "plikTestowy.txt";
		String aliasHasla = "mojAlias";
		String sciezkaDoKeyStore = "D:\\eclipse\\Semestr4\\AESplikMuzyczny\\keyStore.ks";

		byte[] zaszyfrowanyPlik = Szyfrowanie.zaszyfrowanieWiadomosci(
				Szyfrowanie.pobierzKlucz(sciezkaDoKeyStore, new String(
						aliasHasla)), sciezkaDoPliku);
		String sciezkaWyjsciowa = "D:\\Programy\\eclipseEE\\wokspace\\Logowanie\\wyjscie.wav";
		byte[] zdekodowanyTekst = Szyfrowanie.deszyfrowanieWiadomosci(
				Szyfrowanie.pobierzKlucz(sciezkaDoKeyStore, new String(
						aliasHasla)), zaszyfrowanyPlik, sciezkaWyjsciowa);

		AudioInputStream outSteream;
		try {
			outSteream = AudioSystem
					.getAudioInputStream(new ByteArrayInputStream(
							zdekodowanyTekst));
			Clip clip = AudioSystem.getClip();
			clip.open(outSteream);
			clip.start();

		} catch (UnsupportedAudioFileException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JFrame a = new JFrame();
		a.setVisible(true);

	}

	private String polaczZbazaIZnajdzPlik() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// nawiazywanie polaczenia z baza danych , mozna jeszcze dodać haslo
			// jesli potrzeba
			connect = DriverManager
					.getConnection("jdbc:mysql://localhost/stronainternetowa?"
							+ "user=root");

			String nazwaPliku = "\"soft\"";
			String lokalizacjaPliku = null;

			String zapytanie = "SELECT sciezka from stronainternetowa.PLIKI where nazwa = "
					+ nazwaPliku;
			statement = connect.createStatement();
			result = statement.executeQuery(zapytanie);
			if (result.next()) {
				System.out.println("Jestem w ifie, czyli isnieje taki plik");
				lokalizacjaPliku = result.getString("sciezka");
				System.out.println("Plik jest tutaj: " + lokalizacjaPliku);
			} else {
				System.out.println("nie ma takiego pliku w bazie");
			}
			return lokalizacjaPliku;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
