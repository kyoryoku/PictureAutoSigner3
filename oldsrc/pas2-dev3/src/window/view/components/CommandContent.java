package window.view.components;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import model.DataModel;
import window.view.components.controls.SearchComboBox;

public class CommandContent {

    TextArea sourceTaForScb;

    public CommandContent(AnchorPane pane) {

        pane.getChildren().clear();

        Label lbl_str1 = new Label();
        TextArea ta_str1 = new TextArea();
        ta_str1.setWrapText(true);
        ta_str1.setFont(Font.font("Verdana", 16));
        Label lbl_str2 = new Label();
        TextArea ta_str2 = new TextArea();
        ta_str2.setWrapText(true);
        ta_str2.setFont(Font.font("Verdana", 16));

        SearchComboBox scb = new SearchComboBox(pane);
        pane.getChildren().addAll(scb);
        AnchorPane.setRightAnchor(scb, 0.);
        AnchorPane.setTopAnchor(scb, 5.);

        //переход из формы в строку поиска по нажатию CTRL+F
        sourceTaForScb = ta_str1;
        ta_str1.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.isControlDown()){
                    if (keyEvent.getCode() == KeyCode.F){
                        sourceTaForScb = ta_str1;
                        scb.requestFocus();
                    }
                }
            }
        });

        ta_str2.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.isControlDown()){
                    if (keyEvent.getCode() == KeyCode.F){
                        sourceTaForScb = ta_str2;
                        scb.requestFocus();
                    }
                }
            }
        });

        //переход из строки поиска в форму
        scb.getEditor().addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                //добавляем в форму ввыбранное в строке поиска
                if (event.getCode() == KeyCode.ENTER){

                    String txtBefore = sourceTaForScb.getText().substring(0, sourceTaForScb.getCaretPosition());
                    String txtAfter = sourceTaForScb.getText().substring(sourceTaForScb.getCaretPosition(), sourceTaForScb.getText().length());
                    String txtSCB = "" + scb.getSelectionModel().getSelectedItem();
                    String txt = txtBefore + txtSCB + txtAfter;

                    sourceTaForScb.setText(txt);
                    sourceTaForScb.positionCaret(txtBefore.length() + txtSCB.length());
                    sourceTaForScb.requestFocus();
                    return;
                }

                //переходим обратно в форму без добавления по нажатию CTRL+E
                if (event.isControlDown()){
                    if (event.getCode() == KeyCode.E){
                        sourceTaForScb.requestFocus();
                        scb.close();
                        return;
                    }
                }
            }
        });

        //В зависимости от типа команды отображаем разное содержимое формы
        switch(DataModel.getDataModel().getChosenCommand().getType()){

            case NOT_SET :{
                lbl_str1.setText("Для определения параметров команды выберите ее тип!");

                pane.getChildren().addAll(lbl_str1);
                AnchorPane.setLeftAnchor(lbl_str1, 0.);
                AnchorPane.setTopAnchor(lbl_str1, 10.);
                scb.setVisible(false);

                break;
            }
            case CHANGE_COUNTER:{
                lbl_str1.setText("Что изменить?");

                pane.getChildren().addAll(lbl_str1, ta_str1);
                AnchorPane.setLeftAnchor(lbl_str1, 0.);
                AnchorPane.setLeftAnchor(ta_str1, 0.);
                AnchorPane.setTopAnchor(lbl_str1, 10.);
                AnchorPane.setTopAnchor(ta_str1, 30.);
                AnchorPane.setRightAnchor(ta_str1, 0.);
                AnchorPane.setBottomAnchor(ta_str1, 0.);

                break;
            }
            case PAST_TEXT: {
                lbl_str1.setText("Что вставить?");

                pane.getChildren().addAll(lbl_str1, ta_str1);
                AnchorPane.setLeftAnchor(lbl_str1, 0.);
                AnchorPane.setLeftAnchor(ta_str1, 0.);
                AnchorPane.setTopAnchor(lbl_str1, 10.);
                AnchorPane.setTopAnchor(ta_str1, 30.);
                AnchorPane.setRightAnchor(ta_str1, 0.);
                AnchorPane.setBottomAnchor(ta_str1, 0.);

                break;
            }
            case REPLACE: {
                lbl_str1.setText("Что заменять?");
                lbl_str2.setText("На что заменить?");

                pane.getChildren().addAll(lbl_str1, ta_str1, lbl_str2, ta_str2);
                AnchorPane.setLeftAnchor(lbl_str1, 0.);
                AnchorPane.setLeftAnchor(ta_str1, 0.);
                AnchorPane.setLeftAnchor(lbl_str2, 0.);
                AnchorPane.setLeftAnchor(ta_str2, 0.);
                AnchorPane.setTopAnchor(lbl_str1, 10.);
                AnchorPane.setTopAnchor(ta_str1, 30.);
                AnchorPane.setBottomAnchor(ta_str1, 110.);
                AnchorPane.setTopAnchor(lbl_str2, 120.);
                AnchorPane.setTopAnchor(ta_str2, 140.);
                AnchorPane.setBottomAnchor(ta_str2, 0.);
                AnchorPane.setRightAnchor(ta_str1, 0.);
                AnchorPane.setRightAnchor(ta_str2, 0.);

                break;
            }
        }

        //привязываем содержимое форм к полям команды
        ta_str1.textProperty().bindBidirectional(DataModel.getDataModel().getChosenCommand().str1Property());
        ta_str2.textProperty().bindBidirectional(DataModel.getDataModel().getChosenCommand().str2Property());
    }
}
