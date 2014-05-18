package testowanie;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
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
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

import org.farng.mp3.TagException;

import pl.logowanie.net.WyszukanieUtworu;

public class SprawdzenieCzyPoprawnieZakodowano {

	private static final long serialVersionUID = 1L;
	static String sciezkaDoKeyStore = "D:\\Programy\\eclipseEE\\wokspace\\Logowanie\\keystoreSerwera.ks";
	static String aliasHasla;
	static String hasloDoKeystoreaSerwera;

	public static void main(String[] args) {

		String tytul = "Rolling in the Deep";
		String uzytkownik = "Karolina";
		hasloDoKeystoreaSerwera = "zamek";

		aliasHasla = uzytkownik;

		System.out.println("Pobrano z sesji tytul muzyki: " + tytul);

		String sciezkaDoPliku = "D:\\Pobieranie\\music(1).mp3";
		String sciezkaWyjsciowa = "D:\\Pobieranie\\music(1)deszyf.mp3";
		byte[] zaszyfrowanyPlik = przygotujPlikDoPrzeslania(sciezkaDoPliku);
		byte[] zdekodowanyTekst = deszyfrowanieWiadomosci(pobierzKlucz(
				sciezkaDoKeyStore, new String(aliasHasla), new String(
						hasloDoKeystoreaSerwera)), zaszyfrowanyPlik, sciezkaWyjsciowa);
		// byte[] zaszyfrowanyPlik= Szyfrowanie.zaszyfrowaniePliku(klucz,
		// sciezkaDoPliku);
		AudioInputStream outSteream;
		try {
			outSteream = AudioSystem
					.getAudioInputStream(new ByteArrayInputStream(
							zdekodowanyTekst));
			Clip clip = AudioSystem.getClip();
			clip.open(outSteream);
			clip.start();
		} catch (UnsupportedAudioFileException | LineUnavailableException
				| IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JFrame a = new JFrame();
		a.setVisible(true);

	}
	private static byte[] przygotujPlikDoPrzeslania(String sciezkaDoPliku) {
		try {
			File file = new File(sciezkaDoPliku);
			FileInputStream fileIn = new FileInputStream(file);
			ByteArrayOutputStream bOut = new ByteArrayOutputStream();
			OutputStream outStr = new DataOutputStream(bOut);
			byte[] outputByte = new byte[4096];

			while (fileIn.read(outputByte, 0, 4096) != -1) {
				outStr.write(outputByte, 0, 4096);
			}
			byte[] cipherText = bOut.toByteArray();

			fileIn.close();
			outStr.close();
			return cipherText;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
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

	
	public static byte[] zaszyfrowanieWiadomosci(Key klucz,
			String sciezkaWejsciowa) {
		IvParameterSpec ivSpec = new IvParameterSpec(new byte[16]);
		Cipher aesCipher;
		try {
			aesCipher = Cipher.getInstance("AES/CTR/NoPadding", "BC");

			aesCipher.init(Cipher.ENCRYPT_MODE, klucz, ivSpec);

			File inFile = new File(sciezkaWejsciowa);
			FileInputStream inputStream = new FileInputStream(inFile);
			ByteArrayOutputStream bOut = new ByteArrayOutputStream();
			CipherInputStream inCipherStream = new CipherInputStream(inputStream,
					aesCipher);
			int ch;
			while ((ch = inCipherStream.read()) >= 0) {
				bOut.write(ch);
			}

			byte[] cipherText = bOut.toByteArray();
		
			inCipherStream.close();
			inputStream.close();
			
			aesCipher.doFinal(cipherText);
			return cipherText;
		} catch (NoSuchAlgorithmException | NoSuchProviderException
				| NoSuchPaddingException | InvalidKeyException
				| InvalidAlgorithmParameterException | IOException e) {
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

	public static byte[] deszyfrowanieWiadomosci(Key klucz,
			byte[] zaszyfrowanyPlik, String sciezkaWyjsciowa) {
		IvParameterSpec ivSpec = new IvParameterSpec(new byte[16]);
		Cipher aesCipher;
		try {
			aesCipher = Cipher.getInstance("AES/CTR/NoPadding", "BC");
			aesCipher.init(Cipher.DECRYPT_MODE, klucz, ivSpec);

			File outFile = new File(sciezkaWyjsciowa);		
			FileOutputStream outputStream = new FileOutputStream(outFile);
			ByteArrayOutputStream bOut = new ByteArrayOutputStream();
			CipherOutputStream inCipherStream = new CipherOutputStream(bOut,
					aesCipher);

				inCipherStream.write(zaszyfrowanyPlik);	

			byte[] cipherText = bOut.toByteArray();
			outputStream.write(zaszyfrowanyPlik);
			
			inCipherStream.close();
			bOut.close();
			aesCipher.doFinal(cipherText);
		
			return cipherText;
		} catch (NoSuchAlgorithmException | NoSuchProviderException
				| NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
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
}
