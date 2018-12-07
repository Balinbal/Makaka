package view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable{

    @FXML
    BoardDisplayer boardDisplayer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        char[][] data = {
            {'s','-','7'},
            {'f','-','j'},
            {'l','-','g'}
        };

        boardDisplayer.setBoard(data);
    }
}
