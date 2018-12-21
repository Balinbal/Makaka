package Controller;

import Model.ScoreRepresentation;
import Model.StageState;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import Logic.SaveAndLoad;
import javafx.util.Duration;
import view.BoardDisplayer;
import view.CustomBorderPane;
import view.Main;
import view.MessageBoxDisplayer;

import java.io.File;
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

    public void HandleSettings()
    {
        String res = ServerPickerController.show(this.controller.getServer());
        this.controller.setServer(res);
    }

    private void handleTheme(String theme) {
        borderPane.changeTheme(theme);
        boardDisplayer.setTheme(theme);
        changeBackgroundMusic();
    }

    public void HandleThemeMario()
    {
       handleTheme("mario");
    }

    public void HandleThemeSilver()
    {
        handleTheme("silver");
    }

    public void HandleThemeDefault()
    {
        handleTheme("default");
    }
    
    public void HandleDoneButton()
    {
        char[][] board = this.boardDisplayer.getBoard();
        try {
            boolean solved = controller.isSolved(board);
            if (solved) {
                MessageBoxDisplayer.showInfo("Stage completed",
                        "You did it, " + MainWindowController.CurrentUser+"!",
                        "Well done! Stage completed!");
                this.controller.markFinished(MainWindowController.CurrentUser, this.steps, this.time);
                this.controller.incrementLevel();
                this.initializeNewLevel();


            } else {
                MessageBoxDisplayer.showInfo("Incorrect!", "Not good enough!",
                        "The solution is incorrect or it is not the best solution. Please keep trying");
            }
        } catch(Exception e) {
            MessageBoxDisplayer.showError("Error!", "Oops!", "There was a problem checking the solution with the server. Try again later");
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
            if (solution[i].equals("done") || solution[i].equals(""))
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

        MessageBoxDisplayer.showInfo("Top Scorers",
                "Top Scores For level " + Integer.toString(controller.getLevel()),
                builder.toString());
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
        this.backgroundMusicPlayer = new MediaPlayer(song);
        this.backgroundMusicPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                backgroundMusicPlayer.seek(Duration.ZERO);
            }
        });
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

            } catch (InterruptedException e) { }
        }
    }

    public void HandleSave(ActionEvent actionEvent) {
        StageState state = new StageState(this.boardDisplayer.getBoard(), this.controller.getLevel(), this.steps, this.time);
        SaveAndLoad.saveState(state);
    }

    public void HandleLoad(ActionEvent actionEvent){
        StageState state = SaveAndLoad.LoadSolution();
        if (state != null) {
            this.controller.setLevel(state.getLevel());
            this.boardDisplayer.setBoard(state.getBoard());
            this.steps = state.getSteps();
            numberOfSteps.setText(Integer.toString(this.steps));
            this.time = state.getTime();
            this.boardDisplayer.redraw();
        }

    }

    public void HandleClose(ActionEvent actionEvent){
        Main.active.close();
    }
}

