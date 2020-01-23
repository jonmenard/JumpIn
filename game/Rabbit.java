package game;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a rabbit piece on the board.
 * It implements the Piece interface
 * @author Jon and Dhyan and Sam
 *
 */
public class Rabbit extends Moveable {
	
	/**
	 * The constructor for the rabbit.
	 */
	public Rabbit() {
	}
	
	public Rabbit(int x, int y) {
		this.setX(x);
		this.setY(y);
	}
	
	/**
	 * Checks if the tile the rabbit is entering in is a hole.
	 * If it is a hole, the rabbit enters it.
	 * 
	 * @param tile the tile object.
	 */
	private void updateHoles(Tile tile) {
		if(tile instanceof Hole) {
			Hole hole = (Hole) tile;
			hole.fillHole();
		}	
	}
	
	/**
	 * @Override
	 * Performs the jumping operation for the rabbit.
	 *
	 * @param finishX the x coordinates of the selected ending tile
	 * @param finishY the y coordinate of the selected ending tile
	 */
	public boolean move(Tile[][] board, int finishX, int finishY) {
		
		Tile start = board[this.getX()][this.getY()];
		Tile endSpot = null;
		
		int xdiff = (finishX - this.getX());
		int ydiff = (finishY - this.getY());
		int xcurr = this.getX();
		int ycurr = this.getY();
		boolean jumpedOverOne = false;
		boolean spotFound = false;
		
		//Can't move diagonally 
		if(xdiff != 0 && ydiff != 0){
			return false;
		}
		// Goes through the board on one of the selected axis.
		int xinc = 0;
		int yinc = 0;
		//Selecting the proper axis to move on.
		if(xdiff != 0){
			xinc = xdiff / Math.abs(xdiff);
		}else if(ydiff != 0){
			yinc = ydiff / Math.abs(ydiff);
		}
		
		while(xdiff != 0 || ydiff !=0){
			xcurr = xcurr + xinc;
			ycurr = ycurr + yinc;
			
			if(board[xcurr][ycurr].isOccupied()){
				jumpedOverOne = true;
			}else if(!board[xcurr][ycurr].isOccupied()){
				endSpot = board[xcurr][ycurr];
				spotFound = true;
				break;
			}
			xdiff = xdiff - xinc;
			ydiff = ydiff - yinc;
		}
		
		if(spotFound && xcurr == finishX && ycurr == finishY && jumpedOverOne == true){
			start.movePiece(endSpot);
			updateHoles(endSpot);
			this.setX(finishX);
			this.setY(finishY);
			return true;
		} else {
			return false;
		}
		
	}
	
	@Override
	/**
	 *Returns symbol for the rabbit piece.
	 */
	public String getSymbol() {
		return "R";
	}


	@Override
	/**
	 * param board is a pointer the the board object
	 * returns the list of all possible moves for a rabbit
	 */
	public List<int[]> getPossibleMoves(Tile[][] board) {
	
		List<int[]> possibleMoves = new ArrayList<>();
		
		for(int xdiff = -1; xdiff < 2; xdiff++) {
			for(int ydiff = -1; ydiff < 2; ydiff++) {
				if(Math.abs(xdiff - ydiff) == 1) {
					int[] move = getMove(board, xdiff,ydiff);
					if(move != null) {
						possibleMoves.add(move);
					}
				}
			}
		}
		return possibleMoves;
	}

	private int[] getMove(Tile[][] board, int xdiff, int ydiff) {
		Board b = new Board(board);
		int originalx = getX();
		int originaly = getY();
		int x = originalx +xdiff;
		int y = originaly +ydiff;
		
		while(y >= 0 && y< 5 && x >= 0 && x < 5){
			if(this.move(b.getGrid(), x, y)) {
				this.move(b.getGrid(), originalx, originaly);
				int[] move = {x,y};
				return move;
			}
			x += xdiff;
			y += ydiff;
		}
		return null;
	}
}
