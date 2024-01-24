package com.app.quiz;

import java.sql.Connection;
import java.sql.DriverManager;

import io.github.cdimascio.dotenv.Dotenv;

public class databaseConnection {

    public Connection databaseLink;

    public Connection getDatabaseLink() {
        Dotenv dotenv = Dotenv.load();
        String databaseName = dotenv.get("DB_NAME");
        String databaseUser = dotenv.get("DB_USER");
        String databasePassword = dotenv.get("DB_PASSWORD");
        String url = "jdbc:mysql://localhost/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser,databasePassword);
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
        return databaseLink;
    }
}
