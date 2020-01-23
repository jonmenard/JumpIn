package game;
import java.util.EventObject;

/**
 * Is created when the model updates the view, 
 * informs the model of the state of the game.
 * @author Andrew
 *
 */
@SuppressWarnings("serial")
public class UpdateEvent extends EventObject{

	private boolean gameState;
	
	/**
	 * The constructor for the update event.
	 * @param source the object that created event.
	 * @param gameState the state of the game.
	 */
	public UpdateEvent(Board source, boolean gameState){
		super(source);
		this.gameState = gameState;
	}
	
	/**
	 * 
	 * @return the status of the game.
	 */
	public boolean getGameState(){
		return gameState;
	}
}
