package game;
/**
 * Represents a tile on the board, can hold a piece.
 * @author Dhyan and Jon and Sam
 *
 */
public class Tile{
	
	/**
	 *Stores what types the tile can be.
	 */
	public enum Types {FLAT, HILL};
	/**
	 * Stores the type the piece is, flat for hill.
	 */
	private Types type;
	/**
	 * Stores the current piece on the tile.
	 */
	private Piece piece;
	/**
	 * Stores whether the tile is occupied by a piece.
	 */
	private boolean occupied = false;
	
	/**
	 * Creates the tile and sets the type to flat.
	 */
	public Tile() {
		this.type = Tile.Types.FLAT;
	}
	
	/**
	 * Sets the tile to the entered type.
	 * @param type entered for the tile, hill or flat.
	 */
	public Tile(Types type) {
		this.type = type;
	}
	 
	/**
	 * Creates the tile setting it to the entered type and storing the
	 * current piece on it.
	 * @param type entered for the tile, hill or flat.
	 * @param piece the piece that is on the tile.
	 */
	public Tile(Types type, Piece piece) {
		this.type = type;
		occupy(piece);
	}
	
	/**
	 * When a piece lands on the tile it adds it to the tile
	 * and sets the status to occupied. If the tile is already occupied
	 * the piece is not replaced.
	 * @param piece the price that landed on the tile.
	 * @return true if a the piece was added to the tile, false if not.
	 */
	public boolean occupy(Piece piece) {
		if(!occupied) {
			this.piece = piece;
			occupied = true;
			return true;
		}
		return false;
	}
	
	/**
	 * If a piece is removed from the tile it
	 * removes the piece from the tiles storage and
	 * sets it to unocupied.
	 */
	public void unoccupy() {
		piece = null;
		occupied = false;
	}
	
	/**
	 * Gives the status on the occupation of the tile.
	 * @return the occupied variable.
	 */
	public boolean isOccupied() {
		return occupied;
	}
	
	/**
	 * Moves the piece from one tile to another.
	 * @param tile where the piece is being moved to.
	 */
	public void movePiece(Tile tile) {
		if(!(tile.equals(this))){
			tile.unoccupy();
			tile.occupy(this.piece);
			unoccupy();
		}
		
		
		
		
		
	}
	
	/**
	 * Gives the piece on the tile.
	 * @return the piece
	 */
	public Piece getPiece() {
		return this.piece;
	}
	
	/**
	 * Gives the type of the tile, hill or flat
	 * @return the type of the tile
	 */
	public Types getType() {
		return this.type;
	}

	public String getSymbol() {
		if(occupied) {
			return piece.getSymbol();
		} else if(type.equals(Tile.Types.HILL)) {
			return "_^_";
		}else {
			return "___";
		}
	}
}
