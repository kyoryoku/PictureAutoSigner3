import hotkeys.Hotkey;
import javafx.application.Application;
import javafx.stage.Stage;
import ui.WindowsManager;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        WindowsManager.getInstance().openMainWindow();
        Hotkey.getInstance().enableHotkeys();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
