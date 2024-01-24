package com.app.quiz;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static com.app.quiz.quizController.setUser;

public class stuLoginController {

    @FXML
    private Button LoginBtn, backBtn;

    @FXML
    private Button RegToLogBtn;

    @FXML
    private TextField UnameField;

    @FXML
    private PasswordField PassField;

    @FXML
    private Label popup;



    public void loginBtnOnAction() throws IOException{
        if(!UnameField.getText().isEmpty() && !PassField.getText().isEmpty()){
            if(validate()){
                SelectTopic.setUser(UnameField.getText());
                Parent root = FXMLLoader.load(getClass().getResource("selectTopic.fxml"));
                Stage stage = (Stage) LoginBtn.getScene().getWindow();
                stage.setScene(new Scene(root));
            }
        }
        else{
            popup.setText("Enter Username and Password");
        }
    }

    public void regToLogOnAction() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("stu_register.fxml"));
        Stage stage = (Stage) RegToLogBtn.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    public boolean validate(){
        databaseConnection connectNow = new databaseConnection();
        Connection connectDB = connectNow.getDatabaseLink();

        String verifyLogin = "SELECT count(1) FROM student_account WHERE username = '" + UnameField.getText() + "' AND password = '" + PassField.getText() + "'";

        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()){
                if(queryResult.getInt(1) == 1){
                    popup.setText("Congo");
                    return true;
                }else{
                    popup.setText("Invalid Credentials");
                    return false;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    return false;
    }

    public void backBtnOnAction() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
