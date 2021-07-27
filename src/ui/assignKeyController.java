package ui;

import com.jfoenix.controls.JFXButton;
import hotkeys.Hotkey;
import hotkeys.KeyCombination;
import hotkeys.KeyListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import model.Command;
import model.DataModel;
import org.kordamp.ikonli.javafx.FontIcon;

import javax.xml.crypto.Data;
import java.net.URL;
import java.util.ResourceBundle;

public class assignKeyController implements KeyListener {
    @FXML
    private ResourceBundle resources;
    @FXML private URL location;

    @FXML private JFXButton btnAccept;
    @FXML private JFXButton btnCancel;
    @FXML private Label lblHint;
    @FXML private Label lblCombination;

    private boolean uniqueCombination = false;

    @FXML void initialize() {
        initializeButtons();
        Hotkey.getInstance().addListeners(this);
//        lblCombination.textProperty().bindBidirectional(
//                DataModel.getDataModel().getChosenCommand().keyCombinationStringProperty()
//        );
        lblCombination.setText(DataModel.getDataModel().getChosenCommand().getKeyCombinationString());
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

    //обработчик нажатия на кнопку Принять
    @FXML void handleBtnAccept(ActionEvent event) {
        if (uniqueCombination){
            DataModel.getDataModel().getChosenCommand().setKeyCombination(
                    new KeyCombination(lblCombination.getText())
            );
            WindowsManager.getInstance().closeAssignKeyWindow();
        } else {
            lblHint.setText("Невозможно назначить комбинацию!\nКомбинация уже используется!");
        }
    }

    //обработчик нажатия на кнопку Отменить
    @FXML void handleBtnCancel(ActionEvent event) {
        WindowsManager.getInstance().closeAssignKeyWindow();
    }

    @Override
    public void handleHotkeyEvent(KeyCombination keyCombination) {

        //проверяем, что нажатая комбинация нигде не используется
        uniqueCombination = true;
        for (Command cmd : DataModel.getDataModel().getCommands()){
            if (cmd.getKeyCombination().equals(keyCombination)
            && !cmd.getName().equals(DataModel.getDataModel().getChosenCommand().getName())){
                uniqueCombination = false;
                break;
            }
        }

        //меняем тектс в окошке в зависимости от уникальности комбинации
        if (uniqueCombination){
            lblHint.setText("Выбрана комбинация");
        } else {
            lblHint.setText("Комбинация уже используется!");
        }

        lblCombination.setText(keyCombination.toShortString());
    }
}
