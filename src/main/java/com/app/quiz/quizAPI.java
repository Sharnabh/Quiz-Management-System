package com.app.quiz;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.commons.text.StringEscapeUtils;

public class quizAPI {
    @FXML
    private Label mark, id, Time;
    @FXML
    private ToggleButton opt1, opt2, opt3, opt4;
    @FXML
    private Button nextBtn, submitBtn;
    @FXML
    private TextArea Ques;

    private int marks = 0, count = 0, sec = 0, min = 10, waitSec = 5;
    private String correctAnswer = "";
    private static String level;
    private static String category;
    private JSONArray results;
    private ToggleButton select;
    private static String user;

    private Timeline timer = new Timeline(
            new KeyFrame(Duration.ZERO, e -> updateTimer()),
            new KeyFrame(Duration.seconds(1))
    );
    private Timeline wait = new Timeline(
            new KeyFrame(Duration.ZERO, e -> updateWait()),
            new KeyFrame(Duration.seconds(1))
    );

    databaseConnection ConnectNow = new databaseConnection();
    Connection ConnectDB = ConnectNow.getDatabaseLink();

    private void updateWait(){
        if(waitSec>=0)
            waitSec--;

        if(select.getText().equals(correctAnswer)){
            select.setStyle("-fx-background-color: green;");
        }
        else
            select.setStyle("-fx-background-color: red;");

        if(waitSec == 0){
            wait.stop();
            select.setStyle(null);
            if(count != 10)question();
            waitSec = 5;
        }
    }

    private void updateTimer() {
        if (sec == 0) {
            min--;
            sec = 59;
        } else {
            sec--;
        }

        Time.setText(String.format("%02d:%02d", min, sec));

        // Stop the timer when it reaches 00:00
        if (min == 0 && sec == 0) {
            timer.stop();
            checkAnswer();
            setSubmitBtn();
        }
    }

    public static void setUser(String userName) {
        user = userName;
    }

    @FXML
    private void initialize() throws IOException, InterruptedException {

        mark.setText(String.valueOf(marks));
        JSONObject jsonObject = getQuestions();
        results = jsonObject.getJSONArray("results");

        ArrayList<String> choices = new ArrayList<>();

        JSONObject question = results.getJSONObject(0);

        String questionText = StringEscapeUtils.unescapeHtml4(question.getString("question"));

        correctAnswer = question.getString("correct_answer");
        choices.add(correctAnswer);
        JSONArray incorrectAnswers = question.getJSONArray("incorrect_answers");

        for (int j = 0; j < incorrectAnswers.length(); j++) {
            choices.add(incorrectAnswers.getString(j));
        }
        Collections.shuffle(choices);
        id.setText("1");
        setQues(questionText, choices);
        choices.clear();

        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();

    }

    private JSONObject getQuestions() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String URL = "";
        if(category.equals("any"))
            URL = "https://opentdb.com/api.php?amount=10"+ "&difficulty=" + level + "&type=multiple";
        else if (level.equals("any"))
            URL = "https://opentdb.com/api.php?amount=10&category=" + category + "&type=multiple";
        else
            URL = "https://opentdb.com/api.php?amount=10&category=" + category + "&difficulty=" + level + "&type=multiple";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String jsonResponse = response.body();
        return new JSONObject(jsonResponse);
    }

    public void setQues(String ques, ArrayList<String> ch){
        Ques.setText(ques);
        opt1.setText(ch.get(0));
        opt2.setText(ch.get(1));
        opt3.setText(ch.get(2));
        opt4.setText(ch.get(3));
    }

    public void checkAnswer(){
        ToggleGroup group = opt1.getToggleGroup();
        ToggleButton selectedBtn = (ToggleButton) group.getSelectedToggle();
        String userAnswer = selectedBtn.getText();
        select = selectedBtn;
        if(correctAnswer.equals(userAnswer)){
            marks++;
            mark.setText(String.valueOf(marks));
        }
        count++;
        if(count == 9){
            nextBtn.setDisable(true);
            nextBtn.setVisible(false);
            submitBtn.setVisible(true);
            submitBtn.setDisable(false);
        }
    }

    public void question(){
        ArrayList<String> choices = new ArrayList<>();
        JSONObject question = results.getJSONObject(count);

        String questionText = StringEscapeUtils.unescapeHtml4(question.getString("question"));

        correctAnswer = question.getString("correct_answer");
        choices.add(correctAnswer);
        JSONArray incorrectAnswers = question.getJSONArray("incorrect_answers");

        for (int j = 0; j < incorrectAnswers.length(); j++) {
            choices.add(incorrectAnswers.getString(j));
        }
        Collections.shuffle(choices);
        id.setText(String.valueOf(count + 1));
        setQues(questionText, choices);
        choices.clear();

    }

    public void nextBtnOnAction(){
        checkAnswer();
        wait.setCycleCount(Timeline.INDEFINITE);
        wait.play();
    }

    public void setSubmitBtn(){
        checkAnswer();
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
            wait.setCycleCount(Timeline.INDEFINITE);
            wait.play();
        }
    }

    public static void setLevel(String l){
        level = l;
    }

    public static void setCategory(String c){
        category = c;
    }

}
