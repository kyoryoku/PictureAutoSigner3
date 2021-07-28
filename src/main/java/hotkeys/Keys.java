package hotkeys;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Keys {
    private static ArrayList<Key> keyList = new ArrayList<>();
    private static Key undefinedKey = new Key("UNDEFINED", -1, "UNDEFINED");

    static {
        keyList.add(new Key("VK_ESCAPE", KeyEvent.VK_ESCAPE, "Esc"));
        keyList.add(new Key("VK_F1", KeyEvent.VK_F1, "F1"));
        keyList.add(new Key("VK_F2", KeyEvent.VK_F2, "F2"));
        keyList.add(new Key("VK_F3", KeyEvent.VK_F3, "F3"));
        keyList.add(new Key("VK_F4", KeyEvent.VK_F4, "F4"));
        keyList.add(new Key("VK_F5", KeyEvent.VK_F5, "F5"));
        keyList.add(new Key("VK_F6", KeyEvent.VK_F6, "F6"));
        keyList.add(new Key("VK_F7", KeyEvent.VK_F7, "F7"));
        keyList.add(new Key("VK_F8", KeyEvent.VK_F8, "F8"));
        keyList.add(new Key("VK_F9", KeyEvent.VK_F9, "F9"));
        keyList.add(new Key("VK_F10", KeyEvent.VK_F10, "F10"));
        keyList.add(new Key("VK_F11", KeyEvent.VK_F11, "F11"));
        keyList.add(new Key("VK_F12", KeyEvent.VK_F12, "F12"));

        keyList.add(new Key("VK_BACK_QUOTE", KeyEvent.VK_BACK_QUOTE, "`"));
        keyList.add(new Key("VK_1", KeyEvent.VK_1, "1"));
        keyList.add(new Key("VK_2", KeyEvent.VK_2, "2"));
        keyList.add(new Key("VK_3", KeyEvent.VK_3, "3"));
        keyList.add(new Key("VK_4", KeyEvent.VK_4, "4"));
        keyList.add(new Key("VK_5", KeyEvent.VK_5, "5"));
        keyList.add(new Key("VK_6", KeyEvent.VK_6, "6"));
        keyList.add(new Key("VK_7", KeyEvent.VK_7, "7"));
        keyList.add(new Key("VK_8", KeyEvent.VK_8, "8"));
        keyList.add(new Key("VK_9", KeyEvent.VK_9, "9"));
        keyList.add(new Key("VK_0", KeyEvent.VK_0, "0"));
        keyList.add(new Key("VK_MINUS", KeyEvent.VK_MINUS, "-"));
        keyList.add(new Key("VK_EQUALS", KeyEvent.VK_EQUALS, "="));

        keyList.add(new Key("VK_TAB", KeyEvent.VK_TAB, "Tab"));
        keyList.add(new Key("VK_CAPS_LOCK", KeyEvent.VK_CAPS_LOCK, "CapsLock"));
        keyList.add(new Key("VK_SHIFT", KeyEvent.VK_SHIFT, "Shift"));
        keyList.add(new Key("VK_CONTROL", KeyEvent.VK_CONTROL, "Ctrl"));
        keyList.add(new Key("VK_ALT", KeyEvent.VK_ALT, "Alt"));
        keyList.add(new Key("VK_SPACE", KeyEvent.VK_SPACE, "Space"));
        keyList.add(new Key("VK_ENTER", KeyEvent.VK_ENTER, "Enter"));
        keyList.add(new Key("VK_BACK_SLASH", KeyEvent.VK_BACK_SLASH, "BackSlash"));
        keyList.add(new Key("VK_BACK_SPACE", KeyEvent.VK_BACK_SPACE, "BackSpace"));

        keyList.add(new Key("VK_INSERT", KeyEvent.VK_INSERT, "Ins"));
        keyList.add(new Key("VK_DELETE", KeyEvent.VK_DELETE, "Del"));
        keyList.add(new Key("VK_HOME", KeyEvent.VK_HOME, "Home"));
        keyList.add(new Key("VK_END", KeyEvent.VK_END, "End"));
        keyList.add(new Key("VK_PAGE_UP", KeyEvent.VK_PAGE_UP, "PgUP"));
        keyList.add(new Key("VK_PAGE_DOWN", KeyEvent.VK_PAGE_DOWN, "PgDown"));

        keyList.add(new Key("VK_LEFT", KeyEvent.VK_LEFT, "Left"));
        keyList.add(new Key("VK_UP", KeyEvent.VK_UP, "Up"));
        keyList.add(new Key("VK_RIGHT", KeyEvent.VK_RIGHT, "Right"));
        keyList.add(new Key("VK_DOWN", KeyEvent.VK_DOWN, "Down"));

        keyList.add(new Key("VK_PRINTSCREEN", KeyEvent.VK_PRINTSCREEN, "PrtScr"));
        keyList.add(new Key("VK_SCROLL_LOCK", KeyEvent.VK_SCROLL_LOCK, "ScrollLock"));
        keyList.add(new Key("VK_PAUSE", KeyEvent.VK_PAUSE, "PauseBrake"));

        keyList.add(new Key("VK_A", KeyEvent.VK_A, "A"));
        keyList.add(new Key("VK_B", KeyEvent.VK_B, "B"));
        keyList.add(new Key("VK_C", KeyEvent.VK_C, "C"));
        keyList.add(new Key("VK_D", KeyEvent.VK_D, "D"));
        keyList.add(new Key("VK_E", KeyEvent.VK_E, "E"));
        keyList.add(new Key("VK_F", KeyEvent.VK_F, "F"));
        keyList.add(new Key("VK_G", KeyEvent.VK_G, "G"));
        keyList.add(new Key("VK_H", KeyEvent.VK_H, "H"));
        keyList.add(new Key("VK_I", KeyEvent.VK_I, "I"));
        keyList.add(new Key("VK_J", KeyEvent.VK_J, "J"));
        keyList.add(new Key("VK_K", KeyEvent.VK_K, "K"));
        keyList.add(new Key("VK_L", KeyEvent.VK_L, "L"));
        keyList.add(new Key("VK_M", KeyEvent.VK_M, "M"));
        keyList.add(new Key("VK_N", KeyEvent.VK_N, "N"));
        keyList.add(new Key("VK_O", KeyEvent.VK_O, "O"));
        keyList.add(new Key("VK_P", KeyEvent.VK_P, "P"));
        keyList.add(new Key("VK_Q", KeyEvent.VK_Q, "Q"));
        keyList.add(new Key("VK_R", KeyEvent.VK_R, "R"));
        keyList.add(new Key("VK_S", KeyEvent.VK_S, "S"));
        keyList.add(new Key("VK_T", KeyEvent.VK_T, "T"));
        keyList.add(new Key("VK_U", KeyEvent.VK_U, "U"));
        keyList.add(new Key("VK_V", KeyEvent.VK_V, "V"));
        keyList.add(new Key("VK_W", KeyEvent.VK_W, "W"));
        keyList.add(new Key("VK_X", KeyEvent.VK_X, "S"));
        keyList.add(new Key("VK_Y", KeyEvent.VK_Y, "Y"));
        keyList.add(new Key("VK_Z", KeyEvent.VK_Z, "Z"));

        keyList.add(new Key("VK_OPEN_BRACKET", KeyEvent.VK_OPEN_BRACKET, "{"));
        keyList.add(new Key("VK_CLOSE_BRACKET", KeyEvent.VK_CLOSE_BRACKET, "}"));
        keyList.add(new Key("VK_SEMICOLON", KeyEvent.VK_SEMICOLON, ":"));
        keyList.add(new Key("VK_QUOTE", KeyEvent.VK_QUOTE, "'"));
        keyList.add(new Key("VK_COMMA", KeyEvent.VK_COMMA, ","));
        keyList.add(new Key("VK_PERIOD", KeyEvent.VK_PERIOD, "."));
        keyList.add(new Key("VK_SLASH", KeyEvent.VK_SLASH, "/"));

    }

    public static Key getKeyFromLongName(String longName){
        for (Key key : keyList){
            if (key.getLongName().equals(longName)){
                return key;
            }
        }

        return undefinedKey;
    }

    public static Key getKeyFromShortName(String shortName){
        for (Key key : keyList){
            if (key.getShortName().equals(shortName)){
                return key;
            }
        }

        return undefinedKey;
    }

    public static Key getKeyFromCode(int code){
        for (Key key : keyList){
            if (key.getCode() == code){
                return key;
            }
        }

        return undefinedKey;
    }

    public static Key getUndefinedKey(){
        return undefinedKey;
    }

    public static ArrayList<Key> getKeyList(){
        return keyList;
    }
}
