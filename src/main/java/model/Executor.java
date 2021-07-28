package model;

import hotkeys.Keys;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Executor {
    public static HashMap<String, Integer> numerators = new HashMap<>();
    private static Robot robot;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static void execute (String text, boolean pastText) {

        Pattern pattern = Pattern.compile("[#$]{1}\\{\\w+[+-=]?\\w+\\}");
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()){

            if (matcher.group().startsWith("#{VK_")){

                paste(text.substring(0, matcher.start()));
                press(matcher.group().substring(2, matcher.group().length() - 1));

                text = text.substring(matcher.end());
                matcher = pattern.matcher(text);
            } else if (matcher.group().startsWith("${")) {
                text = text.replace(matcher.group(), numerate(matcher.group()));
                matcher = pattern.matcher(text);
            }
        }

        if (pastText){
            paste(text);
        }

    }

    private static void paste (String text){
        if (text.length() != 0) {
            StringSelection stringSelection = new StringSelection(text);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);

            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
        }
    }

    private static void press (String key){
        try {
            Thread.sleep(200);
            robot.keyPress(Keys.getKeyFromLongName(key).getCode());
            robot.keyRelease(Keys.getKeyFromLongName(key).getCode());
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static String numerate (String numeratorString){
        //отсекаем $, { и }
        numeratorString = numeratorString.substring(2, numeratorString.length() - 1);

        String name = "";
        int value = 0;
        String[] strs;

        //определяем операцию нумератора
        if (numeratorString.contains("+")){
            strs = numeratorString.split("\\+");
            name = strs[0];
            value = Integer.parseInt(strs[1]);
        } else if (numeratorString.contains("-")){
            strs = numeratorString.split("-");
            name = strs[0];
            value = Integer.parseInt(strs[1]);
        } else if (numeratorString.contains("=")) {
            strs = numeratorString.split("=");
            name = strs[0];
            value = Integer.parseInt(strs[1]);
        } else {
            name = numeratorString;
        }


        if (!numerators.containsKey(name)){
            numerators.put(name, value);
        }else {
            int tmp = numerators.get(name).intValue();
            if (numeratorString.contains("+")){
                tmp = tmp + value;
            } else if (numeratorString.contains("-")){
                tmp = tmp - value;
            } else if (numeratorString.contains("=")) {
                tmp = value;
            }
            numerators.replace(name, tmp);
        }

        return numerators.get(name).toString();
    }

    public static void replace (String str1, String str2){

        Pattern pattern = Pattern.compile("[$]{1}\\{\\w+[+-=]?\\w+\\}");
        Matcher matcher = pattern.matcher(str1);
        while (matcher.find()){
                str1 = str1.replace(matcher.group(), numerate(matcher.group()));
                matcher = pattern.matcher(str1);
        }

        //нажать бекспейс столько раз, сколько символов в строке str1
        for (int i = 0; i < str1.length(); i++) {
            press("VK_BACK_SPACE");
        }

        //вставить текст стр2
        paste(numerate(str2));
    }


}
