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
    BorderPane borderPane;

    public MainWindowController(){
        controller = new BoardController();

    }

    public void changeBackground(String imagePath) throws FileNotFoundException {

        Image bgImage = new Image(new FileInputStream(imagePath));
        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
        Background background = new Background(new BackgroundImage(bgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize));
        borderPane.setBackground(background);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            changeBackground("./resources/bg/cyan.jpg");

        }catch (Exception e)
        {
            int a =2;
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
