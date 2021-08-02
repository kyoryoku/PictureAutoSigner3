package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class WindowsManager {
    private static WindowsManager instance;
    public static WindowsManager getInstance(){
        if (instance == null){
            instance = new WindowsManager();
        }
        return instance;
    }
    private final ArrayList<String> openedWindows = new ArrayList<>();


    //========================================================
    //                  ГЛАВНОЕ ОКНО
    //========================================================
    private Stage mainViewStage;
    public void openMainWindow(){


        if (!openedWindows.contains("mainViewStage")){
            try {

                //создаем
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/mainView.fxml")));
                mainViewStage = new Stage();
                mainViewStage.setTitle("Picture Auto Signer 3");
                mainViewStage.setScene(new Scene(root, 1000, 600));
                mainViewStage.setOnCloseRequest(e -> closeMainWindow());

                //добавляем в открытые и показываем
                openedWindows.add("mainViewStage");
                mainViewStage.show();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    //закрываем сцену и удаляем из открытых
    public void closeMainWindow(){
        mainViewStage.close();
        openedWindows.remove("mainViewStage");
    }


    //========================================================
    //                  ОКНО НАСТРОЙКИ КОМАНДЫ
    //========================================================
    private Stage commandViewStage;
    public void openCommandWindow(){
        if (!openedWindows.contains("commandViewStage")){
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/commandView.fxml"));
                loader.load();
                Parent root = loader.getRoot();
                CommandController cc = loader.getController();
                commandViewStage = new Stage();
                commandViewStage.setTitle("Настройка команды");
                commandViewStage.setScene(new Scene(root, 800, 500));
                commandViewStage.setOnCloseRequest(e -> closeCommandWindow());

                openedWindows.add("commandViewStage");
                commandViewStage.show();

                cc.afterShow();

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public void closeCommandWindow(){
        commandViewStage.close();
        openedWindows.remove("commandViewStage");
    }


    //========================================================
    //                  ОКНО ПОМОЩИ
    //========================================================
    private Stage helpViewStage;
    public void openHelpWindow(){
        if(!openedWindows.contains("helpViewStage")){
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/helpView.fxml")));
                helpViewStage = new Stage();
                helpViewStage.setTitle("Справка и помощь");
                helpViewStage.setScene(new Scene(root, 600, 400));
                helpViewStage.setOnCloseRequest(e -> closeHelpWindow());

                openedWindows.add("helpViewStage");
                helpViewStage.show();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public void closeHelpWindow(){
        helpViewStage.close();
        openedWindows.remove("helpViewStage");
    }


    //========================================================
    //                  ОКНО ПРОФИЛЯ
    //========================================================
    private Stage profileViewStage;
    public void openProfileWindow(){
        if (!openedWindows.contains("profileViewStage")){
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/profileView.fxml")));
                profileViewStage = new Stage();
                profileViewStage.setTitle("Загрузка / сохранение профиля");
                profileViewStage.setScene(new Scene(root, 600, 400));
                profileViewStage.setOnCloseRequest(e -> closeProfileWindow());

                openedWindows.add("profileViewStage");
                profileViewStage.show();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public void closeProfileWindow(){
        profileViewStage.close();
        openedWindows.remove("profileViewStage");
    }


    //========================================================
    //                  диалоговое ОКНО назначения клавиши
    //========================================================
    private Stage assignKeyViewStage;
    public void openAssignKeyWindow(){
        if (!openedWindows.contains("assignKeyViewStage")){
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/assignKeyView.fxml")));
                assignKeyViewStage = new Stage(StageStyle.UNDECORATED);
                assignKeyViewStage.setTitle("Назначение комбинации");
                assignKeyViewStage.setScene(new Scene(root, 375, 170));
                assignKeyViewStage.setOnCloseRequest(e -> closeAssignKeyWindow());

                openedWindows.add("assignKeyViewStage");
                assignKeyViewStage.show();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public void closeAssignKeyWindow(){
        assignKeyViewStage.close();
        openedWindows.remove("assignKeyViewStage");
    }
}
