package Controller;

import Logic.HTTPServerCommunicator;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.HashMap;
import java.util.List;

public class BoardController {

    private HashMap<Character, Character> rotationMapping;
    HTTPServerCommunicator serverCommunicator;


    public boolean isSolved(char[][] board) throws Exception {
        String solution = this.serverCommunicator.getBoardSolution(board);
        if (solution == "")
            throw new Exception("Couldnt fetch server solution");
        return solution.equals("done");

    }

    public BoardController(){
        rotationMapping = new HashMap<Character, Character>() {{
            put('F', '7');
            put('7', 'J');
            put('J', 'L');
            put('L', 'F');
            put('-', '|');
            put('|', '-');
        }};

        serverCommunicator = new HTTPServerCommunicator("127.0.0.1", 5000);
    }

    public char[][] handleCellClick(char[][] board, int x, int y){
        if (rotationMapping.containsKey(board[y][x]))
            board[y][x] = this.rotationMapping.get(board[y][x]);
        return board;
    }
}
