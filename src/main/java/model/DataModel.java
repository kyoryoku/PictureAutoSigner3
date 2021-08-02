package model;

import hotkeys.Key;
import hotkeys.Keys;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;

public class DataModel {

    private final ObservableList<Command> commands = FXCollections.observableArrayList();
    private final ObservableList<Command> pausedCommands = FXCollections.observableArrayList();
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

    public ArrayList<String> getNumeratorsAndKeysList(){
        ArrayList<String> items = new ArrayList<>();
        for (Key k : Keys.getKeyList()){
            items.add("#{" + k.getLongName() + "}");
        }

        //Добавить нумераторы в список

        Collections.sort(items);
        items.add(0, "${}");
        items.add(0, "#{}");

        return items;
    }

    public void clear(){
        commands.clear();
        chosenCommand.set(null);
    }

    public void disableAll(){
        for(Command cmd : commands){
            cmd.setActive(false);
        }
    }

    public void enableAll(){
        for(Command cmd : commands){
            cmd.setActive(true);
        }
    }

    public void pause(){
        pausedCommands.clear();
        for(Command cmd : commands){

            if (cmd.isActive()){
                pausedCommands.add(cmd);
                cmd.setActive(false);
            }
        }
    }


    public void unpause(){
        for(Command cmd : pausedCommands){
            cmd.setActive(true);
        }
        pausedCommands.clear();
    }
}
