package model;


import hotkeys.Hotkey;
import hotkeys.KeyCombination;
import hotkeys.KeyListener;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Command implements KeyListener {
    private static int idx;

    private SimpleStringProperty name;
    private SimpleObjectProperty<CommandType> type;
    private SimpleBooleanProperty active;
    private SimpleStringProperty keyCombinationString;
    private SimpleStringProperty str1;
    private SimpleStringProperty str2;

    private KeyCombination keyCombination;


    public Command() {
        this.name = new SimpleStringProperty("cmd" + idx);
        this.type = new SimpleObjectProperty<>(CommandType.NOT_SET);
        this.active = new SimpleBooleanProperty(false);
        this.str1 = new SimpleStringProperty("");
        this.str2 = new SimpleStringProperty("");
        this.keyCombination = new KeyCombination();
        this.keyCombinationString = new SimpleStringProperty(this.keyCombination.toShortString());
        Hotkey.getInstance().addListeners(this);

        idx++;
    }

    @Override
    public void handleHotkeyEvent(KeyCombination keyCombination) {
        if (this.isActive() && this.keyCombination.isDefined() && this.keyCombination.equals(keyCombination))
        {
            switch (this.type.get()){
                case PAST_TEXT -> Executor.execute(str1.get(), true);
                case CHANGE_COUNTER -> Executor.execute(str1.get(), false);
                case REPLACE -> Executor.replace(str1.get(), str2.get());
            }
            //System.out.println("выполнилось действие команды " + this.getName());
        }
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public CommandType getType() {
        return type.get();
    }

    public SimpleObjectProperty<CommandType> typeProperty() {
        return type;
    }

    public void setType(CommandType type) {
        this.type.set(type);
    }

    public boolean isActive() {
        return active.get();
    }

    public SimpleBooleanProperty activeProperty() {
        return active;
    }

    public void setActive(boolean active) {
        this.active.set(active);
    }

    public String getKeyCombinationString() {
        return keyCombinationString.get();
    }

    public SimpleStringProperty keyCombinationStringProperty() {
        return keyCombinationString;
    }

    public void setKeyCombinationString(String keyCombinationString) {
        this.keyCombinationString.set(keyCombinationString);
    }

    public String getStr1() {
        return str1.get();
    }

    public SimpleStringProperty str1Property() {
        return str1;
    }

    public void setStr1(String str1) {
        this.str1.set(str1);
    }

    public String getStr2() {
        return str2.get();
    }

    public SimpleStringProperty str2Property() {
        return str2;
    }

    public void setStr2(String str2) {
        this.str2.set(str2);
    }

    public KeyCombination getKeyCombination() {
        return keyCombination;
    }

    public void setKeyCombination(KeyCombination keyCombination) {
        this.keyCombination = keyCombination;
        this.keyCombinationString.set(this.keyCombination.toShortString());
    }
}
