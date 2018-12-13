package view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class CustomBorderPane extends BorderPane {

    private StringProperty backgroundFolder;
    private StringProperty theme;
    private StringProperty backgroundMusicFileName;
    private StringProperty backgroundMusicFileFolder;
    MediaPlayer backgroundMusicPlayer;

    public String getBackgroundFolder() {
        return backgroundFolder.get();
    }

    public void setBackgroundFolder(String backgroundFolder) {
        this.backgroundFolder.set(backgroundFolder);
    }

    public String getTheme() {
        return theme.get();
    }

    public void setTheme(String theme) {
        this.theme.set(theme);
    }

    public String getBackgroundMusicFileName() {
        return backgroundMusicFileName.get();
    }

    public void setBackgroundMusicFileName(String backgroundMusicFileName) {
        this.backgroundMusicFileName.set(backgroundMusicFileName);
    }

    public String getBackgroundMusicFileFolder() {
        return backgroundMusicFileFolder.get();
    }

    public void setBackgroundMusicFileFolder(String backgroundMusicFileFolder) {
        this.backgroundMusicFileFolder.set(backgroundMusicFileFolder);
    }
    public CustomBorderPane(){
        backgroundFolder = new SimpleStringProperty();
        theme = new SimpleStringProperty();
        backgroundMusicFileName = new SimpleStringProperty();
        backgroundMusicFileFolder = new SimpleStringProperty();
    }


    public void changeTheme(String theme)
    {
        setTheme(theme);
        try {
            changeBackground("bg.jpg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void changeBackground(String imagePath) throws FileNotFoundException {
        String backgroundFolder = getBackgroundFolder().replace("{theme}", getTheme());
        Image bgImage = new Image(new FileInputStream(backgroundFolder + imagePath));
        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
        Background background = new Background(new BackgroundImage(bgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize));
        setBackground(background);
    }
}
