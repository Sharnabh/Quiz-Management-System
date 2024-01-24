package com.app.quiz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class Result {

    @FXML
    private Label id, marks;

    @FXML
    private Button exitBtn, backBtn;

    private static String user;
    private static int score;

    public static void setUserMarks(String Uname, int marks){
        user = Uname;
        score = marks;
    }

    @FXML
    private void initialize(){
        id.setText(user);
        marks.setText(String.valueOf(score));
    }

    public void exitBtnOnAction(){
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }

    public void backBtnOnAction() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("selectTopic.fxml"));
        SelectTopic.setUser(user);
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.setScene(new Scene(root));
    }



}
