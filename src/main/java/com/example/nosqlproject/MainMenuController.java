package com.example.nosqlproject;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class MainMenuController {

    public void onShowDataButtonClick(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ShowDataMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1026, 563);
        Info.getInstance().getStage().setScene(scene);
    }

    public void onChangeDataButtonClick(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ShowDataMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1026, 563);
        Info.getInstance().getStage().setScene(scene);
    }
}
