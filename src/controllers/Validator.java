package controllers;

import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class Validator {
    /**
     * An alert to display an error message.
     */
    private static Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    public static boolean isValidString(String str1, String str2){
        return checkStringEntry(str1) && checkStringEntry(str2);
    };

    public static boolean checkStringEntry(String str1){
        boolean isValid = false;
        if(str1.length() != 0 && !str1.isEmpty() && str1 != null){
            isValid =  true;
        }
        return isValid;
    }

    /**
     * This method sets an error alert that displays to the end user.
     *
     * @param msg the message puts in the content of the alert dialog
     */
    public static void displayInvalidInput(String msg) {
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText("Incorrect Input");
        errorAlert.setContentText(msg);
        errorAlert.showAndWait();
    }

    public static boolean validateUserLogin(ResultSet result, String userPassword) throws SQLException {
        while(result.next()){
            if(result.getString("Password").equals(userPassword)){
                return true;
            }
        }
        return false;
    }
}
