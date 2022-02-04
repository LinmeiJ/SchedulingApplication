package main;

import controller.LoginController;
import dbConnection.JDBCConnection;
import enums.CountryId;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class Main extends Application {
    public static String LOGIN_VIEW_PATH = "../views/loginView.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(LOGIN_VIEW_PATH)),  ResourceBundle.getBundle("resource/language", getLocale()));
        primaryStage.setScene(new Scene(root, 800, 800));
        primaryStage.show();
        JDBCConnection.openConnection();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Locale getLocale() {
        Locale currentLocale = Locale.getDefault();
        if(currentLocale.equals( new Locale("en", "GB"))){
            currentLocale = new Locale("en", "US");
        }
        return currentLocale;
    }
}
