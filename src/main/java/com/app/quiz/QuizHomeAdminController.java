package com.app.quiz;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class QuizHomeAdminController {

    @FXML
    private Button InsertBtn, DeleteBtn, homeBtn;

    @FXML
    private AnchorPane InsertPane, DeletePane;

    @FXML
    private TextArea quesINP, Inp1, Inp2, Inp3, Inp4;

    @FXML
    private RadioButton ORB1, ORB2, ORB3, ORB4;

    @FXML
    private Label successLabel;

    @FXML
    private TextField getQid, setQid;

    @FXML
    private TextArea quesOut, Out1, Out2, Out3, Out4;

    @FXML
    private Button deleteBtn2;


    public void BtnOnAction(){
        InsertBtn.setOnAction(actionEvent -> {
            InsertPane.setVisible(true);
            DeletePane.setVisible(false);
        });

        DeleteBtn.setOnAction(actionEvent -> {
            InsertPane.setVisible(false);
            DeletePane.setVisible(true);
        });
    }

    public void submitBtnOnAction(){
        ToggleGroup group = ORB1.getToggleGroup();
        RadioButton selected = (RadioButton) group.getSelectedToggle();
        if (!quesINP.getText().isBlank() && !Inp1.getText().isBlank() && !Inp2.getText().isBlank() && !Inp3.getText().isBlank() && !Inp4.getText().isBlank() && selected != null){
            insertQuestion();
        }
        else{
            successLabel.setText("Fields cannot be Empty");
        }
    }

    private String getCorrectAns(){
        String ans = "";
        if(ORB1.isSelected()){
            ans = Inp1.getText();
        }
        else if(ORB2.isSelected()){
            ans = Inp2.getText();
        }
        else if(ORB3.isSelected()){
            ans = Inp3.getText();
        }
        else if(ORB4.isSelected()){
            ans = Inp4.getText();
        }
        return ans;
    }

    public void insertQuestion(){
        databaseConnection ConnectNow = new databaseConnection();
        Connection ConnectDB = ConnectNow.getDatabaseLink();

        String quid = setQid.getText();
        String ques = quesINP.getText();
        String opt1 = Inp1.getText();
        String opt2 = Inp2.getText();
        String opt3 = Inp3.getText();
        String opt4 = Inp4.getText();
        String ans = getCorrectAns();

        String insertPrompt = "INSERT INTO questions(qid, ques, opt1, opt2, opt3, opt4, ans) VALUES";
        String valuePrompt = "('" + quid + "','" + ques + "','" + opt1 + "','" + opt2 + "','" + opt3 + "','" + opt4 + "','" + ans + "')";
        String query = insertPrompt + valuePrompt;

        try{
            Statement statement = ConnectDB.createStatement();
            statement.executeUpdate(query);
            successLabel.setText("Insertion Successful !");
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void deleteQuestion(){
        databaseConnection ConnectNow = new databaseConnection();
        Connection ConnectDB = ConnectNow.getDatabaseLink();

        String id = getQid.getText();

        try{
            Statement statement = ConnectDB.createStatement();
            statement.executeUpdate("DELETE FROM questions WHERE qid = " + id);
            successLabel.setText("Deletion Successful");
            quesOut.setText("");
            Out1.setText("");
            Out2.setText("");
            Out3.setText("");
            Out4.setText("");

            }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
        finally {
            deleteBtn2.setDisable(true);
        }
    }

    public void searchQuestion(){
        databaseConnection ConnectNow = new databaseConnection();
        Connection ConnectDB = ConnectNow.getDatabaseLink();

        String id = getQid.getText();

        try{
            Statement statement = ConnectDB.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM questions WHERE qid = '" + id + "'");
            if(rs.next()){
                quesOut.setText(rs.getString(2));
                Out1.setText(rs.getString(3));
                Out2.setText(rs.getString(4));
                Out3.setText(rs.getString(5));
                Out4.setText(rs.getString(6));
                getQid.setEditable(false);
                deleteBtn2.setDisable(false);
            }
            else {
                successLabel.setText("Question not Found !");
            }
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void homeBtnOnAction() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        Stage stage = (Stage) homeBtn.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

}
