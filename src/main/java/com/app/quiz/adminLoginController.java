package com.app.quiz;

import javafx.event.ActionEvent;
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
import java.util.Objects;

public class adminLoginController {
    @FXML
    private Button LoginBtn, backBtn;

    @FXML
    private TextField UnameField;

    @FXML
    private PasswordField PassField;

    @FXML
    private Label successPrompt;

    public void loginBtnOnAction(ActionEvent event){
        if(!UnameField.getText().isEmpty() && !PassField.getText().isEmpty()){
            validate();
        }
        else{
            successPrompt.setText("Enter Username and Password");
        }
    }

    public void validate(){
        databaseConnection connectNow = new databaseConnection();
        Connection connectDB = connectNow.getDatabaseLink();

        String verifyLogin = "SELECT count(1) FROM admin_account WHERE username = '" + UnameField.getText() + "' AND password = '" + PassField.getText() + "'";

        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()){
                if(queryResult.getInt(1) == 1){
                    //successPrompt.setText("Congo");
                    sendToHome();
                }else{
                    successPrompt.setText("Invalid Credentials");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void sendToHome() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("quizHomeAdmin.fxml")));
        Stage stage = (Stage) LoginBtn.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void backBtnOnAction() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
