package sample;


import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;

    @FXML
    private JFXListView<String> lv;

    @FXML
    void initialize() {
        ObservableList<String> list = FXCollections.observableArrayList();

        list.add("aqweqwe");
        list.add("asdsdasd");
        list.add("1231231");
        list.add("aqweasda123123qwe");
        lv.setItems(list);



    }
}
