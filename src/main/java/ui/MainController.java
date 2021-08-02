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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import model.Command;
import model.DataModel;
import model.exceptions.LoadProfileException;
import model.exceptions.SaveProfileException;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController {

    @FXML private ResourceBundle resources;
    @FXML private URL location;

    @FXML private JFXListView<Command> lv;
    @FXML private JFXButton btnAdd;
    @FXML private JFXButton btnRemove;
    @FXML private JFXButton btnHelp;
    @FXML private JFXButton btnSave;
    @FXML private JFXButton btnLoad;
    @FXML private Label profilePath;

    @FXML private JFXToggleButton btnDisableAll;



    @FXML void handleBtnDisableAll(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY){
            if (btnDisableAll.isSelected()){
                DataModel.getDataModel().enableAll();
            } else {
                DataModel.getDataModel().disableAll();
            }
        }

        if (event.getButton() == MouseButton.SECONDARY){
            System.out.println("r");
            if (btnDisableAll.isSelected()){
                DataModel.getDataModel().pause();
                btnDisableAll.setSelected(false);
            } else {
                DataModel.getDataModel().unpause();
                btnDisableAll.setSelected(true);
            }
        }



    }


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

    //обработчик нажатия на кнопку Помощь
    @FXML void handleBtnHelp(ActionEvent event) {
        WindowsManager.getInstance().openHelpWindow();
    }


    @FXML private void handleBtnSave (ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить профиль в файл...");
        File file = fileChooser.showSaveDialog(btnSave.getScene().getWindow());
        if (file != null){
            profilePath.setText(file.getAbsolutePath());
            try {
                //hint.setText("Профиль успешно сохранен в файл!");
                DataModel.getDataModel().getProfile().saveProfile();

            } catch (SaveProfileException e) {
                System.out.println(e.getMessage());
                //hint.setText("Ошибка при записи профиля!");
            }

        }
    }

    @FXML private void handleBtnLoad (ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Загрузить профиль из файла...");
        File file = fileChooser.showOpenDialog(btnLoad.getScene().getWindow());
        if (file != null){
            profilePath.setText(file.getAbsolutePath());
            try {
                //hint.setText("Профиль успешно загружен из файла!");
                DataModel.getDataModel().getProfile().loadProfile();
            } catch (LoadProfileException e){
                System.out.println(e.getMessage());
                //hint.setText("Ошибка при чтении профиля!");
            }
        }
    }

    //обработчик кликов по ListView
    @FXML void handleLv(MouseEvent event) {
        if (event.getClickCount() == 2 && lv.getSelectionModel().getSelectedItem() != null) {
            WindowsManager.getInstance().openCommandWindow();
        }
    }

    @FXML void initialize() {
        profilePath.textProperty().bindBidirectional(DataModel.getDataModel().getProfile().profilePathProperty());

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