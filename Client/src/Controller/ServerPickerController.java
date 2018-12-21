package Controller;

import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class ServerPickerController {
    static String show(String current)
    {
        TextInputDialog td = new TextInputDialog(current);

        // setHeaderText
        td.setHeaderText("Enter server address");
        Optional<String> result = td.showAndWait();


        if (result.isPresent()) {
            return result.get();
        } else {
            return "localhost";
        }
    }
}
