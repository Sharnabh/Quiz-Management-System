package com.app.quiz;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HomeController {

    @FXML
    private Button playBtn;

    @FXML
    private void initialize() {

        playBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try {
                    Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.show();

                    Stage stage1 = (Stage) playBtn.getScene().getWindow();
                    stage1.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }



            }
        });

    }

}