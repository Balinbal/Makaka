package Controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.HashMap;
import java.util.List;

public class BoardController {

    private HashMap<Character, Character> rotationMapping;

    public static boolean isSolved(char[][] board) {
        ServerCommunicator s = new ServerCommunicator("127.0.0.1", 5555, 20000);
        List<String> res = s.getSolution(board);
        if (res == null)
            return false;
        return res.size() == 1;

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
    }

    public char[][] handleCellClick(char[][] board, int x, int y){
        if (rotationMapping.containsKey(board[y][x]))
            board[y][x] = this.rotationMapping.get(board[y][x]);
        boolean solved=isSolved(board);
        if (solved) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Stage completed...");
            alert.setHeaderText("You did it");
            alert.setContentText("I have a great message for you! Stage is cleared!");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
        return board;
    }
}
