package testowanie;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestPoprawnosciAdresuEMail {

	String poprawnyEMail = "^(.[A-Za-z0-9\\-]*\\w)+@+([A-Za-z0-9\\-]*\\w)+(\\.[A-Za-z]*\\w)+$";

	public static void main(String[] args) throws Exception {
		TestPoprawnosciAdresuEMail mail = new TestPoprawnosciAdresuEMail();
		mail.test();

	}

	

	@Test
	public void test() {
		String mojEmail = "agnieszka.ludwikowska@op.pl";
		assertTrue(mojEmail.matches(poprawnyEMail));
		String zlyEmail ="aaaaa";
		String zlyEmail2 ="aaa_aa@.pl";
		String zlyEmail3 ="aaa.aa@gmail";
		assertFalse(zlyEmail.matches(poprawnyEMail));
		assertFalse(zlyEmail2.matches(poprawnyEMail));
		assertFalse(zlyEmail3.matches(poprawnyEMail));

	}

}
