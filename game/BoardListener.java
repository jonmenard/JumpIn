package game;
/**
 * The board listener interface is used
 * for any class that needs to be updated
 * by the board model.
 * @author Andrew
 *
 */
public interface BoardListener  {
	/**
	 * Used to notify the GUI that it
	 * needs to redraw the board.
	 * @param event a board event holding the game state.
	 */
	public void reDraw(UpdateEvent event);
}
