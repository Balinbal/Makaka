package Controller;

import java.util.HashMap;

public class BoardController {

    private HashMap<Character, Character> rotationMapping;

    public BoardController(){
        rotationMapping = new HashMap<Character, Character>() {{
            put('F', '7');
            put('7', 'J');
            put('J', 'L');
            put('L', 'F');
            put('-', '|');
            put('|', '-');
        }};
    }

    public char[][] handleCellClick(char[][] board, int x, int y){
        board[y][x] = this.rotationMapping.get(board[y][x]);
        return board;
    }
}
