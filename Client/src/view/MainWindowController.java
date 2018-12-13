package view;

import Controller.BoardController;
import Controller.ServerCommunicator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable{

    @FXML
    BoardDisplayer boardDisplayer;
    BoardController controller;
    @FXML
    CustomBorderPane borderPane;

    public MainWindowController(){
        controller = new BoardController();
    }


    public void HandleDoneButton()
    {
        char[][] board = this.boardDisplayer.getBoard();
        try {
            boolean solved = BoardController.isSolved(board);
            if (solved) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Stage completed");
                alert.setHeaderText("You did it");
                alert.setContentText("Well done! Stage completed!");
                alert.showAndWait().ifPresent(rs -> {
                });
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Incorrect!");
                alert.setHeaderText("Not good enough!");
                alert.setContentText("The solution is incorrect or it is not the best solution. Please keep trying");
                alert.showAndWait().ifPresent(rs -> {
                });
            }
        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Oops!");
            alert.setContentText("There was a problem checking the solution with the server. Try again later");
            alert.showAndWait().ifPresent(rs -> {
            });
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            borderPane.changeBackground("cyan.jpg");

        }catch (Exception e)
        {
        }

        boardDisplayer.setOnMouseClicked(event -> {
            double cellLength = boardDisplayer.getWidth() / boardDisplayer.getBoardWidth();
            double cellHeight = boardDisplayer.getHeight() / boardDisplayer.getBoardHeight();
            int cellx = (int) (event.getX() / cellLength);
            int celly = (int) (event.getY() / cellHeight);
            char[][] rotatedBoard = controller.handleCellClick(boardDisplayer.getBoard(), cellx, celly);
            boardDisplayer.setBoard(rotatedBoard);
        });

        char[][] data = {
            {'S','|','7'},
            {'F','-','J'},
            {'J','|','G'}
        };

        boardDisplayer.setBoard(data);
    }
}
