package testowanie;

import java.io.*;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.id3.ID3v1;

public class ID3Test {
	public static void main(String[] args) throws IOException, TagException {
		File sourceFile = new File("D:\\Programy\\eclipseEE\\wokspace\\Logowanie\\Sting-ShapeOfMyHeart.mp3");
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
		
}
}
