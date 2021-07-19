package window.view.components.buttons;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.kordamp.ikonli.javafx.FontIcon;
import window.Theme;

public class ExportButton extends Button {

    public ExportButton(Pane rootPane) {

        FontIcon icon = new FontIcon("anto-export");
        icon.setIconSize(Theme.ICON_SIZE);
        icon.setIconColor(Theme.COLOR_BLUE);

        this.setMinWidth(Theme.BTN_WIDTH);
        this.setText("Сохранить");
        this.setGraphic(icon);

        rootPane.getChildren().add(this);
    }
}
