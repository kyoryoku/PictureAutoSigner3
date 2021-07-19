package model;

public enum CommandType {
    NOT_SET("Не выбрано"),
    PAST_TEXT("Вставить текст"),
    CHANGE_COUNTER("Изменить счетчики"),
    REPLACE("Заменить");

    private String label;
    CommandType(String label){
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
