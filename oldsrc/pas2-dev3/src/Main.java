import by.kirino.hotkeys3.Hotkey;
import javafx.application.Application;
import javafx.stage.Stage;
import org.jnativehook.NativeHookException;
import window.WindowType;
import window.WindowsManager;

import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) throws NativeHookException, IOException {

        VersionControl.enable();
        Hotkey.getInstance().enableHotkeys();

        Application.launch();
    }


    @Override
    public void start(Stage primaryStage) {
        WindowsManager.createWindows();
        WindowsManager.open(WindowType.MAIN);
    }

}
