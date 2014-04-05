package testowanie;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;

import org.junit.Test;

import pl.logowanie.net.Szyfrowanie;

public class Odszyfrowywanie {
	
	String filename="plikTestowy.txt";
	String hasloDoKeystora = "ala ma kota";
	String aliasHasla = "mojAlias";
	String sciezkaDoKeyStore = "D:\\eclipse\\Semestr4\\AESplikMuzyczny\\keyStore.ks";
	String zaszyfrowanyPlik = "D:\\Programy\\eclipseEE\\wokspace\\Logowanie\\ZaszyfrowanyPlik.wav";
	
	@Test
	public void test() {
	
		
		
		byte[] zdekodowanyTekst = deszyfrowanieWiadomosci(Szyfrowanie.pobierzKlucz(
				sciezkaDoKeyStore, new String(aliasHasla), new String(
						hasloDoKeystora)), zaszyfrowanyPlik);

	}
	public byte[] deszyfrowanieWiadomosci(Key klucz, String plik){
		File file = new File(plik);
		FileInputStream fileIn = new FileInputStream(file);
			IvParameterSpec ivSpec = new IvParameterSpec(new byte[16]);
			Cipher aesCipher;
			try {
				aesCipher = Cipher.getInstance("AES/CTR/NoPadding", "BC");
				aesCipher.init(Cipher.DECRYPT_MODE, klucz, ivSpec);

				
				byte[] outputByte = new byte[4096];
				//copy binary contect to output stream
				while(fileIn.read(outputByte, 0, 4096) != -1)
				{
					out.write(outputByte, 0, 4096);
				}
				fileIn.close();
				out.flush();
				out.close();
				
				
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

}
