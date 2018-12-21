package Controller;

import Logic.HTTPServerCommunicator;
import Logic.IServerCommunicator;
import Model.ScoreRepresentation;
import javafx.scene.control.Alert;
import view.BoardDisplayer;

import java.util.HashMap;
import java.util.List;

public class BoardController {

    BoardDisplayer boardDisplayer;
    private int level;
    private HashMap<Character, Character> rotationMapping;
    IServerCommunicator serverCommunicator;


    public void setServer(String url) {
        this.serverCommunicator = new HTTPServerCommunicator(url);
    }

    public String getServer() {return this.serverCommunicator.getCurrentURL();}

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

        this.serverCommunicator = new HTTPServerCommunicator("");
        this.boardDisplayer = boardDisplayer;
    }

    public void setDisplayer(BoardDisplayer boardDisplayer) {
        this.boardDisplayer = boardDisplayer;
        this.serverCommunicator = new HTTPServerCommunicator(boardDisplayer.getServerUrl());
    }

    public void incrementLevel() {
        this.level +=1;
        drawBoard();
    }

    public void drawBoard() {
        char[][] newBoard = this.serverCommunicator.getBoard(this.level);
        if (newBoard.length == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setHeaderText("Failed communicating with server");
            alert.setContentText("Picking default level");
            alert.showAndWait().ifPresent(rs -> {
            });
            newBoard = new char[][] {{'S','J','G'}, {'L','|','J'}, {'F','F','F'}};
        }

        boardDisplayer.setBoard(newBoard);
        boardDisplayer.redraw();
    }
    public char[][] handleCellClick(char[][] board, int x, int y){
        if (rotationMapping.containsKey(board[y][x]))
            board[y][x] = this.rotationMapping.get(board[y][x]);
        return board;
    }

    public void markFinished(String user, int steps, int time) {
        serverCommunicator.markScore(user, this.level, steps, time);
    }

    public List<ScoreRepresentation> getTopForLevel()
    {
        return serverCommunicator.getTopForLevel(this.level);
    }

    public int getLevel()
    {
        return  this.level;
    }

}
