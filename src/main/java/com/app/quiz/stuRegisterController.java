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
import java.sql.Statement;

public class stuRegisterController {

    @FXML
    private TextField FnameField, LnameField, UnameField;

    @FXML
    private PasswordField PassField, CPassField;

    @FXML
    private Button RegStuBtn, RegToLogBtn, backBtn;

    @FXML
    private Label SuccessPrompt, notSamePass, WarningPrompt;

    public void registerButtonOnAction(){
        if(PassField.getText().equals(CPassField.getText()) && (!PassField.getText().isBlank() && !CPassField.getText().isBlank())){
            notSamePass.setText("");
            WarningPrompt.setText("");
            registerUser();
        } else if (FnameField.getText().isBlank() || LnameField.getText().isBlank() || UnameField.getText().isBlank()) {
            WarningPrompt.setText("Fields cannot be Blank !");
        } else if (PassField.getText().isBlank() || CPassField.getText().isBlank()) {
            WarningPrompt.setText("Password cannot be Blank !");
        } else{
            WarningPrompt.setText("");
            notSamePass.setText("Password Doesn't Match !");
        }
    }

    public void RegToLogBtnOnAction() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("stu_login.fxml"));
        Stage stage = (Stage) RegToLogBtn.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void registerUser(){
        databaseConnection connectNow = new databaseConnection();
        Connection ConnectDB = connectNow.getDatabaseLink();

        String firstname = FnameField.getText();
        String lastname = LnameField.getText();
        String username = UnameField.getText();
        String password = PassField.getText();

        String insertPrompt = "INSERT INTO student_account (firstname, lastname, username, password) VALUES";
        String valuePrompt = "('" + firstname + "','" + lastname + "','" + username + "','" + password + "')";
        String finPrompt = insertPrompt + valuePrompt;

        try{
            Statement statement = ConnectDB.createStatement();
            statement.executeUpdate(finPrompt);
            SuccessPrompt.setText("User Creation Successful !");
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void backBtnOnAction() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        Stage stage = (Stage) backBtn.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
}
