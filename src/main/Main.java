package main;

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

/**
 * This is the class where the application starts
 *
 * @author Linmei M.
 */
public class Main extends Application {

    /**
     * This method create the main/first scene.
     *
     * @author Linmei M.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Views.LOGIN_VIEW_PATH.getView())), ResourceBundle.getBundle("resource/language", getLocale()));
        primaryStage.setScene(new Scene(root));
        primaryStage.centerOnScreen();
        primaryStage.show();
        JDBCConnection.openConnection();
    }

    /**
     * This is where the applications launches.
     * @param args It stores Java command line arguments and is an array of type java. lang. String class.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This method gets the locale based language.
     *
     * @return current locale
     */
    private Locale getLocale() {
//        Locale.setDefault(new Locale("fr", "FR"));
        Locale currentLocale = Locale.getDefault();
        if (currentLocale.equals(new Locale("en", "GB"))) {
            currentLocale = new Locale("en", "US");
        }
        return currentLocale;
    }
}
