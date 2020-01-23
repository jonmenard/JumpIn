package game;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Serializer {
	
	@SuppressWarnings("unchecked")
	/**
	 * Serializes the board into the format of a JSON.
	 * @param board
	 * @return the board in the format of a JSON.
	 */
	public static String serializeBoard(Board board){
		
		JSONArray pieceList = new JSONArray();
		
		for(int i=0; i<5; i++){
			for(int j=0; j<5; j++){
				
				if(board.getSpot(i, j).isOccupied()){
					Piece piece = board.getSpot(i, j).getPiece();
					JSONObject pieceInfo = new JSONObject();
					if(piece instanceof Fox){
						if(!((Fox)piece).isTail()){
							pieceInfo.put("type", "fox");
							pieceInfo.put("x",i);
							pieceInfo.put("y",j);
							pieceInfo.put("tailX", ((Fox)piece).getPair().getX());
							pieceInfo.put("tailY", ((Fox)piece).getPair().getY());
							pieceList.add(pieceInfo);
						}
					}else{
						if(piece instanceof Rabbit){
							pieceInfo.put("type", "rabbit");
						}else{
							pieceInfo.put("type", "mushroom");
						}
						
						pieceInfo.put("x",i);
						pieceInfo.put("y",j);
						pieceList.add(pieceInfo);
					}
				}
			}
		}
		return pieceList.toJSONString();		
	}
	
	/**
	 * Read from a JSON file and parses it into a new board object.
	 * @param fileName the selected file.
	 * @return the new board.
	 */
	public static Board readBoard(File fileName){
		JSONParser parse = new JSONParser();
		Board board = new Board();
		
		try(FileReader read = new FileReader(fileName)){
			
			JSONArray pieceList = (JSONArray) parse.parse(read);
			
			for(Object index : pieceList){
				JSONObject piece = (JSONObject) index;
				if(piece.get("type").equals("fox")){
					board.addFox(((Long)piece.get("x")).intValue(), ((Long)piece.get("y")).intValue(), 
								 ((Long)piece.get("tailX")).intValue(), ((Long)piece.get("tailY")).intValue());
				}else if(piece.get("type").equals("rabbit")){
					board.addRabbit(((Long)piece.get("x")).intValue(),((Long)piece.get("y")).intValue());
				}else{
					board.addMushroom(((Long)piece.get("x")).intValue(), ((Long)piece.get("y")).intValue());
				}
			}
			
			read.close();
			
		}catch(IOException e){
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return board;
	}
}
