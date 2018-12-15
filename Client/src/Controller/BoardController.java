package Controller;

import Logic.HTTPServerCommunicator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import view.BoardDisplayer;

import java.util.HashMap;
import java.util.List;

public class BoardController {

    BoardDisplayer boardDisplayer;
    private int level;
    private HashMap<Character, Character> rotationMapping;
    HTTPServerCommunicator serverCommunicator;


    public boolean isSolved(char[][] board) throws Exception {
        String solution = this.serverCommunicator.getBoardSolution(board);
        if (solution == "")
            throw new Exception("Couldnt fetch server solution");
        return solution.equals("done");

    }
    public String getSolution(char[][] board)
    {
         return this.serverCommunicator.getBoardSolution(board);
    }
    public BoardController(BoardDisplayer boardDisplayer){
        this.level = 1;
        this.rotationMapping = new HashMap<Character, Character>() {{
            put('F', '7');
            put('7', 'J');
            put('J', 'L');
            put('L', 'F');
            put('-', '|');
            put('|', '-');
        }};

        this.serverCommunicator = new HTTPServerCommunicator("127.0.0.1", 5000);
        this.boardDisplayer = boardDisplayer;
    }

    public void setDisplayer(BoardDisplayer boardDisplayer) {
        this.boardDisplayer = boardDisplayer;
    }

    public void incrementLevel() {
        this.level +=1;
        drawBoard();
    }

    public void drawBoard() {
        char[][] newBoard = this.serverCommunicator.getBoard(this.level);
        boardDisplayer.setBoard(newBoard);
        boardDisplayer.redraw();
    }
    public char[][] handleCellClick(char[][] board, int x, int y){
        if (rotationMapping.containsKey(board[y][x]))
            board[y][x] = this.rotationMapping.get(board[y][x]);
        return board;
    }
}
