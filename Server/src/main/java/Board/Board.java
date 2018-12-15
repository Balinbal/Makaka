package Board;
import java.util.HashMap;

public abstract class Board<T> {

    protected String id;
    protected T board;
    protected HashMap<String, HashMap<Character, String>> permittedSteps;

    // C-TOR

    Board() {
        this.id = null;
        this.board = null;
        this.setPermitted();
    }

    Board(T board) {
        this.setPermitted();
        setBoard(board);
        setId(board.toString());
    }

    Board(String board) {
        this.setPermitted();
        setBoard((toBoard(board)));
        setId(board);
    }

    // Setters and Getters
    abstract void setPermitted();

    public T getBoard() {
        return board;
    }

    public abstract void setBoard(T board);

    String getId() {
        return id;
    }

    void setId(String board) {
        this.id = String.valueOf(board.hashCode());
    }

    void setId(Board<T> board) {
        this.id = board.getId();
    }

    // Returns whether a move can be done currentPosition position x, to position y
    abstract boolean isValidMove(Position from, Position to);

    // Converts string to board
    abstract T toBoard(String strBoard);

    // Validators
    // Checks if the board is valid
    abstract boolean isValidBoard();
    // Checks if the position relative to the board is valid
    abstract boolean isValidPosition(Position position);
}
