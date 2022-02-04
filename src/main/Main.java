package main;

import converter.DateTimeConverter;
import dbConnection.JDBCConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class Main extends Application {
    public static String LOGIN_VIEW_PATH = "../views/loginView.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception{
        System.out.println(LocalDateTime.now());
        DateTimeConverter.convertAptTimeToUTC(LocalDate.of(2022,02, 04), "13", "00");
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
