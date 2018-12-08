package view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class CustomBorderPane extends BorderPane {

    private StringProperty backgroundFolder;

    public String getBackgroundFolder() {
        return backgroundFolder.get();
    }


    public void setBackgroundFolder(String backgroundFolder) {
        this.backgroundFolder.set(backgroundFolder);
    }

    public CustomBorderPane(){
            backgroundFolder = new SimpleStringProperty();

    }
    public void changeBackground(String imagePath) throws FileNotFoundException {

        Image bgImage = new Image(new FileInputStream(getBackgroundFolder()+imagePath));
        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
        Background background = new Background(new BackgroundImage(bgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize));
        setBackground(background);
    }
}
