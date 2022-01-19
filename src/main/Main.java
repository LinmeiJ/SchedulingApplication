package main;

import controller.LoginController;
import dbConnection.JDBCConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.ZonedDateTime;
import java.util.Objects;

public class Main extends Application {
    public static String LOGIN_VIEW_PATH = "../views/loginView.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(LOGIN_VIEW_PATH)));
        primaryStage.setScene(new Scene(root, 800, 800));
        primaryStage.show();
        JDBCConnection.openConnection();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
