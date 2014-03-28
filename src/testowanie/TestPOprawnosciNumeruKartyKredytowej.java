package testowanie;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestPOprawnosciNumeruKartyKredytowej {
	String poprawnyNrKartyKredytowej = "[0-9]{16}";
	public static void main(String[] args) throws Exception {
		TestPOprawnosciNumeruKartyKredytowej nr = new TestPOprawnosciNumeruKartyKredytowej();
		nr.test();

	}
	
	@Test
	public void test() {
		String nrKarty= "1234567890123456";
		assertTrue(nrKarty.matches(poprawnyNrKartyKredytowej));
		String zlyNrKarty ="123";
		String zlyNrKarty2 ="d3r";
		String zlyNrKarty3 = "11111111111111111";
		assertFalse(zlyNrKarty.matches(poprawnyNrKartyKredytowej));
		assertFalse(zlyNrKarty2.matches(poprawnyNrKartyKredytowej));
		assertFalse(zlyNrKarty3.matches(poprawnyNrKartyKredytowej));
	}

}
