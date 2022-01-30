package com.example.nosqlproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

public class SimpleTextAreaController {

    @FXML
    private TextArea textArea;

    public void returnMainMenuClick(MouseEvent event) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainMenu.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Info.getInstance().getStage().setScene(scene);
        }catch (Exception ignored){}
    }

    public void init(String text){
        textArea.setText(text);
    }
}
