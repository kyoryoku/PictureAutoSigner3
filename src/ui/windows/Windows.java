package ui.windows;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.*;

import java.io.IOException;
import java.util.HashMap;

public class Windows {

    private static Windows instance;
    public static Windows getInstance(){
        if (instance == null){
            instance = new Windows();
        }
        return instance;
    }

    private HashMap<WindowType, Windowed> openedWindows = new HashMap<>();

    public void openWindow(WindowType type){
        if (!openedWindows.containsKey(type)){
            switch (type){
                case MAIN -> openedWindows.put(type, new mainViewController());
                case COMMAND -> openedWindows.put(type, new commandViewController());
                case PROFILE -> openedWindows.put(type, new profileViewController());
                case HELP -> openedWindows.put(type, new helpViewController());
            }

            openedWindows.get(type).show();
        }
    }

    public void closeWindow(WindowType type){
        openedWindows.get(type).close();
        openedWindows.remove(type);
    }


}
