package testowanie;

public class Main {
	public static void main(String[] args) throws Exception {
//		MySQLAccess dao = new MySQLAccess();
//		dao.readDataBase();
		
//		BazaDanychTestPolaczenia baza = new BazaDanychTestPolaczenia();
//		baza.wczytajBazeDanych();
		
		TestZapytaniaDoBazyDanych baza = new TestZapytaniaDoBazyDanych();
		//baza.test();
		baza.testWyszukiwaniaUzytkownikaZHaslem();;
	}
}