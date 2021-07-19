import javafx.application.Application;
import javafx.stage.Stage;
import ui.windows.WindowType;
import ui.windows.Windows;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Windows.getInstance().openWindow(WindowType.MAIN);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
