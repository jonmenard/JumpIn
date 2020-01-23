package game;
import java.util.*;

/**
 * Represents the board of the game. It controls the main logic for the locations
 * of different tiles and pieces as well as the state of the game.
 *
 * @author Jon and Andrew and Sam and Dhyan
 */
public class Board {

    /**
     * The size of the length and width of the generated board.
     */
    private static int size = 5;
    /**
     * The board itself.
     * A 2d array was used in order to simulate a coordinate system that can match a grid pattern
     * This will be use full when we start adding GUI
     */
    private Tile[][] board;
    /**
     * The number of rabbit pieces entered into the game.
     * all rabbits need to be in holes in order to win the game
     */
    private int rabbits = 0;
    private int rabbitsMax = 3;
    private int foxes = 0;
    private int foxesMax = 2;
    private int mushrooms = 0;
    private int mushroomsMax = 2;
    /**
     * Stores the status of the game, true of being played.
     */
    private boolean status = true;
    private ArrayList<BoardListener> boardListeners = new ArrayList<BoardListener>();
    public Stack<Tile[][]> solution = new Stack<Tile[][]>();
    
    /**
     * The constructor for the board.
     * Adds all the tiles and their pieces.
     */
    public Board() {
        board = new Tile[size][size];
     	 this.status = true;

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if ((y == 0 || y == 4) && (x == 0 || x == 4)) {
                    board[x][y] = new Hole(); // adding holes in every corner of the board
                } else if (y == 2 && x == 2) {
                    board[x][y] = new Hole(); // adding holes in the middle of the board
                } else if ((y % 2 == 0) && (x % 2 == 0)) {
                    board[x][y] = new Tile(Tile.Types.HILL); // creating the hills on the board
                } else {
                    board[x][y] = new Tile(Tile.Types.FLAT);
                }
            }
        }
    }

    public void setDefaultBoard() {
        addFox(1,4,1,3);
        addFox(4,1,3,1);

        addRabbit(3,4);
        addRabbit(4,2);
        addRabbit(1,0);

        addMushroom(3,3);
        addMushroom(2,0);
    }

    /**
     * Adds a fox ad the given coordinates
     * @param headx
     * @param heady
     * @param tailx
     * @param taily
     * @return true if it can add the piece.
     */
    public boolean addFox(int headx, int heady, int tailx, int taily) {
    
    	//Cannot place on a diagonal spot or the same spot.
    	if(Math.abs(headx - tailx) + Math.abs(heady - taily) != 1){
    		return false;
    	}
    	    	
        if (!board[headx][heady].isOccupied() && board[headx][heady].getType() == Tile.Types.FLAT
                && !board[tailx][taily].isOccupied() && board[tailx][taily].getType() == Tile.Types.FLAT && foxes < foxesMax) {
            Fox head = new Fox(headx, heady, false);
            Fox tail = new Fox(tailx, taily, true);
            head.setPair(tail);
            tail.setPair(head);
           
            board[headx][heady].occupy(head);
            board[tailx][taily].occupy(tail);
            foxes += 1;
            
            return true;
        }
        return false;
    }
    
    /**
     * adds a rabbit at the given coordinates.
     * @param x
     * @param y
     * @return true if it can add the piece.
     */
    public boolean addRabbit(int x, int y) {
        if(!board[x][y].isOccupied() && rabbits < rabbitsMax) {
            board[x][y].occupy(new Rabbit(x, y));
            rabbits += 1;
            return true;
        }
        return false;
    }
    
    /**
     * adds a mushroom to the board at the given coordinates.
     * @param x
     * @param y
     * @return true if it can add the piece.
     */
    public boolean addMushroom(int x, int y) {
        if(!board[x][y].isOccupied() && mushrooms < mushroomsMax) {
            board[x][y].occupy(new Mushroom());
            mushrooms += 1;
            return true;
        }
        return false;
    }
    
    /**
     * removes a piece from the board at the given coordinates.
     * @param x
     * @param y
     */
    public void removePiece(int x, int y) {
    	if(board[x][y].isOccupied()) {
    		Tile spot = getSpot(x,y);
    		Piece piece = spot.getPiece();
    		if(piece instanceof Rabbit) {
    			spot.unoccupy();
    			rabbits -= 1;
    		}else if(piece instanceof Mushroom) {
    			spot.unoccupy();
    			mushrooms -= 1;
    		}else if(piece instanceof Fox) {
    			Fox fox = (Fox) piece;
    			getSpot(fox.getPair().getX(), fox.getPair().getY()).unoccupy();
    			spot.unoccupy();
    			foxes -= 1;
    		}
    	}
    }
    
    /**
     * constructs a board from the entered array of tiles.
     * @param b
     */
    public Board(Tile[][] b) {
    	this();
    	int[] fox1 = null;
    	int[] fox2 = null;
  
        for (int y = 4; y >= 0 ; y--) {
            for (int x = 0; x < size; x++) {
                if (b[x][y] instanceof Hole) {
                    this.board[x][y] = new Hole();
                    if (((Hole) b[x][y]).isFull()) {
                        ((Hole) this.board[x][y]).fillHole();
                    }
                } else {
                    this.board[x][y] = new Tile(b[x][y].getType());
                }
                if (b[x][y].getPiece() instanceof Rabbit) {
                    addRabbit(x,y);
                } else if (b[x][y].getPiece() instanceof Fox) {
                    Fox f = (Fox) b[x][y].getPiece();
                    if(!f.isTail()) {
                    		int[] fox = {x,y,f.getPair().getX(),f.getPair().getY()};
                    		if(fox1 == null) {
                    			fox1 = fox;
                    		}	
                    			fox2 = fox;
                    }
                } else if (b[x][y].getPiece() instanceof Mushroom) {
                    addMushroom(x,y);
                }   
            }
        }
        if(fox1 != null) {
        	addFox(fox1[0],fox1[1],fox1[2],fox1[3]);
        	addFox(fox2[0],fox2[1],fox2[2],fox2[3]);
        }
        this.status = checkHoles();
    }

    public void addBoardListener(BoardListener bl) {
        this.boardListeners.add(bl);
    }

    /**
     * Gets the tile at the given coordinates.
     *
     * @param x the x coordinates of the tile
     * @param y the y coordinates of the tile
     * @return the tile corresponding to the coordinates
     */
    public Tile getSpot(int x, int y) {
        return board[x][y];
    }

    /**
     * Checks the holes in the game and sees if all rabbits
     * are in a hole. If all rabbits are the game is over.
     *
     * @return true if all rabbits in a hole, false if there are unplaced rabbits.
     */
    private boolean checkHoles() {
        int rabbitsLeft = rabbits;

        for (Tile[] Tilerow : board) {
            for (Tile each : Tilerow) {
                if (each instanceof Hole) {
                    Hole hole = (Hole) each;
                    if (hole.isOccupied()) {
                        hole.fillHole();
                    	rabbitsLeft--;
                    }else {
                    	hole.unfillHole();
                    }
                }
            }
        }
        this.status = !(rabbitsLeft == 0);
        return status;
    }
    
    /**
     * returns the array of tiles.
     * @return
     */
    public Tile[][] getGrid() {
        return board;
    }

    /**
     * Takes in the coordinates of the starting piece and the
     * coordinates of the end position of the piece.
     * It determines if the starting piece is of the correct type
     * to move and if so runs the proper moving method.
     *
     * @param currentX the x coordinate of the starting tile
     * @param currentY the y coordinate of the ending tile
     * @param xFinal   the x coordinate of the ending tile
     * @param yFinal   the y coordinate of the ending tile
     */
    public boolean takeTurn(int currentX, int currentY, int xFinal, int yFinal) {
        
    	boolean b = false;
    	
        if (getSpot(currentX, currentY).getPiece() instanceof Moveable) {
            Moveable animal = (Moveable) getSpot(currentX, currentY).getPiece();

            if (animal instanceof Fox && ((Fox) animal).getPair() != null) {
             b = animal.move(board, xFinal, yFinal);
            } else if (animal instanceof Rabbit) {
             b = animal.move(board, xFinal, yFinal);
            }

            UpdateEvent e = new UpdateEvent(this, this.getStatus());
            for (BoardListener bl : boardListeners) {
                bl.reDraw(e);
            }
        }
        
        UpdateEvent e = new UpdateEvent(this, this.getStatus());
        for (BoardListener bl : boardListeners) {
            bl.reDraw(e);
        }
        checkHoles();
        return b;
    }

    /**
     * returns game status
     */
    public boolean getStatus() {
        return checkHoles();
    }
    
    /**
     * checks if the current board is the same as the 
     * entered board.
     * @param b
     * @return true if they are the same.
     */
    public boolean equals(Board b) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (this.board[i][j].getSymbol() != b.board[i][j].getSymbol()) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * returns a string output of the current board.
     */
    public String toString() {
        String s = "";
        for (int j = 4; j > -1; j--) {
            for (int i = 0; i < 5; i++) {
                s += this.board[i][j].getSymbol();
            }
            s += "\n";
        }
        return s;
    }
    
    /**
     * returns a string output of the entered array of tiles.
     * @param b
     * @return
     */
    public String toString(Tile[][] b) {
        String s = "";
        for (int j = 4; j > -1; j--) {
            for (int i = 0; i < 5; i++) {
                s += b[i][j].getSymbol();
            }
            s += "\n";
        }
        return s;
    }

    /**
     * Solves game and fill solution with the optimal moves
     * to solve the puzzle
     * solution is empty if unsolvable
     */
    public boolean solve() {
    	solution.clear();
        BoardNode b = getTree();

        while (b != null && b.getParent() != null) {
            solution.add(b.getBoard());
            b = b.getParent();   
        }

        if(b == null) {
        	return false;
        }
        return true;
    }

    /**
     * Builds decision tree and get final answer if possible.
     *
     * @return the final node in the move tree or null if puzzle is impossible
     */
    private BoardNode getTree() {
        Queue<BoardNode> queue = new LinkedList<BoardNode>();
        Set<String> seen = new HashSet<>();
        queue.add(new BoardNode(board, null));
        while (!queue.isEmpty()) {
            BoardNode b = queue.remove();
            Tile[][] base = b.getBoard();
            for (int j = 4; j > -1; j--) {
                for (int i = 0; i < 5; i++) {

                    if (base[i][j].getPiece() instanceof Moveable
                            && ((!(base[i][j].getPiece() instanceof Fox))
                            || !((Fox) base[i][j].getPiece()).isTail())
                    ) {
                        Moveable piece = (Moveable) base[i][j].getPiece();
                        List<int[]> moves = piece.getPossibleMoves(base);
                        for (int[] move : moves) {
                            Board nextState = new Board(base);
                            nextState.takeTurn(piece.getX(), piece.getY(), move[0], move[1]);
                            if (!nextState.checkHoles()) {
                                return new BoardNode(nextState.board, b);
                            } else if (!seen.contains(nextState.toString())) {
                                seen.add(nextState.toString());
                                queue.add(new BoardNode(nextState.board, b));
                            }
                        }
                    }
                }

            }
        }
        return null;
    }
    
    /**
     * returns the amount of rabbits.
     * @return
     */
    public int getNumberRabbits(){
    	return this.rabbits;
    }
}
