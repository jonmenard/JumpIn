package game;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 * Controller works with providing inputs to Board model
 * and initializing the view and board.
 * @author Dhyan and Jon and Andrew
 *
 */
public class Controller implements ActionListener {
	//other classes
	private Board game;
	private View view;
	private PastMoves pastMoves;
	//memory for pieces being selected
	private boolean firstPiece = false;
	private int xStart;
	private int yStart;
	private int[] foxHeadLocation = null;
	private String selectedPiece = null;
	// current level;
	private File level = null;
	//state of view
	private String currentFrame = View.MENUPANEL;
	// file chooser
	private JFileChooser fileChooser = new JFileChooser();
	
	public Controller() {
		fileChooser.setFileFilter(new FileNameExtensionFilter("*.json", "json"));
		reset("game");
	}
	/**
	 * Passes input coordinates to board model in order 
	 * to take a turn.
	 * 
	 * @param e ActionEvent which is a mouse click of a JIButton
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		int turn[] = null;
		
		if(e.getSource() instanceof JMenuItem) {			
			if(((JMenuItem) e.getSource()).getName().equals("Load")) {
				if(loadGame()) {
					if(currentFrame.equals(View.BUILDPANEL)) {
						view.displayGameBoard(game,false);
					}else {
						currentFrame = View.GAMEPANEL;
						view.switchScreen(currentFrame);
						view.displayGameBoard(game,true);
					}
				}
			} else if(((JMenuItem) e.getSource()).getName().equals("New Game")){
				reset("game");
				currentFrame = View.GAMEPANEL;
				view.switchScreen(currentFrame);
				view.displayGameBoard(game,true);
			} else if(((JMenuItem) e.getSource()).getName().equals("Rules")){
				if(view.showmessage("Rules","picture/rules.jpeg")) {
					reset("game");
					currentFrame = View.GAMEPANEL;
					view.switchScreen(currentFrame);
					view.displayGameBoard(game,true);
				}	
			} else if(((JMenuItem) e.getSource()).getName().equals("Build Rules")){	
				view.showmessage("Build Rules","picture/rules.jpeg");
			} else if(((JMenuItem) e.getSource()).getName().equals("Build Game")){
				reset("build");
				currentFrame = View.BUILDPANEL;
				view.switchScreen(currentFrame);
				view.displayGameBoard(game,false);
			} else if(((JMenuItem) e.getSource()).getName().equals("Menu")){
				currentFrame = View.MENUPANEL;
				view.switchScreen(currentFrame);
			} else if(((JMenuItem) e.getSource()).getName().equals("Confirm Build")){
				if(!game.solve() || game.getNumberRabbits() == 0 ) {
					view.showmessage("No Solution","picture/nosolution.jpeg");
				} else {
				saveGame();
				}
			} else if(((JMenuItem) e.getSource()).getName().equals("Reset")){
				reset("load");
				view.displayGameBoard(game,true);
			} else if(((JMenuItem) e.getSource()).getName().equals("Save")){
				saveGame();
			} else if(((JMenuItem) e.getSource()).getName().equals("Redo") & pastMoves.canRedo()){
				turn = pastMoves.redo();
				this.game.takeTurn(turn[0],turn[1],turn[2],turn[3]);	
			}else if(((JMenuItem) e.getSource()).getName().equals("Undo") & pastMoves.canUndo()){
				turn = pastMoves.undo();
				this.game.takeTurn(turn[2],turn[3],turn[0],turn[1]);	
			}else if(((JMenuItem) e.getSource()).getName().equals("Hint")) {
				if(isRunning()) {
					if(this.game.solution.size() == 0) {
						game.solve();
					}
					if(this.game.toString().equals(this.game.toString(this.game.solution.pop()))) {
						this.view.showHint(this.game.getGrid(), this.game.solution.pop());
					} else {
						game.solve();
						this.view.showHint(this.game.getGrid(), this.game.solution.pop());
					}
				}
			}
			firstPiece = false;
		} else if(currentFrame == View.BUILDPANEL) {
			if(e.getSource() instanceof JIButton && selectedPiece != null) {
				int x = ((JIButton) e.getSource()).x;
				int y = ((JIButton) e.getSource()).y;
				Tile tile = game.getSpot(x, y);
				
				if(!tile.isOccupied() && !(tile instanceof Hole)) {
					if(selectedPiece.equals("FH") && !(tile.getType().equals(Tile.Types.HILL))) {
						if(foxHeadLocation != null) {
							 if(!game.addFox(foxHeadLocation[0] ,foxHeadLocation[1] ,x ,y )) {
								 view.showmessage("Too Many Foxes","picture/foxhead1.jpeg");
							 }
							foxHeadLocation = null;
						} else {
							foxHeadLocation = new int[] {x, y};
						}
					} else if(selectedPiece.equals("R") && !game.addRabbit(x, y)) {
						view.showmessage("Too Many Rabbits","picture/rabbit.jpeg");
					} else if(selectedPiece.equals("M") && !game.addMushroom(x, y)) {
						view.showmessage("Too Many Mushrooms","picture/mushroom.jpeg");
					}
				}else if(selectedPiece.equals("___")){
					game.removePiece(x, y);
				}
			} else if(e.getSource() instanceof JButton) {
				selectedPiece = ((JButton) e.getSource()).getName();
			}
			view.displayGameBoard(game,false);
			if(foxHeadLocation != null){
				view.foxBuildHighLight(game ,((JIButton) e.getSource()).x ,((JIButton) e.getSource()).y);
			}
			
		}else if(e.getSource() instanceof JIButton) {
			if(isRunning()) {
				if(firstPiece) {
					int xFinal = ((JIButton) e.getSource()).x;
					int yFinal = ((JIButton) e.getSource()).y;
					if( this.game.takeTurn(xStart, yStart, xFinal, yFinal)) {
						pastMoves.saveTurn(xStart, yStart, xFinal, yFinal);
					}
					firstPiece = !firstPiece;
				} else if(game.getSpot(((JIButton) e.getSource()).x, ((JIButton) e.getSource()).y).getPiece() instanceof Moveable){
					((JIButton)e.getSource()).setBorder(new LineBorder(Color.GREEN, 4));
					
					xStart = ((JIButton) e.getSource()).x;
					yStart = ((JIButton) e.getSource()).y;
					firstPiece = !firstPiece;
				}
			}
		}
		
		if(currentFrame.equals(View.GAMEPANEL) && !isRunning()) {
			if(view.showmessage("Winner","picture/Winner.png")) {
				reset("load");
				view.displayGameBoard(game,true);
			}
		}
	}	
	
	/**
	 * loads in a new game from a selected file.
	 * @return true if the game was able to be loaded in.
	 */
	private boolean loadGame() {
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			level = fileChooser.getSelectedFile();
			reset("load");
			return true;
		}
		return false;
	}
	
	/**
	 * Saves the game using the serializer and Java file IO.
	 */
	private void saveGame() {
		if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			try(FileWriter file = new FileWriter(fileChooser.getSelectedFile() + ".json")){
				file.write(Serializer.serializeBoard(this.game));
				file.flush();
				file.close();
			} catch(IOException err){
				err.printStackTrace();
			}
		}
	}
	
	/**
	 * Returns the state of the game
	 * 
	 * @return true if game is running, false otherwise.
	 */
	public boolean isRunning() {
		return this.game.getStatus();
	}
	
	/**
	 * Resets the level back to its original state.
	 * @param string
	 */
	public void reset(String string){
		if(string.equals("load")) {
			if(level != null){
				this.game = Serializer.readBoard(level);
			}else {
				reset("game");
			}
		}else if(string.equals("game")) {
			this.game = new Board();
			this.game.setDefaultBoard();
			this.level = null;
		}else if(string.equals("build")) {
			this.game = new Board();
		}
		
		if(this.view != null) {
			this.game.addBoardListener(view);
		}else {
			this.view = new View(this.game);
			this.view.addActionListenerToButtons(this);	
			this.game.addBoardListener(view);
		}	
		this.pastMoves = new PastMoves();
	}
}
