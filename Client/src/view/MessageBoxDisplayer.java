package view;

import javafx.scene.control.Alert;

public class MessageBoxDisplayer {
    public static void show(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait().ifPresent(rs -> {
        });
    }

    public static void showError(String title, String header, String content)
    {
        show(Alert.AlertType.ERROR, title, header, content);
    }

    public static void showInfo(String title, String header, String content)
    {
        show(Alert.AlertType.INFORMATION, title, header, content);
    }

    public static void showWarn(String title, String header, String content)
    {
        show(Alert.AlertType.WARNING, title, header, content);
    }
}
