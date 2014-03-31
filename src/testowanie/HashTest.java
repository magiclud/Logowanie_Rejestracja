package testowanie;

import static org.junit.Assert.*;

import java.security.MessageDigest;

import org.junit.Test;

public class HashTest {
	public static void main(String[] args) throws Exception {
		//String message = "asdasd";
		String message = "a8f5f167f44f4964e6c998dee827110c";
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] hash = md.digest(message.getBytes("UTF-8"));
		// converting byte array to Hexadecimal String
		StringBuilder sb = new StringBuilder(2 * hash.length);
		for (byte b : hash) {
			sb.append(String.format("%02x", b & 0xff));
		}
		String hashString = sb.toString();
		System.out.println(hashString);
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
