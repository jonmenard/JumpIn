package game;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * View is basically a JFrame with other GUI components.
 * It listens to updates in the Board model.
 * @author Andrew, Dhyan, Jon, Sam and Karanvir
 */
@SuppressWarnings("serial")
public class View extends JFrame implements BoardListener{
    private JIButton[][] gamebuttons;
    private JIButton[][] buildbuttons;
    private ArrayList<JMenuBar> menus;
    private HashMap<String,JPanel> panels;
    private Color color = (new Color(240,234,214));
    public static final String GAMEPANEL = "Game";
    public static final String MENUPANEL = "Menu";
    public static final String BUILDPANEL = "Build";
    private Images imgHolder = new Images();
       
    /**
     *
	 * Sets up the view
	 * 
	 * @param board the board model is used to fill out the grid
	 */
    public View(Board board) {
    	
    	super("Jump In");
    	super.setResizable(false);
        this.setMinimumSize(new Dimension(600,605));
        this.setMaximumSize(new Dimension(600,605));
       
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //creating the gamePlay JPanel
        
        Tile[][] grid = board.getGrid();
        int x = grid.length;
        int y = grid[0].length;
        gamebuttons = new JIButton[x][y];
        buildbuttons = new JIButton[x][y];
        JPanel gamePlay = new JPanel(new BorderLayout());
        JPanel gameGrid = createBoardPanel(board,gamebuttons,color,117,109);
        		
        // creating a menubar for in the game
        
        JMenuBar gameBar= new JMenuBar();
        JMenuItem mainmenu = new JMenuItem("Menu");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem reset = new JMenuItem("Reset");
        JMenuItem undo = new JMenuItem("Undo");
        JMenuItem redo = new JMenuItem("Redo");
        JMenuItem hint = new JMenuItem("Hint");
       
        gameBar.add(mainmenu);
        gameBar.add(save);
        gameBar.add(reset);
        gameBar.add(undo);
        gameBar.add(redo);
        gameBar.add(hint);
        
        gamePlay.add(gameBar,BorderLayout.PAGE_START);
        gamePlay.add(gameGrid, BorderLayout.CENTER);
        
        //Creating the main menu JPanel
        
        JPanel loadScreen = new JPanel(new BorderLayout());
        loadScreen.setBackground(color);
        
        JLabel imageScreen = new JLabel( getImageIcon("picture/loadscreen.jpeg", 600, 605));
        imageScreen.setLayout(new BorderLayout());
           
        JMenuBar loadBar = new JMenuBar();
        JMenuItem start = new JMenuItem("New Game");
        JMenuItem build = new JMenuItem("Build Game");
        JMenuItem load = new JMenuItem("Load");
        JMenuItem rules = new JMenuItem("Rules");
      
        loadBar.add(start);
        loadBar.add(build);
        loadBar.add(load);
        loadBar.add(rules); 
        loadBar.setBorder(new EmptyBorder(0,0,0,0));
       
        imageScreen.add(loadBar, BorderLayout.PAGE_START);
        loadScreen.add(imageScreen, BorderLayout.PAGE_START);
       
       // creating the build game pane
        
       JPanel gameBuilder = new JPanel(new BorderLayout());
       JMenuBar buildBar = new JMenuBar();
    
       JPanel buildGrid = createBoardPanel(board,buildbuttons,color,119,88);
      
       JPanel buttonHolder = new JPanel(new GridLayout(1,4));
          
       JButton mushroom = new JButton(getImageIcon("picture/mushroom.jpeg", 118, 102));
       mushroom.setName("M");
       mushroom.setBorder(new EmptyBorder(0, 0, 0, 0));
       JButton rabbit = new JButton(getImageIcon("picture/rabbit.jpeg", 118, 102));
       rabbit.setName("R");
       rabbit.setBorder(new EmptyBorder(0, 0, 0, 0));
       JButton fox = new JButton(getImageIcon("picture/foxhead1.jpeg", 117, 102));
       fox.setName("FH");
       fox.setBorder(new EmptyBorder(0, 0, 0, 0));
       JButton foxt = new JButton(getImageIcon("picture/foxtail3.jpeg", 118, 102));
       foxt.setName("FH");
       foxt.setBorder(new EmptyBorder(0, 0, 0, 0));
       JButton empty = new JButton(getImageIcon("picture/flattile.jpeg", 118, 102));
       empty.setName("___");
       empty.setBorder(new EmptyBorder(0, 0, 0, 0));
      
       buttonHolder.add(rabbit);
       buttonHolder.add(mushroom);
       buttonHolder.add(fox);
       buttonHolder.add(foxt);
       buttonHolder.add(empty);
       buttonHolder.setBorder((new LineBorder(color ,3)));
       
       gameBuilder.add(buttonHolder, BorderLayout.CENTER);
       gameBuilder.add(buildGrid, BorderLayout.PAGE_END); 
      
       JMenuItem menu1 = new JMenuItem("Menu");
       JMenuItem confirm = new JMenuItem("Confirm Build");
       JMenuItem load1 = new JMenuItem("Load");
       JMenuItem rules1 = new JMenuItem("Build Rules");
       
       buildBar.add(menu1);
       buildBar.add(confirm);
       buildBar.add(load1);
       buildBar.add(rules1);
       buildBar.setBorder(new EmptyBorder(0,0,0,0));
   
       
       gameBuilder.add(buildBar,BorderLayout.PAGE_START);
       menus = new ArrayList<JMenuBar>();
       menus.add(loadBar);
       menus.add(buildBar);
       menus.add(gameBar);
       

       JPanel main = new JPanel(new CardLayout());
       main.add(loadScreen, MENUPANEL);
       main.add(gamePlay, GAMEPANEL);
       main.add(gameBuilder, BUILDPANEL);
       
       ((CardLayout)(main.getLayout())).show(main, "Menu");
       
       
       panels = new HashMap<String,JPanel>();
       panels.put("loadScreen",loadScreen);
       panels.put("gamePlay",gamePlay);
       panels.put("gameBuilder",gameBuilder);
       panels.put("butttonHolder",buttonHolder);
       panels.put("main",main);
       
        add(main);
        pack();
        setVisible(true);
        setSize(600, 600);    
    }
    
    /**
	 * Adds action listener to buttons
	 * 
	 * @param a action listener that listens for mouse click of
	 * a button and its coordinates
	 */
    public void addActionListenerToButtons(ActionListener a) {
    	for(int row = 0; row < gamebuttons.length; row++) {
    		for(int col = 0; col < gamebuttons[row].length; col++) {
    			gamebuttons[row][col].addActionListener(a);
    			buildbuttons[row][col].addActionListener(a);
    		}
    	}
    	
    	for(JMenuBar menu : menus) {
    		for(Component each: menu.getComponents()) {
    			JMenuItem button = ((JMenuItem) each);
    	       	button.setName(button.getText());
    	       	button.setBackground(color);
    	        button.setFont(new Font("Comic Sans MS", Font.BOLD, 17));
    	        button.addActionListener(a);
    	        button.addMouseListener(new java.awt.event.MouseAdapter() {
	                public void mouseEntered(java.awt.event.MouseEvent evt) {
	                	button.setForeground(new Color(218,145,30));
	                }
	
	                public void mouseExited(java.awt.event.MouseEvent evt) {
	                	button.setForeground(Color.BLACK);
	                }
	            });
    		}
    	}
    	
    	for(Component each:  panels.get("butttonHolder").getComponents()) {
        	((JButton) each).addActionListener(a);	
        } 
    }
    /**
	 * Fills up the grid based on board model 
	 * 
	 * @param board model that is used to update the grid
	 */
    public void displayGameBoard(Board board, boolean state) {
    	
    	JIButton[][] buttons;
		String panelName;
		int x;
		int y;
    	
    	if(state) {
    		buttons = gamebuttons;
    		panelName = "gamePlay";
    		x = 118;
    	    y = 109;
    	}else {
    		buttons = buildbuttons;
    		panelName = "gameBuilder";
    		x = 119;
    		y = 88;
    	}
    		
        for(int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                String symbol = board.getSpot(i, j).getSymbol();
                buttons[i][j].setIcon(getIcon(symbol,x,y));
                buttons[i][j].setBorder(new LineBorder(Color.black, 0));
                if(board.getSpot(i, j).getType().equals(Tile.Types.HILL)) {
                	Border border1 = BorderFactory.createRaisedBevelBorder();
                	Border border2 = BorderFactory.createLoweredSoftBevelBorder();
                	buttons[i][j].setBorder(BorderFactory.createCompoundBorder(border2,border1));
                }
            }
        }
        JPanel play = panels.get(panelName);
        play.setVisible(true);
        play.revalidate();
        play.repaint();
    }
    
    /**
	 * Creates an Icon based on symbol of tile and
	 * loads appropriate file.
	 * 
	 * @param symbol text rep of a tile
	 * @return IconImage 
	 */
    public Icon getIcon(String symbol, int x, int y) {
    	String filename = "picture/";
        switch(symbol) {
        case "_^_":
        	filename += "hill.jpeg";
        	break;
        case "___":
        	filename += "flattile.jpeg";
        	break;
        case "R":
        	filename += "rabbit.jpeg";
        	break;
         case "FH1":
        	filename += "foxhead1.jpeg";
        	break;
        case "FH2":
        	filename += "foxhead2.jpeg";
        	break;
        case "FH3":
        	filename += "foxhead3.jpeg";
        	break;
        case "FH4":
        	filename += "foxhead4.jpeg";
        	break;
        case "FT1":
        	filename += "foxtail1.jpeg";
        	break;
        case "FT2":
        	filename += "foxtail2.jpeg";
        	break;
        case "FT3":
        	filename += "foxtail3.jpeg";
        	break;
        case "FT4":
        	filename += "foxtail4.jpeg";
        	break;
        case "[]":
        	filename += "hole.jpeg";
        	break;
        case "[R]":
        	filename += "holeR.jpeg";
        	break;
        case "M": 
        	filename += "mushroom.jpeg";
        	break;
        };
        //resize
        Image image = imgHolder.getImage(filename);
	    Image newImage = image.getScaledInstance(x,y, java.awt.Image.SCALE_SMOOTH);
	    return new ImageIcon(newImage);
    }
    
    /**
     * Return the button corresponding to the entered coordinates.
     * @param x
     * @param y
     * @return the selected button.
     */
    public JIButton getButton(int x, int y) {
    	return gamebuttons[x][y];
    }
    
    /**
	 * Redraws the grid given an update to Board model.
	 * 
	 * @param event UpdateEvent with updated Board
	 */
	@Override
	public void reDraw(UpdateEvent event) {
		displayGameBoard(((Board) event.getSource()),true);
	}
	
	/**
	 * Highlights the buttons that the solver suggests for the next move.
	 * @param b the array of tiles.
	 * @param next the array of the next move set from the solver.
	 */
	public void showHint(Tile[][] b, Tile[][] next) {
		for(int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
            	if(!b[i][j].getSymbol().equals(next[i][j].getSymbol())) {
            		gamebuttons[i][j].setBorder(new LineBorder(Color.YELLOW, 4));
            	}
            }
		}
	}
	
	/**
	 * Used to switch to the screen corresponding to the
	 * entered string.
	 * @param screen 
	 */
	public void switchScreen(String screen){
		((CardLayout)(panels.get("main").getLayout())).show(panels.get("main"), screen);
	}
	
	public String getFileName(String message, String i){
	
		return (String) JOptionPane.showInputDialog(null,message,"File name",JOptionPane.INFORMATION_MESSAGE,getImageIcon(i,117,109),null,"");
	}
	
	public boolean showmessage(String f, String i){
		
		UIManager.put("OptionPane.background", color);
	    UIManager.put("Panel.background", color);
	    UIManager.put("Button.background", color);
	    UIManager.put("OptionPane.messageFont", new Font("Comic Sans MS", Font.BOLD, 15)); 
	    UIManager.put("OptionPane.setSize", new Dimension(600,600));
	    
	    String buttonText;
	    if(f.equals("Rules")) {
	    	buttonText = "New Game";
	    }else {
	    	buttonText = "Ok";
	    }
	    
	    InputStream input = (ResourceLoader.load("/textfiles/" + f +".txt"));
	    
	    InputStreamReader isReader = new InputStreamReader(input);
	      //Creating a BufferedReader object
	      BufferedReader reader = new BufferedReader(isReader);
	      StringBuffer sb = new StringBuffer();
	      String str;
	      try {
			while((str = reader.readLine())!= null){
			     sb.append(str + "\n");
			  }
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	    
		int n = JOptionPane.showOptionDialog(this, sb.toString(), 
			       f, JOptionPane.YES_OPTION, JOptionPane.INFORMATION_MESSAGE, 
			       getImageIcon(i, 117, 109), new Object[] {buttonText}, JOptionPane.YES_OPTION);
		if(n == JOptionPane.YES_OPTION) {
			return true;
		}
		return false;
	}
	
	public ImageIcon getImageIcon(String s, int x, int y) {
		Image i  = imgHolder.getImage(s);
		Image newImage = i.getScaledInstance(x,y, java.awt.Image.SCALE_SMOOTH);
		return(new ImageIcon(newImage));
	}
	
	/**
	 * Helper method to create the panel that stores the games buttons.
	 * @param board
	 * @param buttons
	 * @param color
	 * @param x the width for the buttons.
	 * @param y the height for the buttons.
	 * @return the created panel for the buttons.
	 */
	private JPanel createBoardPanel(Board board, JIButton[][] buttons, Color color, int x, int y) {
		int length = board.getGrid().length;
		JPanel panel = new JPanel(new GridLayout(length, length));
		for(int i = length-1; i >= 0; i--) {
            for (int j = 0; j < length; j++) {
                JIButton button = new JIButton(j, i);
                buttons[j][i] = button;
                String symbol = board.getSpot(j, i).getSymbol();
                buttons[j][i].setBackground(color);
                buttons[j][i].setIcon(getIcon(symbol,x,y));
                buttons[j][i].setPreferredSize(new Dimension(x, y));
                if ((i % 2 == 0) && (j % 2 == 0)) {
                	Border border1 = BorderFactory.createRaisedBevelBorder();
                	Border border2 = BorderFactory.createLoweredSoftBevelBorder();
                	buttons[j][i].setBorder(BorderFactory.createCompoundBorder(border2,border1));	
                }
                panel.add(button);
            }
		}
		return panel;
	}
	
	/**
	 * Highlights the spots that the other part of the fox can be entered into
	 * for the level builder.
	 * @param board
	 * @param x the location of the first spot.
	 * @param y the location of the second spot.
	 */
	public void foxBuildHighLight(Board board, int x , int y){
		int diff = 1;
		for(int i=0; i<2; i++){
			if((x + diff) <= 4 && (x + diff) >= 0){
				if(board.getSpot(x + diff, y).getType() == Tile.Types.FLAT && !board.getSpot(x + diff, y).isOccupied()){
					buildbuttons[x+ diff][y].setBorder(new LineBorder(Color.YELLOW, 4));
				}
			}
			if((y + diff) <= 4 && (y + diff) >= 0){
				if(board.getSpot(x, y+ diff).getType() == Tile.Types.FLAT && !board.getSpot(x, y + diff).isOccupied()){
					buildbuttons[x][y+ diff].setBorder(new LineBorder(Color.YELLOW, 4));
				}
			}
			diff = diff*-1;
		}
	}
}
