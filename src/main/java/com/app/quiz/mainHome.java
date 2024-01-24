package com.app.quiz;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class mainHome {
    @FXML
    private Button stuLogBtn;

    @FXML
    private Button adminLogBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private Button stuRegBtn;

    @FXML
    private void initialize(){
        stuLogBtn.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("stu_login.fxml"));
                Stage stage = (Stage) stuLogBtn.getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        adminLogBtn.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("admin_login.fxml"));
                Stage stage = (Stage) stuLogBtn.getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        stuRegBtn.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("stu_register.fxml"));
                Stage stage = (Stage) stuLogBtn.getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        exitBtn.setOnAction(event -> {
            Stage stage = (Stage) exitBtn.getScene().getWindow();
            stage.close();
        });
    }
}
