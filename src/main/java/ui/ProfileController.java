package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import model.DataModel;
import model.exceptions.LoadProfileException;
import model.exceptions.SaveProfileException;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController {
    @FXML private ResourceBundle resources;
    @FXML private URL location;

    @FXML private JFXButton btnSave;
    @FXML private JFXButton btnLoad;
    @FXML private Label profilePath;
    @FXML private Label hint;
    @FXML private JFXCheckBox autoLoad;

    @FXML void initialize() {
        initializeButtons();

        profilePath.textProperty().bindBidirectional(DataModel.getDataModel().getProfile().profilePathProperty());
    }

    private void initializeButtons() {
        int iconSize = 20;
        Color iconColor = Color.web("#EEEEEE");

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


    @FXML private void handleBtnSave (ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить профиль в файл...");
        File file = fileChooser.showSaveDialog(btnSave.getScene().getWindow());
        if (file != null){
            profilePath.setText(file.getAbsolutePath());
            try {
                hint.setText("Профиль успешно сохранен в файл!");
                DataModel.getDataModel().getProfile().saveProfile();

            } catch (SaveProfileException e) {
                System.out.println(e.getMessage());
                hint.setText("Ошибка при записи профиля!");
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
                hint.setText("Профиль успешно загружен из файла!");
                DataModel.getDataModel().getProfile().loadProfile();
            } catch (LoadProfileException e){
                System.out.println(e.getMessage());
                hint.setText("Ошибка при чтении профиля!");
            }
        }
    }

    @FXML private void handleChangeAutoLoad (ActionEvent event){

    }
}
