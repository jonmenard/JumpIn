package game;
/**
 * Represents a hole tile on the board
 * @author Jon and Dhyan and Sam
 *
 */
public class Hole extends Tile {
	
	/**
	 * Stores whether the hole has been filled or not.
	 */
	private boolean hasRabbit;
	
	/**
	 * Creates the hole and assigns it to a hill type.
	 */
	public Hole() {
		super(Tile.Types.HILL);
	}
	
	/**
	 * Tells whether the hole is filled or not filled.
	 * @return the hasRabbit variable.
	 */
	public boolean isFull() {
		return this.hasRabbit;	
	}
	
	/**
	 * Sets the hole to filled with a rabbit piece.
	 */
	public void fillHole() {
		hasRabbit = true;
	}
	
	public void unfillHole() {
		hasRabbit = false;
	}
	@Override
	/**
	 * Returns symbol for the hole piece.
	 */
	public String getSymbol() {
		if(hasRabbit) {
			return "[R]";
		}else {
			return "[]";
		}
	}
	
}
