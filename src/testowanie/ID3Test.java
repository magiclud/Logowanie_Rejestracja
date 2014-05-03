package testowanie;

import java.io.*;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.id3.ID3v1;



public class ID3Test {
	public static void main(String[] args) throws IOException, TagException {
		File sourceFile = new File("D:\\Programy\\eclipseEE\\wokspace\\Logowanie\\muzyka\\Sting-ShapeOfMyHeart.mp3");
		MP3File mp3file = new MP3File(sourceFile);
		ID3v1 tag = mp3file.getID3v1Tag();
		System.out.println("Album; "+tag.getAlbum());
		System.out.println("Year; "+tag.getYear());
		System.out.println("Artist; "+tag.getArtist());
		System.out.println("Genre; "+tag.getGenre());
		System.out.println("AlbumTitle; "+tag.getAlbumTitle());
		System.out.println("Title; "+tag.getTitle());
		System.out.println("Comment; "+tag.getComment());
		System.out.println("Identifier; "+tag.getIdentifier());
		System.out.println("SongComment; "+tag.getSongComment());
		System.out.println("SongGenre; "+tag.getSongGenre());
		
String sciezka = "D:\\Programy\\eclipseEE\\wokspace\\Logowanie\\muzyka";
	File katalog = new File(sciezka); 
	odwiedzRekursywnieKatalogi(katalog);
	String tytul = "Shape Of My Heart";
	znajdzPlikiPasujaceDoTytulu(katalog, tytul);
}

private static void odwiedzRekursywnieKatalogi(File katalog) throws IOException, TagException {
    File pliki[] = katalog.listFiles();
    for (int i = 0; i < pliki.length; i++) {
        if(pliki[i].isDirectory()) odwiedzRekursywnieKatalogi(new File(katalog.getName() + File.separatorChar + pliki[i].getName()));
        else if(pliki[i].isFile()) {System.out.println("Plik"+i+": "+pliki[i].getName());
        String lokalizacja = pliki[i].getAbsolutePath();
        System.out.println("Sciezka "+ lokalizacja);
        MP3File mp3file = new MP3File(lokalizacja);
		ID3v1 tag = mp3file.getID3v1Tag();
		System.out.println("Dane pliku: \n-artysta: "+ tag.getArtist());
        
        }

    }        
    
}

private static void znajdzPlikiPasujaceDoTytulu(File katalog, String tytul) throws IOException,
TagException {
File pliki[] = katalog.listFiles();
for (int i = 0; i < pliki.length; i++) {
if (pliki[i].isDirectory())
	znajdzPlikiPasujaceDoTytulu(new File(katalog.getName()
			+ File.separatorChar + pliki[i].getName()), tytul);
else if (pliki[i].isFile()) {
	String lokalizacja = pliki[i].getAbsolutePath();
	//System.out.println("Sciezka " + lokalizacja);
	MP3File mp3file = new MP3File(lokalizacja);
	ID3v1 tag = mp3file.getID3v1Tag();
	if(tag.getTitle().equals(tytul)){
		//pasujacePlikiMuzyczne.add(pliki[i]);
		System.out.println("!!!!!!!!Plik" + i + ": " + pliki[i].getName()+ "!!!!!!!!!!!");
	}
}

}
}
}