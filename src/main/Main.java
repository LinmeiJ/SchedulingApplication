package main;

import dateTimeUtil.DateTimeConverter;
import dbConnection.JDBCConnection;
import enums.Views;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class Main extends Application {

    /**
     * This class create the main/first scene.
     *
     *
     * @author Linmei M.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Views.LOGIN_VIEW_PATH.getView())),  ResourceBundle.getBundle("resource/language", getLocale()));
        primaryStage.setScene(new Scene(root, 800, 800));
        primaryStage.show();
        JDBCConnection.openConnection();
    }

    public static void main(String[] args) {
        launch(args);
//        JDBCConnection.closeConnection();
    }

    private Locale getLocale() {
        Locale currentLocale = Locale.getDefault();
        if(currentLocale.equals( new Locale("en", "GB"))){
            currentLocale = new Locale("en", "US");
        }
        return currentLocale;
    }
}
