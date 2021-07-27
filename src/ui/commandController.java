package ui;

import com.jfoenix.controls.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import model.CommandType;
import model.DataModel;
import org.w3c.dom.ls.LSOutput;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class commandController {

    @FXML private ResourceBundle resources;
    @FXML private URL location;

    @FXML private JFXTextField cmdName;
    @FXML private JFXComboBox<CommandType> cmdType;
    @FXML private JFXTextArea cmdStr1;
    @FXML private JFXTextArea cmdStr2;
    @FXML private Label lblMessage;
    @FXML private StackPane pane;

    JFXDialog searchDialog;
    JFXDialogLayout searchDialogContent;
//    JFXComboBox<String> searchDialogContentBody;
    JFXTextField searchDialogContentBody;



    @FXML void initialize() {

        cmdName.textProperty().bindBidirectional(
                DataModel.getDataModel().getChosenCommand().nameProperty()
        );

        cmdType.getItems().setAll(CommandType.values());
        cmdType.valueProperty().bindBidirectional(DataModel.getDataModel().getChosenCommand().typeProperty());
        cmdType.valueProperty().addListener(new ChangeListener<CommandType>() {
            @Override
            public void changed(ObservableValue<? extends CommandType> observableValue, CommandType oldValue, CommandType newValue) {
                changeContent(newValue);
            }
        });

        lblMessage.setText("Выберите тип команды!");
        changeContent(DataModel.getDataModel().getChosenCommand().getType());

        cmdStr1.textProperty().bindBidirectional(
                DataModel.getDataModel().getChosenCommand().str1Property()
        );

        cmdStr2.textProperty().bindBidirectional(
                DataModel.getDataModel().getChosenCommand().str2Property()
        );



//        searchDialogContentBody = new JFXComboBox<String>();
        searchDialogContentBody = new JFXTextField();
        searchDialogContentBody.setPromptText("Введите текст для поиска");
        searchDialogContentBody.setLabelFloat(true);
        searchDialogContentBody.setEditable(true);
        ArrayList<String> list = new ArrayList<String>();
        list.add("1111");
        list.add("2222");
        list.add("3333");
        list.add("4444");
        list.add("5555");
//        searchDialogContentBody.getItems().setAll(list);

        searchDialogContent = new JFXDialogLayout( );
        searchDialogContent.setBody(searchDialogContentBody);
        searchDialog = new JFXDialog(pane, searchDialogContent, JFXDialog.DialogTransition.NONE);
    }

    //этот метод нужен для передачи фокуса на поле сразу после отображения окна
    //вызывается в виндовсменеджере
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

    @FXML private void handleKeyRelease(KeyEvent event){
        if (event.isControlDown()){
            if (event.getCode() == KeyCode.F){
                openSearchDialog();
                searchDialogContentBody.requestFocus();
            }
        }
    }

    private void openSearchDialog(){
        searchDialog.show();
    }
}