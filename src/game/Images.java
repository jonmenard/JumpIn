package game;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Images {
	public HashMap<String,Image> imgMap = new HashMap<String,Image>();
	
	public Images() {
	    InputStream input = (ResourceLoader.load("/textfiles/pictures.txt"));
	    InputStreamReader isReader = new InputStreamReader(input);
	      //Creating a BufferedReader object
	    BufferedReader reader = new BufferedReader(isReader);
	    StringBuffer sb = new StringBuffer();
	    String str;
	    try {
	    	while((str = reader.readLine())!= null){
			     sb.append(str);
			  }
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	    for(String line: sb.toString().split(" ")) {
	    	Image i;
	    	try {
	    		i = ImageIO.read(ResourceLoader.load("/" + line));
	    		imgMap.put(line, i);
	    	} catch (IOException e) {
			e.printStackTrace();
	    	}	
	    }
	}
		  
	public Image getImage(String s) {
		return imgMap.get(s);
	}
	
}
