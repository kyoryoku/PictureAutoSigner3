package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataModel {

    private final ObservableList<Command> commands = FXCollections.observableArrayList();
    private final ObjectProperty<Command> chosenCommand = new SimpleObjectProperty<>(null);
    private final Profile profile;


    public Command getChosenCommand() {
        return chosenCommand.get();
    }

    public ObjectProperty<Command> chosenCommandProperty() {
        return chosenCommand;
    }

    public void setChosenCommand(Command chosenCommand) {
        this.chosenCommand.set(chosenCommand);
    }

    public ObservableList<Command> getCommands() {
        return commands;
    }


    private static DataModel dataModel;

    private DataModel() {
        this.profile = new Profile();
    }

    public static DataModel getDataModel(){
        if (dataModel == null){
            dataModel = new DataModel();
        }
        return dataModel;
    }

    public void addCommand(Command command){
        commands.add(command);
    }

    public void removeCommand(Command command){
        if (command != null){
            commands.remove(command);
        }

    }

    public Profile getProfile() {
        return profile;
    }
}
