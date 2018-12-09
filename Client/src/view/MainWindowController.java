package view;

import Controller.BoardController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
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
