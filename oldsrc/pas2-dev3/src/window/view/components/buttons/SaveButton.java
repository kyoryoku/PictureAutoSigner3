package window.view.components.buttons;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.kordamp.ikonli.javafx.FontIcon;
import window.Theme;

public class SaveButton extends Button {

    public SaveButton(Pane rootPane) {

        FontIcon icon = new FontIcon("anto-save");
        icon.setIconSize(Theme.ICON_SIZE);
        icon.setIconColor(Theme.COLOR_BLUE);

        this.setMinWidth(Theme.BTN_WIDTH);
        this.setText("Сохранить");
        this.setGraphic(icon);

        rootPane.getChildren().add(this);
    }
}
