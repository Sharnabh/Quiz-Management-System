package com.app.quiz;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class quizController {

    @FXML
    private ToggleButton opt1, opt2, opt3, opt4;

    @FXML
    private Button submitBtn, nextBtn;
    @FXML
    private Label marksLabel, minute, id, warning;
    @FXML
    private TextArea ques;

    private Timeline timer = new Timeline(
            new KeyFrame(Duration.ZERO, e -> updateTimer()),
            new KeyFrame(Duration.seconds(1))
    );

    private void updateTimer() {
        if (sec == 0) {
            min--;
            sec = 59;
        } else {
            sec--;
        }

        minute.setText(String.format("%02d:%02d", min, sec));

        // Stop the timer when it reaches 00:00
        if (min == 0 && sec == 0) {
            timer.stop();
            answerCheck();
            submitBtnOnAction();
            // You can add additional actions here, such as displaying a message or performing other tasks
        }
    }

    databaseConnection ConnectNow = new databaseConnection();
    Connection ConnectDB = ConnectNow.getDatabaseLink();
    String answer = "";

    private int marks, count, sec, min;
    private static String user;

    public quizController(){
        marks = 0;
        sec = 0;
        min = 10;
        count = 1;
    }

    //Timer timer;
    @FXML
    private void initialize(){
        try{
            Statement statement = ConnectDB.createStatement();
            ResultSet rs= statement.executeQuery("SELECT * FROM questions WHERE qid = 1");
            while(rs.next()){
                id.setText(rs.getString(1));
                ques.setText(rs.getString(2));
                opt1.setText(rs.getString(3));
                opt2.setText(rs.getString(4));
                opt3.setText(rs.getString(5));
                opt4.setText(rs.getString(6));
                answer = rs.getString(7);
            }
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();

    }

    public static void setUser(String userName) {
        user = userName;
    }

    public void answerCheck(){
        ToggleGroup group = opt1.getToggleGroup();
        ToggleButton selected = (ToggleButton) group.getSelectedToggle();
        String selectedAnswer = selected.getText();

        if(answer.equals(selectedAnswer)){
            marks += 1;
            marksLabel.setText("" + marks);
        }
        ++count;
        if(count == 10){
            nextBtn.setVisible(false);
            submitBtn.setDisable(false);
        }
    }

    public void submitBtnOnAction(){
        answerCheck();
        try {
            Statement statement = ConnectDB.createStatement();
            statement.executeUpdate("UPDATE student_account SET marks = " + marks + " WHERE username = '" + user + "'");

            Result.setUserMarks(user, marks);
            Parent root = FXMLLoader.load(getClass().getResource("result.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();

            Stage stage1 = (Stage) submitBtn.getScene().getWindow();
            stage1.close();

        }catch (Exception e){
            e.printStackTrace();;
            e.getCause();
        }
        finally {
            timer.stop();
        }
    }

    public void nextBtnOnAction(){
        answerCheck();
        question();

    }

    public void question(){
        try{
            Statement statement = ConnectDB.createStatement();
            ResultSet rs= statement.executeQuery("SELECT * FROM questions WHERE qid = " + count);
            while(rs.next()){
                id.setText(rs.getString(1));
                ques.setText(rs.getString(2));
                opt1.setText(rs.getString(3));
                opt2.setText(rs.getString(4));
                opt3.setText(rs.getString(5));
                opt4.setText(rs.getString(6));
                answer = rs.getString(7);
            }
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }


}
