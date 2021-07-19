package window.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.DataModel;
import model.exceptions.LoadProfileException;
import model.exceptions.SaveProfileException;
import window.Window;
import window.WindowType;
import window.WindowsManager;
import window.view.components.buttons.AcceptButton;
import window.view.components.buttons.ExportButton;
import window.view.components.buttons.ImportButton;

import java.io.File;

public class ProfileView implements Window {

    private Stage stage;
    private Scene scene;

    @Override
    public void show() {
        stage = new Stage();
        AnchorPane anchorPane = new AnchorPane();

        Label lblProfilePath = new Label("Путь к профилю:");
        TextField tfProfilePath = new TextField();
        tfProfilePath.textProperty().bindBidirectional(DataModel.getDataModel().getProfile().profilePathProperty());

        Label lblImportExport = new Label("Загрузить / Сохранить профиль:");

        //Сохранить профиль в файл
        Button btnExportProfile = new ExportButton(anchorPane);
        btnExportProfile.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Сохранить профиль в файл...");
            File file = fileChooser.showSaveDialog(stage);
            if (file != null){
                tfProfilePath.setText(file.getAbsolutePath());
                try {
                    lblProfilePath.setText("Профиль успешно сохранен в файл: ");
                    DataModel.getDataModel().getProfile().saveProfile();

                } catch (SaveProfileException e) {
                    System.out.println(e.getMessage());
                    lblProfilePath.setText("Ошибка при записи профиля!");
                }

            }
        });

        //Загрузить профиль из файла
        Button btnImportProfile = new ImportButton(anchorPane);
        btnImportProfile.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Загрузить профиль из файла...");
            File file = fileChooser.showOpenDialog(stage);
            if (file != null){
                tfProfilePath.setText(file.getAbsolutePath());
                try {
                    lblProfilePath.setText("Профиль успешно загружен из файла: ");
                    DataModel.getDataModel().getProfile().loadProfile();
                } catch (LoadProfileException e){
                    System.out.println(e.getMessage());
                    lblProfilePath.setText("Ошибка при чтении профиля!");
                }
            }
        });

        AcceptButton btnAccept = new AcceptButton(anchorPane);
        btnAccept.setOnAction(actionEvent -> {
            WindowsManager.close(WindowType.PROFILE);
        });


        anchorPane.getChildren().addAll(lblProfilePath, tfProfilePath, lblImportExport);

        AnchorPane.setLeftAnchor(lblImportExport, 5.);
        AnchorPane.setTopAnchor(lblImportExport, 10.);

        AnchorPane.setLeftAnchor(lblProfilePath, 5.);
        AnchorPane.setTopAnchor(lblProfilePath, 80.);

        AnchorPane.setLeftAnchor(tfProfilePath, 5.);
        AnchorPane.setRightAnchor(tfProfilePath, 5.);
        AnchorPane.setTopAnchor(tfProfilePath, 100.);

        AnchorPane.setLeftAnchor(btnImportProfile, 5.);
        AnchorPane.setTopAnchor(btnImportProfile, 30.);

        AnchorPane.setLeftAnchor(btnExportProfile, 135.);
        AnchorPane.setTopAnchor(btnExportProfile, 30.);

        AnchorPane.setLeftAnchor(btnAccept, 5.);
        AnchorPane.setBottomAnchor(btnAccept, 5.);



        scene = new Scene(anchorPane, 500, 200);
        stage.setScene(scene);
        stage.setTitle("Настройки");
        stage.setResizable(false);
        stage.setOnCloseRequest(e->{
            WindowsManager.close(WindowType.PROFILE);
        });
        stage.show();
    }

    @Override
    public void close() {
        stage.close();
    }

    @Override
    public String getName() {
        return "profileView";
    }
}
