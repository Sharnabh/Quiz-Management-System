module com.app.quiz {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires java.sql;
    requires java.net.http;
    requires org.json;
    requires org.apache.commons.lang3;
    requires org.apache.commons.text;
    requires io.github.cdimascio.dotenv.java;
    requires mysql.connector.j;

    opens com.app.quiz to javafx.fxml;
    exports com.app.quiz;
}