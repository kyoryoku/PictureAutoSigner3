package window.view;

import by.kirino.hotkeys3.KeyCombination;
import by.kirino.hotkeys3.KeyListener;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Command;
import model.DataModel;
import org.kordamp.ikonli.javafx.FontIcon;
import window.Window;
import window.WindowType;
import window.WindowsManager;
import window.view.components.buttons.AddButton;
import window.view.components.buttons.InfoButton;
import window.view.components.buttons.RemoveButton;
import window.view.components.buttons.ProfileButton;

public class MainView implements Window {

    private Stage stage;
    private Scene scene;

    @Override
    public void show() {



        ListView<Command> lv = new ListView<>();
        HBox btnBox = new HBox(5);

         //КНОПКА "Добавить"
        AddButton btnAdd = new AddButton(btnBox);
        btnAdd.setOnAction(actionEvent -> {
            DataModel.getDataModel().addCommand(new Command());
        });

        //КНОПКА "УДАЛИТЬ"
        RemoveButton btnRemove = new RemoveButton(btnBox);
        btnRemove.setOnAction(actionEvent -> {
            if (DataModel.getDataModel().getChosenCommand() != null ||
                    DataModel.getDataModel().getCommands().size() != 0)
            {
                DataModel.getDataModel().removeCommand(
                        DataModel.getDataModel().getChosenCommand()
                );
            }
        });

        //КНОПКА "НАСТРОЙКИ"
        ProfileButton btnSettings = new ProfileButton(btnBox);
        btnSettings.setOnAction(e -> {
            WindowsManager.open(WindowType.PROFILE);
        });

        //КНОПКА "Информация"
        InfoButton btnInfo = new InfoButton(btnBox);
        btnInfo.setOnAction(actionEvent -> {
            WindowsManager.open(WindowType.INFO);
        });

        btnBox.setAlignment(Pos.CENTER);
        AnchorPane.setLeftAnchor(btnBox, 0.);
        AnchorPane.setTopAnchor(btnBox, 0.);
        AnchorPane btnRoot = new AnchorPane(btnBox);
        AnchorPane.setLeftAnchor(btnRoot, 5.);
        AnchorPane.setTopAnchor(btnRoot, 5.);
        AnchorPane.setRightAnchor(btnRoot, 0.);


        //ЛИСТ ВЬЮ
        lv.setItems(DataModel.getDataModel().getCommands());
        lv.setCellFactory(param -> {
            return new ListCell<Command>(){
                @Override
                protected void updateItem(Command item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty){
                        setText(null);
                        setGraphic(null);
                    } else {

                        //Создаем элементы
                        CheckBox checkBox = new CheckBox();
                        Label lblCmdName = new Label();
                        Label lblCmdCombination = new Label();
                        FontIcon iconSettings = new FontIcon();
                        Button btnSettings = new Button();

                        //ЧЕКБОКС для отключения команды
                        checkBox.selectedProperty().bindBidirectional(item.activeProperty());
                        AnchorPane.setLeftAnchor(checkBox, 0.);

                        //ЛЕЙБЛ "имя команды"
                        lblCmdName.textProperty().bind(item.nameProperty());
                        lblCmdName.disableProperty().bind(item.activeProperty().not());
                        AnchorPane.setLeftAnchor(lblCmdName, 0.);

                        //ЛЕЙБЛ "назначенная комбинация"
                        lblCmdCombination.textProperty().bindBidirectional(item.keyCombinationStringProperty());
                        lblCmdCombination.disableProperty().bind(item.activeProperty().not());
                        AnchorPane.setRightAnchor(lblCmdCombination, 0.);

                        //настройка контейнера для лейблов
                        AnchorPane lblContainer = new AnchorPane(lblCmdName, lblCmdCombination);
                        AnchorPane.setLeftAnchor(lblContainer, 30.);
                        AnchorPane.setRightAnchor(lblContainer, 50.);
                        AnchorPane.setTopAnchor(lblContainer, 3.);

                        //КНОПКА "назначить комбинацию"
                        iconSettings.setIconLiteral("anto-gold");
                        iconSettings.setIconSize(20);
                        iconSettings.setIconColor(Color.web("#0675ba"));
                        btnSettings.setGraphic(iconSettings);
                        AnchorPane.setRightAnchor(btnSettings, 0.);
                        btnSettings.setOnAction(event -> {
                            DataModel.getDataModel().setChosenCommand(item);
                            WindowsManager.open(WindowType.HOTKEY_ASSIGN);
                        });


                        //подсказка
//                        Tooltip tooltip = new Tooltip("подсказка");
//                        tooltip.setShowDelay(new Duration(100));
//                        tooltip.setShowDuration(new Duration(10000));
//                        this.setTooltip(tooltip);

                        //Настройка МЕЙН-КОНТЕЙНЕРА ячейки листвью
                        AnchorPane root = new AnchorPane(checkBox, lblContainer, btnSettings);
                        root.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                        setText(null);
                        setGraphic(root);



                    }
                }
            };
        }) ;

        lv.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null){
                DataModel.getDataModel().setChosenCommand(newSelection);
            }
        });

        lv.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent click) {
                if (click.getClickCount() == 2 && lv.getSelectionModel().getSelectedItem() != null) {
                    WindowsManager.open(WindowType.COMMAND_SETTINGS);
                }
            }
        });
        AnchorPane.setLeftAnchor(lv, 0.);
        AnchorPane.setRightAnchor(lv, 0.);
        AnchorPane.setBottomAnchor(lv, 0.);
        AnchorPane.setTopAnchor(lv, 0.);

        AnchorPane lvRoot = new AnchorPane(lv);
        AnchorPane.setLeftAnchor(lvRoot, 5.);
        AnchorPane.setTopAnchor(lvRoot, 40.);
        AnchorPane.setRightAnchor(lvRoot, 5.);
        AnchorPane.setBottomAnchor(lvRoot, 5.);

        //НАСТРОЙКА МЕЙН КОНТЕЙНЕРА
        AnchorPane root = new AnchorPane(btnRoot, lvRoot);
        scene = new Scene(root, 800,500);
        stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Нумератор рисунков V2");
        stage.setResizable(true);
        stage.show();


        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("1123123213");
        alert.setHeaderText(null);
        alert.setContentText("123123123123");
        alert.initOwner(stage.getOwner());
        alert.show();
    }

    @Override
    public void close() {
        stage.close();
    }

    @Override
    public String getName() {
        return "mainView";
    }

}
