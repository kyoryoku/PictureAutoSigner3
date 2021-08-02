package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import model.Command;
import model.CommandType;
import model.DataModel;
import org.kordamp.ikonli.javafx.FontIcon;

import javax.xml.crypto.Data;
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

    //Кнопки
    @FXML private JFXButton btnAccept;
    @FXML private JFXButton btnCancel;


    //в ней хранится TextArea, которая вызвала окно поиска
    private JFXTextArea sourceTextArea;

    @FXML void initialize() {
        lblMessage.setText("Выберите тип команды!");
        cmdType.getItems().setAll(CommandType.values());
        cmdType.valueProperty().addListener((observableValue, oldValue, newValue) -> changeContent(newValue));

        cmdStr1.setOnKeyReleased(this::handleStr1Str2KeyRelease);
        cmdStr2.setOnKeyReleased(this::handleStr1Str2KeyRelease);

        initializeButtons();
        initializeSearchAnchorPane();
        initializeSearchComboBox();
    }

    //этот метод нужен для передачи фокуса на поле сразу после отображения окна
    //вызывается в WindowsManager
    public void afterShow(){
        cmdName.setText(DataModel.getDataModel().getChosenCommand().getName());
        cmdType.getSelectionModel().select(DataModel.getDataModel().getChosenCommand().getType());
        changeContent(DataModel.getDataModel().getChosenCommand().getType());
        cmdStr1.setText(DataModel.getDataModel().getChosenCommand().getStr1());
        cmdStr2.setText(DataModel.getDataModel().getChosenCommand().getStr2());

        cmdName.requestFocus();
        cmdName.positionCaret(cmdName.getText().length());
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
            }
            case REPLACE -> {
                lblMessage.setVisible(false);
                cmdStr1.setVisible(true);
                cmdStr2.setVisible(true);
            }
        }
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

    //инициализируем окошко поиска
    private void initializeSearchAnchorPane(){
        searchAnchorPane.setVisible(false);
        searchAnchorPane.setPrefHeight(90);
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

                String oldText = sourceTextArea.getText();
                int caretPosition = sourceTextArea.getCaretPosition();
                sourceTextArea.setText(oldText.substring(0, caretPosition) + text + oldText.substring(caretPosition));


                //очищаем Editor, прячем выпадающий список, передаем фокус в TextArea
                searchComboBox.getEditor().clear();
                searchComboBox.hide();
                sourceTextArea.requestFocus();

                //Если пользователь выбрал пустые шаблоны ${} или #{},
                // то переводим каретку на 1 символ назад, чтобы получился между скобок.
                //Иначе ставим каретку в конец строки.
                int newCaretPosition = caretPosition + text.length();
                if (text.equals("${}") || text.equals("#{}")){
                    sourceTextArea.positionCaret(newCaretPosition - 1);
                } else {
                    sourceTextArea.positionCaret(newCaretPosition);
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

    private void initializeButtons(){
        int iconSize = 20;
        Color iconColor = Color.web("#0dba19");
        Color iconColorAlert = Color.web("#ff1128");

        //Кнопка Принять
        FontIcon iconAccept = new FontIcon("anto-check");
        iconAccept.setIconSize(iconSize);
        iconAccept.setIconColor(iconColor);
        btnAccept.setGraphic(iconAccept);

        //Кнопка Отменить
        FontIcon iconCancel = new FontIcon("anto-close");
        iconCancel.setIconSize(iconSize);
        iconCancel.setIconColor(iconColorAlert);
        btnCancel.setGraphic(iconCancel);
    }

    @FXML void handleBtnAccept(ActionEvent event) {

        DataModel.getDataModel().getChosenCommand().setName(cmdName.getText());
        DataModel.getDataModel().getChosenCommand().setType(cmdType.getValue());
        DataModel.getDataModel().getChosenCommand().setStr1(cmdStr1.getText());
        DataModel.getDataModel().getChosenCommand().setStr2(cmdStr2.getText());

        WindowsManager.getInstance().closeCommandWindow();

    }

    @FXML void handleBtnCancel(ActionEvent event) {
        WindowsManager.getInstance().closeCommandWindow();
    }
}