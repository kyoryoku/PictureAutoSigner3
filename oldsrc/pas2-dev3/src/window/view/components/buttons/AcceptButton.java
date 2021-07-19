package window.view.components.buttons;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.kordamp.ikonli.javafx.FontIcon;
import window.Theme;

public class AcceptButton extends Button {

    public AcceptButton(Pane rootPane) {

        FontIcon icon = new FontIcon();
        icon.setIconLiteral("anto-check");
        icon.setIconSize(Theme.ICON_SIZE);
        icon.setIconColor(Theme.COLOR_BLUE);

        this.setMinWidth(Theme.BTN_WIDTH);
        this.setText("Принять");
        this.setGraphic(icon);

        rootPane.getChildren().add(this);
    }

}
