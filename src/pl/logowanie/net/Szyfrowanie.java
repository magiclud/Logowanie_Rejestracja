package pl.logowanie.net;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import java.security.Security;
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

public class Szyfrowanie {
	//static String sciezkaDoKeyStore = "D:\\Programy\\eclipseEE\\wokspace\\Logowanie\\keyStore.ks";
	static String trybSzyfrowania = "OFB";
	static String hasloDoKeystora = "ala ma kota";
	static String aliasHasla = "mojAlias";

	static String hashString(String haslo) {

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
			outputStream.close();

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

	public static Key pobierzKlucz(String sciezkaDoKeyStore, String aliasHasla) {

		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		try {
			KeyStore ks = KeyStore.getInstance("UBER", "BC");
			InputStream inputStream = new FileInputStream(sciezkaDoKeyStore);
			ks.load(inputStream, hasloDoKeystora.toCharArray());
			inputStream.close();
			// inputStream.flush();
			Key klucz = ks.getKey(aliasHasla, hasloDoKeystora.toCharArray());
			inputStream.close();
			return klucz;
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

	public static byte[] zaszyfrowaniePliku(Key klucz,
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
	
	static Key dodajKlucz(String sciezkaDoKeyStore, String aliasHasla) {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		try {
			KeyStore keyStore = KeyStore.getInstance("UBER", "BC");
			InputStream inputStream = null;
			keyStore.load(inputStream, hasloDoKeystora.toCharArray());
			KeyGenerator keyGen = KeyGenerator.getInstance("ARC4", "BC");
			Key secretKey = keyGen.generateKey();
			keyStore.setKeyEntry(aliasHasla, secretKey,
					hasloDoKeystora.toCharArray(), null);
			// zapisz keyStore
			FileOutputStream fos = new FileOutputStream(sciezkaDoKeyStore);
			keyStore.store(fos, hasloDoKeystora.toCharArray());
			fos.close();
			
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
	
	public static byte[] zaszyfrowanieWiadomosci(Key klucz, String wiadomosc) {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
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
	static byte[] dekodujWiadomosc(byte[] kryptogram, Key klucz) {

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