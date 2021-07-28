package model.exceptions;

public class SaveProfileException extends Exception{

    public SaveProfileException(String message) {
        super("Ошибка при сохранении профиля! " + message);
    }
}
