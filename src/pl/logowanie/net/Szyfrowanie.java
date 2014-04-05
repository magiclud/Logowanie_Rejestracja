package pl.logowanie.net;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.servlet.ServletOutputStream;

public class Szyfrowanie {
	static String sciezkaDoKeyStore = "D:\\Programy\\eclipseEE\\wokspace\\Logowanie\\keyStore.ks";
	static String trybSzyfrowania = "OFB";
	static String hasloDoKeystora = "ala ma kota";
	static String aliasHasla = "mojAlias";

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
				| NoSuchPaddingException | BadPaddingException
				| IllegalBlockSizeException | InvalidKeyException
				| InvalidAlgorithmParameterException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return null;

	}

	public static Key pobierzKlucz(String sciezkaDoKeyStore, String aliasHasla,
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
			CipherInputStream inCipherStream = new CipherInputStream(
					inputStream, aesCipher);
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

	public static byte[] deszyfrowaniePliku(Key klucz,
			String sciezkaDoPliku) {

		IvParameterSpec ivSpec = new IvParameterSpec(new byte[16]);
		Cipher aesCipher;
		try {
			aesCipher = Cipher.getInstance("AES/CTR/NoPadding", "BC");

			aesCipher.init(Cipher.DECRYPT_MODE, klucz, ivSpec);

			File file = new File(sciezkaDoPliku);
			FileInputStream fileIn = new FileInputStream(file);
			ByteArrayOutputStream bOut = new ByteArrayOutputStream();
			CipherOutputStream inCipherStream = new CipherOutputStream(bOut,
					aesCipher);

//			int ch;
//			while ((ch = fileIn.read()) >= 0) {
//				inCipherStream.write(ch);
//			}
			byte[] outputByte = new byte[4096];
//			// copy binary contect to output stream
//			while (fileIn.read(bOut) != -1) {
//				inCipherStream.write(outputByte, 0, 4096);
//			}
//			byte[] outputByte = new byte[4096];
			//copy binary contect to output stream
			while(fileIn.read(outputByte, 0, 4096) != -1)
			{
				inCipherStream.write(outputByte, 0, 4096);
			}
			
			byte[]cipherText = bOut.toByteArray();
	//		byte[] cipherText = bOut.toByteArray();

			fileIn.close();
			inCipherStream.close();
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