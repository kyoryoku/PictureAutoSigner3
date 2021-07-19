package window;

import window.view.*;
import java.util.HashMap;
import java.util.Map;

public class WindowsManager {

    private static Map<WindowType, Window> windows = new HashMap<>();
    private static Map<WindowType, Window> openedWindows = new HashMap<>();

    public static void createWindows(){

        windows.put(WindowType.MAIN, new MainView());
        windows.put(WindowType.COMMAND_SETTINGS, new CommandView());
        windows.put(WindowType.HOTKEY_ASSIGN, new HotkeyAssignView());
        windows.put(WindowType.PROFILE, new ProfileView());
        windows.put(WindowType.INFO, new InfoView());

    }

    public static void open(WindowType windowType){
        if (openedWindows.containsKey(windowType)){
            //в списке открытых окон такое уже есть!
            return;
        }

        openedWindows.put(windowType, windows.get(windowType));
        windows.get(windowType).show();

    }


    public static void close(WindowType windowType){
        windows.get(windowType).close();
        openedWindows.remove(windowType);
    }
}
