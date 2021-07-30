package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import model.Command;
import model.DataModel;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController {

    @FXML private ResourceBundle resources;
    @FXML private URL location;

    @FXML private JFXListView<Command> lv;
    @FXML private JFXButton btnAdd;
    @FXML private JFXButton btnRemove;
    @FXML private JFXButton btnProfile;
    @FXML private JFXButton btnHelp;
    @FXML private JFXButton btnSave;
    @FXML private JFXButton btnLoad;




    //обработчик нажатия на кнопку Добавить
    @FXML void handleBtnAdd(ActionEvent event) {
        DataModel.getDataModel().addCommand(new Command());
    }

    //обработчик нажатия на кнопку Удалить
    @FXML void handleBtnRemove(ActionEvent event) {
        if (DataModel.getDataModel().getChosenCommand() != null ||
                DataModel.getDataModel().getCommands().size() != 0)
        {
            DataModel.getDataModel().removeCommand(
                    DataModel.getDataModel().getChosenCommand()
            );
        }
    }

    //обработчик нажатия на кнопку Профиль
    @FXML void handleBtnProfile(ActionEvent event) {
        WindowsManager.getInstance().openProfileWindow();
    }

    //обработчик нажатия на кнопку Помощь
    @FXML void handleBtnHelp(ActionEvent event) {
        WindowsManager.getInstance().openHelpWindow();
    }

    //обработчик кликов по ListView
    @FXML void handleLv(MouseEvent event) {
        if (event.getClickCount() == 2 && lv.getSelectionModel().getSelectedItem() != null) {
            WindowsManager.getInstance().openCommandWindow();
        }
    }

    @FXML void initialize() {


        initializeButtons();
        initializeListView();
    }

    //настройка кнопок
    private void initializeButtons(){
        int iconSize = 20;
        Color iconColor = Color.web("#EEEEEE");
        Color iconColorAlert = Color.web("#ff1128");

        //Кнопка Добавить
        FontIcon addIcon = new FontIcon("anto-plus");
        addIcon.setIconSize(iconSize);
        addIcon.setIconColor(iconColor);
        btnAdd.setGraphic(addIcon);

        //Кнопка Удалить
        FontIcon removeIcon = new FontIcon("anto-delete");
        removeIcon.setIconSize(iconSize);
        removeIcon.setIconColor(iconColorAlert);
        btnRemove.setGraphic(removeIcon);

        //Кнопка Профиль
        FontIcon profileIcon = new FontIcon("anto-file-ppt");
        profileIcon.setIconSize(iconSize);
        profileIcon.setIconColor(iconColor);
        btnProfile.setGraphic(profileIcon);

        //Кнопка Помощь
        FontIcon helpIcon = new FontIcon("anto-question-circle");
        helpIcon.setIconSize(iconSize);
        helpIcon.setIconColor(iconColor);
        btnHelp.setGraphic(helpIcon);

        //Кнопка Save
        FontIcon iconSave = new FontIcon("anto-export");
        iconSave.setIconSize(iconSize);
        iconSave.setIconColor(iconColor);
        btnSave.setGraphic(iconSave);

        //Кнопка Load
        FontIcon iconLoad = new FontIcon("anto-import");
        iconLoad.setIconSize(iconSize);
        iconLoad.setIconColor(iconColor);
        btnLoad.setGraphic(iconLoad);

    }

    //Настройка ListView
    private void initializeListView(){
        lv.setItems(DataModel.getDataModel().getCommands());

        //настройка содержимого строк лист вью
        lv.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Command item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    //Кнопка переключалка активности команды
                    JFXToggleButton btnActivate = new JFXToggleButton();
                    btnActivate.setPadding(new Insets(-5, 0, -5, -5));
                    btnActivate.selectedProperty().bindBidirectional(item.activeProperty());
                    btnActivate.setText("");
                    AnchorPane.setLeftAnchor(btnActivate, 0.);
                    AnchorPane.setTopAnchor(btnActivate, 0.);

                    //Метка, отображающая название команды
                    Label lblCmdName = new Label();
                    lblCmdName.textProperty().bindBidirectional(item.nameProperty());
                    //lblCmdName.disableProperty().bind(item.activeProperty().not());
                    AnchorPane.setLeftAnchor(lblCmdName, 50.);
                    AnchorPane.setTopAnchor(lblCmdName, 11.);

                    //Метка, отображающая назначенную комбинацию
                    Label lblAssignedKey = new Label();
                    lblAssignedKey.textProperty().bindBidirectional(item.keyCombinationStringProperty());
                    //lblAssignedKey.disableProperty().bind(item.activeProperty().not());
                    AnchorPane.setRightAnchor(lblAssignedKey, 50.);
                    AnchorPane.setTopAnchor(lblAssignedKey, 11.);

                    //Кнопка назначения комбинации с иконкой
                    FontIcon iconKeyAssign = new FontIcon();
                    JFXButton btnKeyAssign = new JFXButton();
                    iconKeyAssign.setIconLiteral("anto-gold");
                    iconKeyAssign.setIconSize(20);
                    iconKeyAssign.setIconColor(Color.web("#EEEEEE"));
                    btnKeyAssign.setGraphic(iconKeyAssign);
                    AnchorPane.setRightAnchor(btnKeyAssign, -2.);
                    AnchorPane.setTopAnchor(btnKeyAssign, 5.);
                    btnKeyAssign.setOnAction(event -> {
                        DataModel.getDataModel().setChosenCommand(item);
                        WindowsManager.getInstance().openAssignKeyWindow();
                    });

                    AnchorPane root = new AnchorPane(btnActivate, lblCmdName, lblAssignedKey, btnKeyAssign);
                    setText(null);
                    setGraphic(root);
                }
            }
        });

        lv.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null){
                DataModel.getDataModel().setChosenCommand(newSelection);
            }
        });
    }
}