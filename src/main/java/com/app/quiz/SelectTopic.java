package com.app.quiz;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

import static com.app.quiz.quizAPI.setLevel;
import static com.app.quiz.quizAPI.setCategory;

public class SelectTopic {

    @FXML
    private ChoiceBox cat, level;
    @FXML
    private Button submitBtn;
    @FXML
    private Label warning;
    @FXML
    private Button trivia, admin;
    @FXML
    private AnchorPane triviaPane;
    private static String user;

    String[] category, difficulty;

    public static void setUser(String userName) {
        user = userName;
    }

    @FXML
    private void initialize(){
        category = new String[]{"Any Category", "General Knowledge", "Entertainment: Books", "Entertainment: Film",
                "Entertainment: Music", "Entertainment: Musicals & Theatres", "Entertainment: Television",
                "Entertainment: Video Games", "Entertainment: Board Games", "Science & Nature",
                "Science: Computers", "Science: Mathematics", "Mythology", "Sports", "Geography", "History", "Politics",
                "Art", "Celebrities", "Animals", "Vehicles", "Entertainment: Comics", "Science: Gadgets",
                "Entertainment: Japanese Anime & Manga", "Entertainment: Cartoon & Animations"
        };

        difficulty = new String[]{"Any Difficulty", "Easy", "Medium", "Hard"};

        cat.setItems(FXCollections.observableArrayList(category));
        level.setItems(FXCollections.observableArrayList(difficulty));
    }

    public void setTriviaBtn(){
        triviaPane.setVisible(true);
    }

    public void setAdminBtn() throws IOException {
        triviaPane.setVisible(false);
        quizController.setUser(user);
        Parent root = FXMLLoader.load(getClass().getResource("quizSection.fxml"));
        Stage stage = (Stage) admin.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void setSubmitBtn() throws IOException {

        String categorySelected = (String) cat.getValue();
        String levelSelected = (String) level.getValue();

        if(cat.getValue() == null || level.getValue() == null){
            warning.setText("Please select a Category or Difficulty");
        } else if (categorySelected.equals("Any Category")) {
            setCategory("any");
            setLevel(levelSelected.toLowerCase());
            changeScene(submitBtn);

        } else if (levelSelected.equals("Any Difficulty")) {
            int index = -1;
            for (int i = 0; i < category.length; i++) {
                if (category[i].equals(categorySelected)) {
                    index = i;
                    break;
                }
            }

            int val = 8 + index;

            setLevel("any");
            setCategory(String.valueOf(val));
            changeScene(submitBtn);
        } else {
            int index = -1;
            for (int i = 0; i < category.length; i++) {
                if (category[i].equals(categorySelected)) {
                    index = i;
                    break;
                }
            }

            int val = 8 + index;

            setLevel(levelSelected.toLowerCase());
            setCategory(String.valueOf(val));
            changeScene(submitBtn);
        }

    }

    private void changeScene(Button Btn) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("apiQuizSection.fxml"));
        quizAPI.setUser(user);
        Stage stage = (Stage) Btn.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

}
