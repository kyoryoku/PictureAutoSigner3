package window.view.components.buttons;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.kordamp.ikonli.javafx.FontIcon;
import window.Theme;

public class RemoveButton extends Button {

    public RemoveButton(Pane rootPane) {

        FontIcon icon = new FontIcon("anto-delete");
        icon.setIconSize(Theme.ICON_SIZE);
        icon.setIconColor(Theme.COLOR_RED);

        this.setMinWidth(Theme.BTN_WIDTH);
        this.setText("Удалить");
        this.setGraphic(icon);

        rootPane.getChildren().add(this);
    }
}
