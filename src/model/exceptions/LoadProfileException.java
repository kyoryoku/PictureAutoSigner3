package model.exceptions;

public class LoadProfileException extends Exception{

    public LoadProfileException(String message) {
        super("Ошибка при загрузке профиля! " + message);
    }
}
