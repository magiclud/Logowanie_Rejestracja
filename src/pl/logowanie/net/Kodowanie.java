package pl.logowanie.net;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;

public class Kodowanie {
	static String sciezkaDoKeyStore = "D:\\Programy\\eclipseEE\\wokspace\\Logowanie\\keyStore.ks";
	static String trybSzyfrowania = "OFB";
	static String hasloDoKeystora = "ala ma kota";

	String hashString(String haslo) {

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
	
	
	public static String zakoduj(String wiadomosc, String aliasHasla, String login) {
		try {
			Key key = dodajKlucz(aliasHasla);

			IvParameterSpec ivSpec = new IvParameterSpec(new byte[16]);
			Cipher aesCipher = Cipher.getInstance("AES/" + trybSzyfrowania
					+ "/NoPadding", "BC");
			aesCipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
			byte[] kryptogram = aesCipher.doFinal(wiadomosc.getBytes());
			
		
			return zapiszZakodowanaWiadomoscDoPilku(kryptogram, login);
		} catch (InvalidKeyException | InvalidAlgorithmParameterException
				| NoSuchProviderException | BadPaddingException
				| NoSuchAlgorithmException | NoSuchPaddingException
				| IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String dekoduj(String sciezkaDoPliku, String aliasHasla) {

		try {
			Key klucz = pobierzKlucz(aliasHasla);
			byte[] kryptogram = oczytZPlikuWiadomosci(sciezkaDoPliku)
					.getBytes();
			// wektor inicjujacy
			IvParameterSpec ivSpec = new IvParameterSpec(new byte[16]);
			Cipher cipher = Cipher.getInstance("AES/" + trybSzyfrowania
					+ "/NoPadding", "BC");
			cipher.init(Cipher.DECRYPT_MODE, klucz, ivSpec);
			String tescPliku = new String(cipher.doFinal(kryptogram));
			return tescPliku;
		} catch (InvalidKeyException | InvalidAlgorithmParameterException
				| NoSuchProviderException | BadPaddingException
				| NoSuchAlgorithmException | NoSuchPaddingException
				| IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	static Key pobierzKlucz(String aliasHasla) {

		try {
			KeyStore ks = KeyStore.getInstance("UBER", "BC");
			InputStream inputStream = new FileInputStream(sciezkaDoKeyStore);
			ks.load(inputStream, hasloDoKeystora.toCharArray());
			return ks.getKey(aliasHasla, hasloDoKeystora.toCharArray());
		} catch (CertificateException | IOException | UnrecoverableKeyException
				| KeyStoreException | NoSuchAlgorithmException
				| NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String zapiszZakodowanaWiadomoscDoPilku(byte[] kryptogram,
			String login) {

		String plikZWiadomoscia = "D:\\Programy\\eclipseEE\\wokspace\\Logowanie\\"
				+ login + ".txt";
		File kryptogramFile = new File(plikZWiadomoscia);
		FileOutputStream stream;
		try {
			stream = new FileOutputStream(kryptogramFile);

			stream.write(kryptogram);
			stream.close();
			// System.out.println("sceizka do kryptogramu"
			// + kryptogramFile.getAbsolutePath());
			return plikZWiadomoscia;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	static String oczytZPlikuWiadomosci(String plikZWiadomoscia) {

		FileReader file;
		BufferedReader bufferedReader;
		String wiadomosc = "";
		try {
			file = new FileReader(plikZWiadomoscia);
			bufferedReader = new BufferedReader(file);
			String wczytaneDane = bufferedReader.readLine();
			do {
				wiadomosc = wiadomosc + wczytaneDane;
				// System.out.println(wczytaneDane);
				wczytaneDane = bufferedReader.readLine();

			} while (wczytaneDane != null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return wiadomosc;
	}

	private static Key dodajKlucz(String aliasHasla) {
		try {
			KeyStore keyStore = KeyStore.getInstance("UBER", "BC");
			File file = new File(sciezkaDoKeyStore);
			InputStream inputStream = new FileInputStream(file);
			keyStore.load(inputStream, hasloDoKeystora.toCharArray());

			KeyGenerator keyGen = KeyGenerator.getInstance("ARC4", "BC");
			Key secretKey = keyGen.generateKey();
			keyStore.setKeyEntry(aliasHasla, secretKey,
					hasloDoKeystora.toCharArray(), null);
			
			zapiszKeyStore(keyStore,
					hasloDoKeystora,sciezkaDoKeyStore);
			return secretKey;
		} catch (KeyStoreException | IOException | NoSuchAlgorithmException
				| CertificateException | NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;
	}
	
	private static String zapiszKeyStore(KeyStore keyStore,
			String hasloDoKeystora,String sciezkaDoPliku) {
		try {
			File keyStoreFile = new File(sciezkaDoPliku);
			FileOutputStream fos = new FileOutputStream(keyStoreFile);
			keyStore.store(fos, hasloDoKeystora.toCharArray());
			fos.close();
			System.out.println("sceizka do keystore"
					+ keyStoreFile.getAbsolutePath());
			return keyStoreFile.getAbsolutePath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


}