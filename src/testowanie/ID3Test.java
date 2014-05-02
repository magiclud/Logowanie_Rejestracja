package testowanie;

import java.io.*;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.id3.ID3v1;

public class ID3Test {
	public static void main(String[] args) throws IOException, TagException {
		File sourceFile = new File("D:\\Programy\\eclipseEE\\wokspace\\Logowanie\\Adele-RollingInTheDeep.mp3");
		MP3File mp3file = new MP3File(sourceFile);
		ID3v1 tag = mp3file.getID3v1Tag();
		System.out.println(tag.getAlbum());
		tag.getYear();
		tag.getArtist();
		tag.getGenre();
		System.out.println(tag.getAlbumTitle());
		System.out.println(tag.getTitle());
	}
}
