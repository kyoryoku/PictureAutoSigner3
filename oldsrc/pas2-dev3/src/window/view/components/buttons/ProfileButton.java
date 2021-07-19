package window.view.components.buttons;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.kordamp.ikonli.javafx.FontIcon;
import window.Theme;

public class ProfileButton extends Button {

    public ProfileButton(Pane rootPane) {

        FontIcon icon = new FontIcon("anto-file-ppt");
        icon.setIconSize(Theme.ICON_SIZE);
        icon.setIconColor(Theme.COLOR_BLUE);

        this.setMinWidth(Theme.BTN_WIDTH);
        this.setText("Профиль");
        this.setGraphic(icon);

        rootPane.getChildren().add(this);
    }
}
