package model.exceptions;

public class KMLException extends Exception{

    public KMLException(String message) {
        super("Ошибка в файле профиля! " + message);
    }
}
