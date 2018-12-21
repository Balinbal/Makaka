package Logic;

import Model.StageState;
import javafx.stage.FileChooser;
import view.Main;
import view.MessageBoxDisplayer;

import java.io.*;

public class SaveAndLoad {

    public static void saveState(StageState state) {
        String path = getPathWithPicker(true);
        if (path == null) {
            MessageBoxDisplayer.showWarn("Not saved", "State not saved!", "No file was picked");
        } else {
            state.toFile(path);
            MessageBoxDisplayer.showInfo("Saved successfully!", "Stage state was saved","");
        }
    }


    public static StageState LoadSolution() {
        String path = getPathWithPicker(false);

        if (path == null) {
            MessageBoxDisplayer.showWarn("Failed", "State not loaded!", "No file was picked");
        } else {
            StageState ret = StageState.fromFile(path);
            if (ret == null) {
                MessageBoxDisplayer.showWarn("Failed", "State not loaded!", "Failed to load state");
            }
            else {
                MessageBoxDisplayer.showInfo("Success", "State loaded", "Stage " + String.valueOf(ret.getLevel())+
                        " is loaded at your last state! Good luck!");
            }
            return ret;
        }
        return null;
    }

    private static String getPathWithPicker(boolean isSave) {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PIP files", "*.pip");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file;
        if (isSave) {
            file = fileChooser.showSaveDialog(Main.active);

        }
        else {file = fileChooser.showOpenDialog(Main.active); }
        if (file == null) {
            return null;
        }
        return file.getPath();
    }
}