package window.view.components.controls;


import by.kirino.hotkeys3.Key;
import by.kirino.hotkeys3.Keys;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import model.Executor;

import java.util.Collections;

public class SearchComboBox extends ComboBox {

    public SearchComboBox(Pane pane){

        pane.getStylesheets().add(getClass().getResource("SearchComboBox.css").toExternalForm());

        ObservableList<String> fullList = FXCollections.observableArrayList();
        ObservableList<String> showList = FXCollections.observableArrayList();

        for(Key key : Keys.getKeyList()){
            fullList.add("#{" + key.getLongName() + "}");
        }
        for(String num: Executor.numerators.keySet()){
            fullList.add("${" + num + "}");
        }

        Collections.sort(fullList);

        fullList.add(0, "#{}");
        fullList.add(0, "${}");

        showList.addAll(fullList);

        this.setEditable(true);
        this.setItems(showList);

        this.setOnKeyReleased(e -> {

            if (e.getCode() == KeyCode.CONTROL || e.getCode().isArrowKey()){
                return;
            }

            if (e.getCode() == KeyCode.ESCAPE || e.getCode() == KeyCode.ENTER ){
                close();
                return;
            }

            if (showList.size() == 0){
                this.hide();
            } else {
                this.show();
            }

            String typedStr = this.getEditor().getText().toLowerCase();
            showList.clear();
            for (String str : fullList){
                if (str.toLowerCase().contains(typedStr)){
                    showList.add(str);
                }
            }
        });
    }


    public void close(){
        this.getEditor().clear();
        this.hide();
    }
}
