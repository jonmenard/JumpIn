package game;
import java.util.*;


/**
 * Past moves stores 4 digit arrays of moves than can be undone or redone
 * @author Jon
 *
 */
public class PastMoves {

	private Stack<int[]> undo;
	private Stack<int[]> redo;
	
	public PastMoves() {
		undo = new Stack<int[]>();
		redo = new Stack<int[]>();
	}
	
	/**
	 * turns the input coordinates into an int array
	 * pushes the array to the undo stack
	 * @param a,b,x,y int coordinates
	 */
	public void saveTurn(int a, int b, int x, int y) {
		int[] turn = {a,b,x,y};
		undo.push(turn);
	}
	
	/**
	 * takes to top array in stack and put it on top of redo stack
	 * removes and return the top stack
	 * @return 4 digit coordinate array
	 */
	public int[] redo() {
		undo.push(redo.peek());
		return redo.pop();
	}
	/**
	 * takes to top array in stack and put it on top of undo stack
	 * removes and return the top stack
	 * @return 4 digit coordinate array
	 */
	public int[] undo() {
		redo.push(undo.peek());
		return undo.pop();
	}
	/**
	 * 
	 * @return true if there is a coordinate array to undo
	 */
	public boolean canUndo() {
		return !undo.isEmpty();
	}
	
	/**
	 * 
	 * @return true if there is a coordinate array to redo
	 */
	public boolean canRedo() {
		return !redo.isEmpty();
	}
}
