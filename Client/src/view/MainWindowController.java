package view;

import Controller.BoardController;
import Controller.ServerCommunicator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
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
    MediaPlayer backgroundMusicPlayer;

    public MainWindowController(){
        controller = new BoardController(null);
        backgroundMusicPlayer = null;
    }


    public void HandleDoneButton()
    {
        borderPane.changeTheme("red");
        boardDisplayer.setTheme("red");
        changeBackgroundMusic();

        char[][] board = this.boardDisplayer.getBoard();
        try {
            boolean solved = controller.isSolved(board);
            if (solved) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Stage completed");
                alert.setHeaderText("You did it");
                alert.setContentText("Well done! Stage completed!");
                alert.showAndWait().ifPresent(rs -> {
                });
                this.controller.incrementLevel();
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

    public void HandleSolveButton()
    {
        char[][] board = this.boardDisplayer.getBoard();
        String solutionFromServer = controller.getSolution(board);
        String[] solution = solutionFromServer.split("\\|");
        for(int i=0; i<solution.length; ++i)
        {
            if (solution[i].equals("done"))
            {
                break;
            }
            String[] moveSpecificPipe = solution[i].split(",");
            int y = Integer.parseInt(moveSpecificPipe[0]);
            int x = Integer.parseInt(moveSpecificPipe[1]);
            int numberOfRotations = Integer.parseInt(moveSpecificPipe[2]);
            for(int j=0; j<numberOfRotations; ++j)
            {
                board = controller.handleCellClick(board,x,y);
                System.out.println("rotate "+Integer.toString(y)+","+Integer.toString(x));
                boardDisplayer.setBoard(board);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }



    }
    public void changeBackgroundMusic()
    {
        if (this.backgroundMusicPlayer != null) {
            this.backgroundMusicPlayer.stop();
        }
        String musicFileName = borderPane.getBackgroundMusicFileFolder().replace("{theme}", borderPane.getTheme())
                + borderPane.getBackgroundMusicFileName();
        Media song = new Media(new File(musicFileName).toURI().toString());
        this.backgroundMusicPlayer= new MediaPlayer(song);
        this.backgroundMusicPlayer.play();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            borderPane.changeBackground("bg.jpg");

        }catch (Exception e)
        {
        }

        changeBackgroundMusic();
        boardDisplayer.setOnMouseClicked(event -> {
            double cellLength = boardDisplayer.getWidth() / boardDisplayer.getBoardWidth();
            double cellHeight = boardDisplayer.getHeight() / boardDisplayer.getBoardHeight();
            int cellx = (int) (event.getX() / cellLength);
            int celly = (int) (event.getY() / cellHeight);
            char[][] rotatedBoard = controller.handleCellClick(boardDisplayer.getBoard(), cellx, celly);
            boardDisplayer.setBoard(rotatedBoard);
        });

        this.controller.setDisplayer(boardDisplayer);
        this.controller.drawBoard();
//        char[][] data = {
//            {'S','|','7'},
//            {'F','-','J'},
//            {'J','|','G'}
//        };
//
//        boardDisplayer.setBoard(data);
    }
}
