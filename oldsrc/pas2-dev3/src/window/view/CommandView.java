package window.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Command;
import model.CommandType;
import model.DataModel;
import window.Window;
import window.WindowType;
import window.WindowsManager;
import window.view.components.buttons.AcceptButton;
import window.view.components.buttons.CancelButton;
import window.view.components.CommandContent;


public class CommandView implements Window {

    private Stage stage;
    private Scene scene;
    private Command oldCommand;

    public CommandView() {

    }


    @Override
    public void show() {
        saveOldCommandValues();
        stage = new Stage();
        stage.setTitle("Настройка команды \"" + DataModel.getDataModel().getChosenCommand().getName() + "\"" );
        stage.setResizable(false);

        GridPane grid = new GridPane();
        AnchorPane content = new AnchorPane();
//        grid.setGridLinesVisible(true);
        CommandContent cc = new CommandContent(content);

        Label lbl_name = new Label("Имя команды:");
        TextField tf_name  = new TextField();
        tf_name.textProperty().bindBidirectional(DataModel.getDataModel().getChosenCommand().nameProperty());

        Label lbl_type = new Label("Тип команды:");
        ComboBox<CommandType> cb_type  = new ComboBox<>();
        cb_type.getItems().setAll(CommandType.values());
        cb_type.setMaxWidth(Double.MAX_VALUE);
        cb_type.valueProperty().bindBidirectional(DataModel.getDataModel().getChosenCommand().typeProperty());
        cb_type.valueProperty().addListener(new ChangeListener<CommandType>() {
            @Override
            public void changed(ObservableValue<? extends CommandType> observableValue, CommandType oldValue, CommandType newValue) {
                CommandContent cc = new CommandContent(content);
            }
        });

        grid.getColumnConstraints().add(new ColumnConstraints(590));
        grid.getRowConstraints().add(new RowConstraints(20));
        grid.add(lbl_name, 0, 0);
        grid.getRowConstraints().add(new RowConstraints(30));
        grid.add(tf_name, 0, 1);
        grid.getRowConstraints().add(new RowConstraints(20));
        grid.add(new Label(), 0, 2);
        grid.getRowConstraints().add(new RowConstraints(20));
        grid.add(lbl_type, 0, 3);
        grid.getRowConstraints().add(new RowConstraints(30));
        grid.add(cb_type, 0, 4);
        grid.getRowConstraints().add(new RowConstraints(220));
        grid.add(content, 0, 5);


        HBox hbxBtn = new HBox(50);
        hbxBtn.setAlignment(Pos.CENTER);

        AcceptButton btn_accept = new AcceptButton(hbxBtn);
        btn_accept.setOnAction(e -> {
            acceptButtonAction();
        });

        CancelButton btn_cancel = new CancelButton(hbxBtn);
        btn_cancel.setOnAction(event -> {
            cancelButtonAction();
        });


        AnchorPane apBtn = new AnchorPane(hbxBtn);
        AnchorPane.setLeftAnchor(apBtn, 175.);
        AnchorPane.setBottomAnchor(apBtn, 20.);

        AnchorPane.setRightAnchor(grid, 5.);
        AnchorPane.setLeftAnchor(grid, 5.);
        AnchorPane.setTopAnchor(grid, 5.);
        AnchorPane.setBottomAnchor(grid, 20.);

        AnchorPane root = new AnchorPane(grid, apBtn);
        AnchorPane.setLeftAnchor(grid, 5.);
        AnchorPane.setRightAnchor(grid, 5.);
        scene = new Scene(root, 600, 400);

        stage.setScene(scene);

        stage.show();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                cancelButtonAction();
            }
        });
    }

    private void saveOldCommandValues(){
//        oldCommand = new Command();
//        oldCommand.setType();ypeProperty(DataModel.getDataModel().getChosenCommand().getTypeProperty());
//        oldCommand.setNameProperty(DataModel.getDataModel().getChosenCommand().getNameProperty());
//        oldCommand.setStr1Property(DataModel.getDataModel().getChosenCommand().getStr1Property());
//        oldCommand.setStr2Property(DataModel.getDataModel().getChosenCommand().getStr2Property());
    }

    private void acceptButtonAction(){
        WindowsManager.close(WindowType.COMMAND_SETTINGS);
    }

    private void cancelButtonAction(){
//        DataModel.getDataModel().getChosenCommand().setNameProperty(oldCommand.getNameProperty());
//        DataModel.getDataModel().getChosenCommand().setTypeProperty(oldCommand.getTypeProperty());
//        DataModel.getDataModel().getChosenCommand().setStr1Property(oldCommand.getStr1Property());
//        DataModel.getDataModel().getChosenCommand().setStr2Property(oldCommand.getStr2Property());
//        WindowsManager.close(WindowType.COMMAND_SETTINGS);
    }


    @Override
    public void close() {
        stage.close();
    }

    @Override
    public String getName() {
        return "cmdView";
    }
}
