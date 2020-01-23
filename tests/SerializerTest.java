package tests;
import game.*;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class SerializerTest {

	private Board board;
	
	@Before
	public void setUp() throws Exception {
		board = new Board();
		board.setDefaultBoard();
	}

	@Test
	public void testSerialize() {
		
		try(FileWriter file = new FileWriter("test.json")){
			file.write(Serializer.serializeBoard(board));
			file.flush();
			file.close();
		} catch(IOException err){
			err.printStackTrace();
		}
		
		File  file = new File("test.json");
		Board boardClone = Serializer.readBoard((File)file);
		assert board.equals(boardClone);

		
	}

}
