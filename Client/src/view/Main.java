package view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.Optional;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));

        primaryStage.setTitle("Pipe Game");
        primaryStage.setScene(new Scene(root, 500, 500));


        // create a text input dialog
        TextInputDialog td = new TextInputDialog("Enter your name");

        // setHeaderText
        td.setHeaderText("enter your name");

        Optional<String> result = td.showAndWait();
        if (result.isPresent()) {
            MainWindowController.CurrentUser = result.get();
        } else {
            MainWindowController.CurrentUser = "user";
        }

        primaryStage.show();
    }


    public static void main(String[] args) {

        launch(args);
    }

}
