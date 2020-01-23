package game;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the fox piece on the board.
 * It implements the piece interface. 
 * 
 * @author Jon and Dhyan and Sam and Andrew
 
 *
 */
public class Fox extends Moveable {
	
	/**
	 * The pair for a fox to allow a fox piece to take up two spaces.
	 */
	private Fox foxPair;
	private int imageR;
	private boolean isTail;
	/**
	 * Constructor of the fox.
	 */
	public Fox() {
	}
	
	public Fox(int x, int y, boolean isTail) {
		this.setX(x);
		this.setY(y);
		this.isTail = isTail;
	}
	
	/**
	 * Sets the entered fox piece to the tail of the fox.
	 * @param pair a fox object that will be set as the tail.
	 */
	public void setPair(Fox pair) {
		this.foxPair = pair; 
		if(!isTail) {
			setImageR(1,0);
		}
	}
	
	public void setImageR(int action, int r) {
		if(action == 0) {
			this.imageR = r;
		}else {
			int x = this.getX()-foxPair.getX();
			int y = this.getY()-foxPair.getY();
			
			if(x > 0 && y == 0) {
				imageR = 3;
				foxPair.setImageR(0,1);
				
			}else if(x < 0 && y == 0) {
				imageR = 1;
				foxPair.setImageR(0,3);
				
			}else if(y < 0 && x == 0) {
				imageR = 4;
				foxPair.setImageR(0,2);
			}else if(y > 0 && x == 0) {
				imageR = 2;
				foxPair.setImageR(0,4);
			}
		}
	}
	
	/**
	 * Returns the tail of the fox.
	 * @return the tail fox object
	 */
	public Fox getPair() {
		return this.foxPair;
	}
	
	@Override
	/**
	 * Returns symbol for the fox.
	 */
	public String getSymbol() {
		
		if(isTail) {
			return "FT" + imageR;
		}else {
			return "FH" + imageR;
		}
	}

	public boolean isTail() {
		return isTail;
	}
	
	@Override
	/**
	 * Performs the sliding operation for the fox and its tail.
	 * 
	 * @param board the 2D array of tiles that represents the board
	 * @param finishX the x coordinate of the ending tile
	 * @param finishY the y coordinate of the ending tile
	 */
	public boolean move(Tile[][] board, int finishX, int finishY) {
		
		Tile start = board[this.getX()][this.getY()];
		Tile tail = board[foxPair.getX()][foxPair.getY()];
		Tile endSpot = null;
		Tile tailSpot = null;
		
		//This is fox an outlier case that can occur when one of the parts of the fox
		//is selected to move so the other part would be out of bounds.
		if(finishX > 4 || finishX < 0){
			finishX = finishX - (finishX - this.getX()) / Math.abs((finishX - this.getX()));
		}else if(finishY > 4 || finishY < 0){
			finishY = finishY - (finishY - this.getX()) / Math.abs((finishY - this.getX()));
		}
		
		int xdiff = (finishX - this.getX());
		int ydiff = (finishY - this.getY());
		int xcurr = this.getX();
		int ycurr = this.getY();
		int tailX = foxPair.getX();
		int tailY = foxPair.getY();
		boolean spotFound = false;
		
		if((xdiff != 0) && (ydiff != 0)){
			return false;
		}
		
		if(xdiff != 0 && (this.getX() - foxPair.getX() !=0)){
			int xinc = xdiff / Math.abs(xdiff);
			//This is here to simplify the movement of the fox a bit.
			//If the selected moving piece is not ahead in the movement
			//this will make it so that the piece that is ahead is now the
			//selected moving piece.
			if(xinc != (this.getX() - this.foxPair.getX())){
				return this.foxPair.move(board, finishX + xinc, finishY);
			}
			while(xdiff != 0){
				//Go until you reach an occupied piece or run out of space to move.
				if(board[xcurr + xinc][ycurr].isOccupied()){
					break;
				}else if(!board[xcurr + xinc][ycurr].isOccupied()){
					xcurr = xcurr + xinc;
					tailX = tailX + xinc;
					tailSpot = board[tailX][tailY];
					endSpot = board[xcurr][ycurr];
					spotFound = true;
				}
				xdiff = xdiff - xinc;
			}
			
		}else if(ydiff != 0 && (this.getY() - foxPair.getY() !=0)){
			int yinc = ydiff / Math.abs(ydiff);
			if(yinc != (this.getY() - this.foxPair.getY())){
				return this.foxPair.move(board, finishX, finishY + yinc);
			}
			while(ydiff != 0){
				if(board[xcurr][ycurr + yinc].isOccupied()){
					break;
				}else if(!board[xcurr][ycurr + yinc].isOccupied()){
					ycurr = ycurr + yinc;
					tailY = tailY + yinc;
					tailSpot = board[tailX][tailY];
					endSpot = board[xcurr][ycurr];
					spotFound = true;
				}
				ydiff = ydiff - yinc;
			}
		}
		
		if(spotFound){
			start.movePiece(endSpot);
			tail.movePiece(tailSpot);
			
			this.setX(xcurr);
			this.setY(ycurr);
			this.foxPair.setX(tailX);
			this.foxPair.setY(tailY);
			return true;
		} else {
			return false;
		}
	}
	@Override
	/**
	 * param board is a pointer the the board object
	 * returns a list of all possible moves for a piece.
	 */
	public List<int[]> getPossibleMoves(Tile[][] board) {
		List<int[]> moves = new ArrayList<>();
		int xincrement = 0;
		int yincrement = 0;
		int x;
		int y;
		
		//checks which axis the fox tail is on so it can move in that direction
		if (foxPair.getX() == getX()){
			yincrement = 1;
			x = getX();
			y = 0;
		} else {
			xincrement = 1;
			x = 0;
			y = getY();
		}
		
		// checks which moves can be made by sliding in the same axis as the fox head and tail are on
		for (int i = 0; i < 5; i++) {
			int[] move = {x,y};
			int currentx = this.getX();
			int currenty = this.getY();
				if(this.move(board, x, y)) {
					this.move(board, currentx, currenty);
					moves.add(move);
				}
			x += xincrement;
			y += yincrement;
		}
		return moves;
	}
}
