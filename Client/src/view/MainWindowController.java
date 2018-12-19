package view;

import Controller.BoardController;
import Controller.ServerCommunicator;
import Model.ScoreRepresentation;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
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

    public static String CurrentUser;

    @FXML
    BoardDisplayer boardDisplayer;
    BoardController controller;
    @FXML
    CustomBorderPane borderPane;
    MediaPlayer backgroundMusicPlayer;
    @FXML
    Label numberOfSteps;
    @FXML
    Label timerLabel;
    int steps;
    int time;

    public MainWindowController(){
        controller = new BoardController(null);
        backgroundMusicPlayer = null;
        steps = 0;
        time = 0;
    }

    public void HandleThemeMario()
    {
        borderPane.changeTheme("mario");
        boardDisplayer.setTheme("mario");
        changeBackgroundMusic();
    }

    public void HandleThemeSilver()
    {
        borderPane.changeTheme("silver");
        boardDisplayer.setTheme("silver");
        changeBackgroundMusic();
    }

    public void HandleThemeDefault()
    {
        borderPane.changeTheme("default");
        boardDisplayer.setTheme("default");
        changeBackgroundMusic();
    }
    public void HandleDoneButton()
    {
        char[][] board = this.boardDisplayer.getBoard();
        try {
            boolean solved = controller.isSolved(board);
            if (solved) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Stage completed");
                alert.setHeaderText("You did it, " + MainWindowController.CurrentUser+"!");
                alert.setContentText("Well done! Stage completed!");
                alert.showAndWait().ifPresent(rs -> {
                });
                this.controller.markFinished(MainWindowController.CurrentUser, this.steps, this.time);
                this.controller.incrementLevel();
                this.initializeNewLevel();


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

    public void SolveWithAnimation()
    {
        char[][] board = this.boardDisplayer.getBoard();
        String solutionFromServer = controller.getSolution(board);
        System.out.println(solutionFromServer);
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
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void HandleSolveButton()
    {
        Runnable runnable =  () -> {this.SolveWithAnimation(); };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public void HandleTopTenCurrentLevel()
    {
        StringBuilder builder = new StringBuilder();
        List<ScoreRepresentation> scores = controller.getTopForLevel();
        for (int i=0; i<scores.size(); ++i)
        {
            builder.append(i+1);
            builder.append(") ");
            builder.append("User: ");
            builder.append(scores.get(i).getUser());
            builder.append(" Time: ");
            builder.append((scores.get(i).getTime()));
            builder.append(" Steps: ");
            builder.append(scores.get(i).getSteps());
            builder.append("\n");
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Top Scorers");
        alert.setHeaderText("Top Scores For level " + Integer.toString(controller.getLevel()));
        alert.setContentText(builder.toString());
        alert.showAndWait().ifPresent(rs -> {
        });
    }
    public void HandleTopTenAllLevels()
    {

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
            this.incrementSteps();
        });

        this.controller.setDisplayer(boardDisplayer);
        this.controller.drawBoard();
        numberOfSteps.setText(Integer.toString(this.steps));
        timerLabel.setText(Integer.toString(this.time));
        Runnable runnable =  () -> {this.stopWatchLoop(); };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void incrementSteps()
    {
        this.steps++;
        numberOfSteps.setText(Integer.toString(this.steps));
    }

    private void initializeNewLevel()
    {
        this.steps = 0;
        numberOfSteps.setText(Integer.toString(this.steps));
        this.time = 0;
        timerLabel.setText(Integer.toString(this.time));
    }

    private void stopWatchLoop()
    {
        while(true)
        {
            try {
                Thread.sleep(1000);
                this.time++;
                Platform.runLater(
                        () -> {
                            timerLabel.setText(Integer.toString(this.time));
                        }
                );

            } catch (InterruptedException e) {
            }

        }
    }
}
