package game;
/**
 *
 * Represents a single node in the decision tree for the solver
 *
 * @author Sam
 */

public class BoardNode {
    private BoardNode parent;
    private Tile[][] board;

    public BoardNode(Tile[][] b, BoardNode p){
        parent = p;
        board = b;
    }

    public BoardNode getParent() {
        return parent;
    }

    public Tile[][] getBoard() {
        return board;
    }

    public void setBoard(Tile[][] board) {
        this.board = board;
    }

    public String toString() {
        return new Board(board).toString();
    }
}
