package ui;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.CommandType;
import model.DataModel;

import java.net.URL;
import java.util.ResourceBundle;

public class CommandController {

    @FXML private ResourceBundle resources;
    @FXML private URL location;


    //Элементы команды
    @FXML private JFXTextField cmdName;
    @FXML private JFXComboBox<CommandType> cmdType;
    @FXML private JFXTextArea cmdStr1;
    @FXML private JFXTextArea cmdStr2;

    //Метка выводит текст, если не выбран тип команды
    @FXML private Label lblMessage;

    //Элементы всплывающего окошка поиска
    @FXML private AnchorPane searchAnchorPane;
    @FXML private JFXComboBox<String> searchComboBox;

    //в ней хранится TextArea, которая вызвала окно поиска
    private JFXTextArea sourceTextArea;

    @FXML void initialize() {
        initializeCmdName();
        initializeCmdType();
        initializeCmdStrs();
        initializeLabelMessage();

        initializeSearchAnchorPane();
        initializeSearchComboBox();
    }

    //этот метод нужен для передачи фокуса на поле сразу после отображения окна
    //вызывается в WindowsManager
    public void afterShow(){
        cmdName.requestFocus();
    }

    //Инициализируем поля Str1 и Str2 у команды
    private void initializeCmdStrs(){
        cmdStr1.textProperty().bindBidirectional(
                DataModel.getDataModel().getChosenCommand().str1Property()
        );
        cmdStr1.setOnKeyReleased(this::handleStr1Str2KeyRelease);

        cmdStr2.textProperty().bindBidirectional(
                DataModel.getDataModel().getChosenCommand().str2Property()
        );
        cmdStr2.setOnKeyReleased(this::handleStr1Str2KeyRelease);
    }

    //обработчик нажатия клавиш в полях cmdStr1 и cmdStr2
    private void handleStr1Str2KeyRelease(KeyEvent event){

        //Если нажата ctrl+F
        if (event.isControlDown()){
            if (event.getCode() == KeyCode.F){

                //Запоминаем источник, показываем окошко, передаем фокус
                sourceTextArea = (JFXTextArea) event.getSource();
                searchAnchorPane.setVisible(true);
                searchComboBox.requestFocus();
            }
        }
    }

    //Инициализируем ComboBox для типа команды
    private void initializeCmdType(){
        cmdType.getItems().setAll(CommandType.values());
        cmdType.valueProperty().bindBidirectional(DataModel.getDataModel().getChosenCommand().typeProperty());
        cmdType.valueProperty().addListener((observableValue, oldValue, newValue) -> changeContent(newValue));

        changeContent(DataModel.getDataModel().getChosenCommand().getType());
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

    //инициализируем окошко поиска
    private void initializeSearchAnchorPane(){
        searchAnchorPane.setVisible(false);
        searchAnchorPane.setPrefHeight(90);
    }

    //инициализируем поле имени команды на форме
    private void initializeCmdName(){
        cmdName.textProperty().bindBidirectional(
                DataModel.getDataModel().getChosenCommand().nameProperty()
        );
    }

    //инициализируем метку-подсказку
    private void initializeLabelMessage(){
        lblMessage.setText("Выберите тип команды!");
    }

    //инициализируем ComboBox для поиска
    private void initializeSearchComboBox(){
        //устанавливаем элементы для отображения в выпадающем списке
        ObservableList<String> items = FXCollections.observableArrayList(DataModel.getDataModel().getNumeratorsAndKeysList());
        FilteredList<String> filteredItems = new FilteredList<String>(items, p -> true);
        searchComboBox.setItems(filteredItems);

        //прячем окошко поиска, если ComboBox теряет фокус
        searchComboBox.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused){
                searchAnchorPane.setVisible(false);
            }
        });

        //фильтруем элементы ComboBox, когда меняется значение
        searchComboBox.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            final TextField editor = searchComboBox.getEditor();
            final String selected = searchComboBox.getSelectionModel().getSelectedItem();

            //Если Editor пустой, то прячем выпадающий список с вариантами, иначе показываем
            if (newValue.isEmpty()){
                searchComboBox.hide();
            } else {
                searchComboBox.show();
            }

            // This needs run on the GUI thread to avoid the error described
            // here: https://bugs.openjdk.java.net/browse/JDK-8081700.
            Platform.runLater(() -> {
                if (selected == null || !selected.equals(editor.getText())) {
                    filteredItems.setPredicate(item -> item.toUpperCase().contains(newValue.toUpperCase()));
                }
            });
        });

        //обрабатываем разные "управляющие" клавиши для ComboBox
        searchComboBox.getEditor().setOnKeyReleased(keyEvent -> {

            if (keyEvent.getCode() == KeyCode.ENTER){
                //Если нажали ENTER, то добавляем текст из Editor в TextArea, вызвавшую окошко поиска
                String text = searchComboBox.getEditor().getText();
                sourceTextArea.setText(sourceTextArea.getText() + text);

                //очищаем Editor, прячем выпадающий список, передаем фокус в TextArea
                searchComboBox.getEditor().clear();
                searchComboBox.hide();
                sourceTextArea.requestFocus();

                //Если пользователь выбрал пустые шаблоны ${} или #{},
                // то переводим каретку на 1 символ назад, чтобы получился между скобок.
                //Иначе ставим каретку в конец строки.
                if (text.equals("${}") || text.equals("#{}")){
                    sourceTextArea.positionCaret(sourceTextArea.getText().length() - 1);
                } else {
                    sourceTextArea.positionCaret(sourceTextArea.getText().length());
                }
            }
        });

        //Расширяем или сужаем окошко поиска, в зависимости от того, отображается ли выпадающий список
        //или нет.
        searchComboBox.setOnShowing(e -> searchAnchorPane.setPrefHeight(210));
        searchComboBox.setOnHiding(e -> searchAnchorPane.setPrefHeight(90));

        //Количество отображаемых вариантов в выпадающем списке
        searchComboBox.setVisibleRowCount(4);
    }
}