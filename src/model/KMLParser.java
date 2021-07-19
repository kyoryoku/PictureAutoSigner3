package model;

import hotkeys.KeyCombination;
import model.exceptions.KMLException;

public class KMLParser {

    /**
     * Метод парсит строку текста в объект Command
     * @return Объект Command
     * */
    public Command stringToObject(String str) throws KMLException {

        Command cmd = new Command();

        //Если пришол налл
        if (str == null) {
            throw new KMLException("Не удалось считать строки из профиля!");
        }

        //Если строка пустая или содержит только пробелы, бросаем исключение
        if (str.isBlank()) {
            throw new KMLException("Пустая строка или содержит только пробелы!");
        }

        //Делим строку на части, если частей не 6, бросаем исключение
        String[] strs = str.split(":::", -1);
        if (strs.length != 6) {
            throw new KMLException("Количество параметров в строке не равно 6!");
        }

        //Имя команды идет первым параметром
        cmd.setName(strs[0]);

        //Тип команды идет вторым параметром
        switch (strs[1]) {
            case "вставить" -> cmd.setType(CommandType.PAST_TEXT);
            case "заменить" -> cmd.setType(CommandType.REPLACE);
            case "изменить" -> cmd.setType(CommandType.CHANGE_COUNTER);
            case "не установлено" -> cmd.setType(CommandType.NOT_SET);
            default -> throw new KMLException("Второй параметр не соответствует ожидаемому!");
        }

        //Активность команды идет третьим параметром
        switch (strs[2]) {
            case "активная" -> cmd.setActive(true);
            case "не активная" -> cmd.setActive(false);
            default -> throw new KMLException("Третий параметр не соответствует ожидаемому!");
        }

        try {
            //Назначенная комбинация - четвертый параметр
            cmd.setKeyCombination(new KeyCombination(strs[3]));
        } catch (Exception e){
            throw new KMLException("Не удаяется корректно распознать комбинацию!");
        }

        //Строка стр1 - пятый параметр. Заменяем (NL) на символ переноса строки
        cmd.setStr1(strs[4].replace("(NL)", "\n"));

        //Строка стр2 - шестой параметр. Заменяем (NL) на символ переноса строки
        cmd.setStr2(strs[5].replace("(NL)", "\n"));

        return cmd;
    }

    public String objectToString(Command cmd) throws KMLException {
        String str = "";
        String delimiter = ":::";

        if (cmd == null){
            throw new KMLException("Не удается преобразовать объект в строку!");
        }

        str = str + cmd.getName() + delimiter;

        switch (cmd.getType()) {
            case PAST_TEXT -> str = str + "вставить" + delimiter;
            case REPLACE -> str = str + "заменить" + delimiter;
            case CHANGE_COUNTER -> str = str + "изменить" + delimiter;
            case NOT_SET -> str = str + "не установлено" + delimiter;
        }

        if (cmd.isActive()){
            str = str + "активная" + delimiter;
        } else {
            str = str + "не активная" + delimiter;
        }


        str = str + cmd.getKeyCombinationString() + delimiter;

        str = str + cmd.getStr1().replace("\n", "(NL)") + delimiter;

        str = str + cmd.getStr2().replace("\n", "(NL)");

        return str;
    }


}
