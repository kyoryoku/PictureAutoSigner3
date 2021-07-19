package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.windows.WindowType;
import ui.windows.Windows;

import java.io.IOException;

public class profileViewController implements Windowed{

    private Stage stage;
    @Override
    public void show() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("profileView.fxml"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        stage = new Stage();
        stage.setTitle("PROFILE");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
        stage.setOnCloseRequest(e -> {
            Windows.getInstance().closeWindow(WindowType.PROFILE);
        });
    }

    @Override
    public void close() {
        stage.close();
    }
}
