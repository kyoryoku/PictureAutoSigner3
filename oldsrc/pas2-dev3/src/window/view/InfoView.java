package window.view;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import window.Window;
import window.WindowType;
import window.WindowsManager;

import java.net.URL;

public class InfoView implements Window {

    private Stage stage;
    private Scene scene;

    @Override
    public void show() {
        stage = new Stage();

        URL url = this.getClass().getResource("./help/index.html");
        WebView browser = new WebView();
        WebEngine webEngine = browser.getEngine();
        webEngine.load(url.toString());


        scene = new Scene(browser, 1000, 600);
        stage.setScene(scene);
        stage.setTitle("Информация");
        stage.setResizable(true);
        stage.setOnCloseRequest(e->{
            WindowsManager.close(WindowType.INFO);
        });
        stage.show();
    }

    @Override
    public void close() {
        stage.close();
    }

    @Override
    public String getName() {
        return "infoView";
    }
}
