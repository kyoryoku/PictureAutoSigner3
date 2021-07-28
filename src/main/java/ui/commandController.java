package ui;

import com.jfoenix.controls.*;
import hotkeys.Key;
import hotkeys.Keys;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.CommandType;
import model.DataModel;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class commandController {

    @FXML private ResourceBundle resources;
    @FXML private URL location;

    @FXML private JFXTextField cmdName;
    @FXML private JFXComboBox<CommandType> cmdType;
    @FXML private JFXTextArea cmdStr1;
    @FXML private JFXTextArea cmdStr2;
    @FXML private Label lblMessage;
    @FXML private AnchorPane searchAnchorPane;
    @FXML JFXComboBox<String> comboBox;


    @FXML void initialize() {
        searchAnchorPane.setVisible(false);
        searchAnchorPane.setPrefHeight(90);
        comboBox.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused){
                searchAnchorPane.setVisible(false);
            }
        });

        cmdName.textProperty().bindBidirectional(
                DataModel.getDataModel().getChosenCommand().nameProperty()
        );

        cmdType.getItems().setAll(CommandType.values());
        cmdType.valueProperty().bindBidirectional(DataModel.getDataModel().getChosenCommand().typeProperty());
        cmdType.valueProperty().addListener((observableValue, oldValue, newValue) -> changeContent(newValue));

        lblMessage.setText("Выберите тип команды!");
        changeContent(DataModel.getDataModel().getChosenCommand().getType());

        cmdStr1.textProperty().bindBidirectional(
                DataModel.getDataModel().getChosenCommand().str1Property()
        );
        cmdStr1.setOnKeyReleased(this::handleStr1Str2KeyRelease);

        cmdStr2.textProperty().bindBidirectional(
                DataModel.getDataModel().getChosenCommand().str2Property()
        );
        cmdStr2.setOnKeyReleased(this::handleStr1Str2KeyRelease);





        ObservableList<String> items = FXCollections.observableArrayList();
        for (Key k : Keys.getKeyList()){
            items.add(k.getLongName());
        }

        FilteredList<String> filteredItems = new FilteredList<String>(items, p -> true);
        comboBox.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            final TextField editor = comboBox.getEditor();
            final String selected = comboBox.getSelectionModel().getSelectedItem();

            if (newValue.isEmpty()){
                comboBox.hide();
            } else {
                comboBox.show();
            }

            // This needs run on the GUI thread to avoid the error described
            // here: https://bugs.openjdk.java.net/browse/JDK-8081700.
            Platform.runLater(() -> {

                // If the no item in the list is selected or the selected item
                // isn't equal to the current input, we refilter the list.
                if (selected == null || !selected.equals(editor.getText())) {
                    filteredItems.setPredicate(item -> item.toUpperCase().contains(newValue.toUpperCase()));
                }
            });
        });
        comboBox.setOnShowing(e -> searchAnchorPane.setPrefHeight(210));
        comboBox.setOnHiding(e -> searchAnchorPane.setPrefHeight(90));
        comboBox.setItems(filteredItems);
        comboBox.setVisibleRowCount(4);
    }

    //этот метод нужен для передачи фокуса на поле сразу после отображения окна
    //вызывается в WindowsManager
    public void afterShow(){
        cmdName.requestFocus();

    }

    //метод обновляет контент в зависимости от типа команды
    private void changeContent(CommandType newType){
        switch (newType){
            case NOT_SET -> {
                lblMessage.setVisible(true);
                cmdStr1.setVisible(false);
                cmdStr2.setVisible(false);
            }
            case PAST_TEXT, CHANGE_COUNTER -> {
                lblMessage.setVisible(false);
                cmdStr1.setVisible(true);
                cmdStr2.setVisible(false);

                AnchorPane.setBottomAnchor(cmdStr1, 10.);
            }
            case REPLACE -> {
                AnchorPane.setBottomAnchor(cmdStr1, 150.);

                lblMessage.setVisible(false);
                cmdStr1.setVisible(true);
                cmdStr2.setVisible(true);
            }
        }
    }

    private void handleStr1Str2KeyRelease(KeyEvent event){
        if (event.isControlDown()){
            if (event.getCode() == KeyCode.F){
                searchAnchorPane.setVisible(true);
                comboBox.requestFocus();
            }
        }
    }

}