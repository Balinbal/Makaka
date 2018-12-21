package view;

import Controller.MainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Optional;

public class Main extends Application {

    public static Stage active;

    @Override
    public void start(Stage primaryStage) throws Exception{
        active = primaryStage;
        // create a text input dialog
        TextInputDialog td = new TextInputDialog("Enter your name");

        // setHeaderText
        td.setHeaderText("Enter your name");

        Optional<String> result = td.showAndWait();

        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));

        primaryStage.setTitle("Pipe Game");
        primaryStage.setScene(new Scene(root, 500, 500));



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
