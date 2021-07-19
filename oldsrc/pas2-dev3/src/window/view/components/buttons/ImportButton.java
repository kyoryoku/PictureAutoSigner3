package window.view.components.buttons;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.kordamp.ikonli.javafx.FontIcon;
import window.Theme;

public class ImportButton extends Button {

    public ImportButton(Pane rootPane) {

        FontIcon icon = new FontIcon("anto-import");
        icon.setIconSize(Theme.ICON_SIZE);
        icon.setIconColor(Theme.COLOR_BLUE);

        this.setMinWidth(Theme.BTN_WIDTH);
        this.setText("Загрузить");
        this.setGraphic(icon);

        rootPane.getChildren().add(this);
    }
}
