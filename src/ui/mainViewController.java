package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Command;
import model.DataModel;
import org.kordamp.ikonli.javafx.FontIcon;
import ui.windows.WindowType;
import ui.windows.Windows;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class mainViewController implements Windowed {

    @FXML private ResourceBundle resources;
    @FXML private URL location;

    @FXML private JFXListView<Command> lv;
    @FXML private JFXButton btnAdd;
    @FXML private JFXButton btnRemove;
    @FXML private JFXButton btnProfile;
    @FXML private JFXButton btnHelp;

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
        Windows.getInstance().openWindow(WindowType.PROFILE);
    }

    //обработчик нажатия на кнопку Помощь
    @FXML void handleBtnHelp(ActionEvent event) {
        Windows.getInstance().openWindow(WindowType.HELP);
    }

    //обработчик кликов по листвью
    @FXML void handleLv(MouseEvent event) {
        if (event.getClickCount() == 2 && lv.getSelectionModel().getSelectedItem() != null) {
            Windows.getInstance().openWindow(WindowType.COMMAND);
        }
    }

    @FXML void initialize() {
        initializeButtons();
        initializeListView();
    }

    //настройка кнпопок
    private void initializeButtons(){
        int iconSize = 20;
        Color iconColor = Color.web("#0675ba");
        Color iconColorAlert = Color.web("#ba2106");

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
        FontIcon helpIcon = new FontIcon("anto-info-circle");
        helpIcon.setIconSize(iconSize);
        helpIcon.setIconColor(iconColor);
        btnHelp.setGraphic(helpIcon);
    }

    //Настройка листвью
    private void initializeListView(){
        lv.setItems(DataModel.getDataModel().getCommands());

        //настройка содержимого строк лист вью
        lv.setCellFactory(param -> {
            return new ListCell<Command>(){
                @Override
                protected void updateItem(Command item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty){
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
                        iconKeyAssign.setIconColor(Color.web("#0675ba"));
                        btnKeyAssign.setGraphic(iconKeyAssign);
                        AnchorPane.setRightAnchor(btnKeyAssign, -2.);
                        AnchorPane.setTopAnchor(btnKeyAssign, 5.);
                        btnKeyAssign.setOnAction(event -> {

                        });

                        AnchorPane root = new AnchorPane(btnActivate, lblCmdName, lblAssignedKey, btnKeyAssign);
                        setText(null);
                        setGraphic(root);
                    }
                }
            };
        });

        lv.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null){
                DataModel.getDataModel().setChosenCommand(newSelection);
            }
        });
    }

    private Stage stage;
    @Override
    public void show() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("mainView.fxml"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        stage = new Stage();
        stage.setTitle("Picture Auto Signer 3");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
        stage.setOnCloseRequest(e -> {
            Windows.getInstance().closeWindow(WindowType.MAIN);
        });
    }

    @Override
    public void close() {
        stage.close();
    }
}