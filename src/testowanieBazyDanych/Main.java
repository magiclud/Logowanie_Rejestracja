package testowanieBazyDanych;

public class Main {
	public static void main(String[] args) throws Exception {
//		MySQLAccess dao = new MySQLAccess();
//		dao.readDataBase();
		
		BazaDanychTestPolaczenia baza = new BazaDanychTestPolaczenia();
		baza.wczytajBazeDanych();
	}
}