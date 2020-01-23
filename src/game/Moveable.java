package game;
import java.util.List;

/**
 * Represents a moveable piece on the board.
 * Has a general move method
 * 
 * @author Dhyan
 *
 */
public abstract class Moveable implements Piece {
	private int x;
	private int y;
	abstract boolean move(Tile[][] board, int finishX, int finishY);
	public abstract List<int[]> getPossibleMoves(Tile[][] board);

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
}
