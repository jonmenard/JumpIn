package game;
/**
 * Represents a Mushroom object on the board
 * It implement the Piece interface
 * @author Jon and Sam
 *
 */
public class Mushroom implements Piece {

	public Mushroom() {
	}
	
	@Override
	public String getSymbol() {
		return "M";
	}
}
