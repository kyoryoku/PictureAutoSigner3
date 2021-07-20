package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.CommandType;
import model.DataModel;
import ui.windows.WindowType;
import ui.windows.Windows;

import javax.xml.crypto.Data;

public class commandViewController implements Windowed {

    @FXML private ResourceBundle resources;
    @FXML private URL location;

    @FXML private JFXTextField cmdName;
    @FXML private JFXComboBox<CommandType> cmdType;
    @FXML private JFXTextArea cmdStr1;
    @FXML private JFXTextArea cmdStr2;
    @FXML private Label lblMessage;

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



    }

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



    Stage stage;
    @Override
    public void show() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("commandView.fxml"));
            stage = new Stage();
            stage.setTitle("COMMAND VIEW");
            stage.setScene(new Scene(root, 600, 400));

            stage.setOnCloseRequest(e -> {
                Windows.getInstance().closeWindow(WindowType.COMMAND);
            });

            stage.show();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Override
    public void close() {
        stage.close();
    }
}