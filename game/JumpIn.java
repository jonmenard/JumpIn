package game;
/**
 * JumpIn is the main game that instantiates the controller
 * to run the game. It's pretty empty right now but will be 
 * filled in with more stuff regarding game settings etc.
 * @author Dhyan
 *
 */
public class JumpIn {
	public static void main(String[] args) {
		Controller controller = new Controller();
		while(controller.isRunning()) {}
	}

}
