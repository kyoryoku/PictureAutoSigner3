package window.view;

import by.kirino.hotkeys3.Hotkey;
import by.kirino.hotkeys3.KeyCombination;
import by.kirino.hotkeys3.KeyListener;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Command;
import model.DataModel;
import window.Window;
import window.WindowType;
import window.WindowsManager;
import window.view.components.buttons.AcceptButton;
import window.view.components.buttons.CancelButton;

import javax.xml.crypto.Data;

public class HotkeyAssignView implements Window, KeyListener {

    private Stage stage;
    private Scene scene;
    private KeyCombination keyCombination;
    private Label lblKeyCombination;
    private Label lblMessage;

    @Override
    public void show() {

        Hotkey.getInstance().addListener(this);
        keyCombination = DataModel.getDataModel().getChosenCommand().getKeyCombination();

        lblMessage = new Label();
        lblMessage.setText("Нажмите горячую клавишу");
        lblKeyCombination = new Label();
        lblKeyCombination.setFont(Font.font(25));
        lblKeyCombination.setText(keyCombination.toShortString());

        VBox hbxLbl = new VBox(10, lblMessage, lblKeyCombination);
        hbxLbl.setAlignment(Pos.CENTER);
        AnchorPane.setLeftAnchor(hbxLbl, 0.);
        AnchorPane.setTopAnchor(hbxLbl, 0.);
        AnchorPane.setRightAnchor(hbxLbl, 0.);
        AnchorPane.setBottomAnchor(hbxLbl, 0.);
        AnchorPane lblRoot = new AnchorPane(hbxLbl);
        AnchorPane.setLeftAnchor(lblRoot, 5.);
        AnchorPane.setTopAnchor(lblRoot, 20.);
        AnchorPane.setRightAnchor(lblRoot, 5.);
        HBox hbxBtn = new HBox(25);
        hbxBtn.setAlignment(Pos.CENTER);
        AcceptButton acceptButton = new AcceptButton(hbxBtn);
        acceptButton.setOnAction(actionEvent -> {
            DataModel.getDataModel().getChosenCommand().setKeyCombination(this.keyCombination);
            WindowsManager.close(WindowType.HOTKEY_ASSIGN);
        });

        CancelButton cancelButton = new CancelButton(hbxBtn);
        cancelButton.setOnAction(e -> {
            WindowsManager.close(WindowType.HOTKEY_ASSIGN);
        });

        AnchorPane.setLeftAnchor(hbxBtn, 0.);
        AnchorPane.setTopAnchor(hbxBtn, 0.);
        AnchorPane.setRightAnchor(hbxBtn, 0.);
        AnchorPane.setBottomAnchor(hbxBtn, 0.);
        AnchorPane btnRoot = new AnchorPane(hbxBtn);
        AnchorPane.setLeftAnchor(btnRoot, 5.);
        AnchorPane.setRightAnchor(btnRoot, 5.);
        AnchorPane.setBottomAnchor(btnRoot, 25.);

        AnchorPane root = new AnchorPane(lblRoot, btnRoot);

        scene = new Scene(root, 300, 150);
        stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                WindowsManager.close(WindowType.HOTKEY_ASSIGN);
            }
        });

        stage.show();
    }

    @Override
    public void close() {
        stage.close();
        Hotkey.getInstance().removeListener(this);
    }

    @Override
    public String getName() {
        return "hotkeyView";
    }

    @Override
    public void handleHotkeyEvent(KeyCombination keyCombination) {
        for(Command cmd : DataModel.getDataModel().getCommands()){
            if (cmd.getKeyCombination().equals(keyCombination)){
                lblKeyCombination.setText("<уже используется>");
                lblMessage.setText("Придумайте другую комбинацию!");
                return;
            }
        }

        lblMessage.setText("Выбрана комбинация");
        this.keyCombination = keyCombination;
        lblKeyCombination.setText(keyCombination.toShortString());
    }
}
