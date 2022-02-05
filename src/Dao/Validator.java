package Dao;

import controller.LoginController;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Validator {
    /**
     * An optional button type window to confirm user's action.
     */
    public static Optional<ButtonType> confirmResult;

    /**
     * An alert to display an error message.
     */
    private static Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    /**
     * An alert object to display a confirmation to a user.
     */
    private static Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);

    /**
     * An alert object to display info to user to the user.
     */
    private static Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);

    public static boolean isValidString(String str1, String str2){
        return checkStringEntry(str1) && checkStringEntry(str2);
    };

    public static boolean isValidString(String str1, String str2, String str3, String str4){
        return checkStringEntry(str1) && checkStringEntry(str2) && checkStringEntry(str3) && checkStringEntry(str4);
    };
    public static boolean isValidString(String str1, String str2, String str3){
        return checkStringEntry(str1) && checkStringEntry(str2) && checkStringEntry(str3);
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
    public static void displayLoginInvalidInput(String msg) {
        errorAlert.setTitle(LoginController.language.getString("error"));
        errorAlert.setHeaderText(LoginController.language.getString("invalidInput"));
        errorAlert.setContentText(msg);
        errorAlert.showAndWait();
    }


    /**
     * This method sets an error alert that displays to the end user.
     *
     * @param msg the message puts in the content of the alert dialog
     */
    public static void displayInvalidInput(String msg) {
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText(("Invalid Input"));
        errorAlert.setContentText(msg);
        errorAlert.showAndWait();
    }

    public static boolean validateUserLogin(String dbPassword, String userPassword) throws SQLException {
        if (dbPassword.equals(userPassword)) {
            return true;
        }
        return false;
    }

    /**
     * This method sets a confirmation window to ensure the end user wants to exit the program.
     */
    public static void displayExitConfirmation() {
        confirmAlert.setTitle("Exit");
        confirmAlert.setHeaderText("Close the program");
        confirmAlert.setContentText("Are you sure you want to close the program?");
        confirmResult = confirmAlert.showAndWait();
    }


    public static void displaySuccess(String message) {
        infoAlert.setTitle("Information");
        infoAlert.setHeaderText(message + " Successfully!");
        infoAlert.showAndWait();
    }
    /**
     * This method generates a message to confirm whether the end user want to delete a selected item.
     */
    public static void displayDeleteConfirmation() {
        confirmAlert.setTitle("Parts");
        confirmAlert.setHeaderText("Delete");
        confirmAlert.setContentText("Are you sure you want to delete it?");
        confirmResult = confirmAlert.showAndWait();
    }

    /**
     * This method checks whether an user's input is an integer.
     *
     * @param input input from the end user
     * @return whether is a integer
     */
    public static boolean isInteger(String input) {
        boolean isValid = false;
        try {
            Integer d = Integer.parseInt(input);
            isValid = true;
        } catch (Exception ex) {
            isValid = false;
        }
        return isValid;
    }

    /**
     * This method checks whether an user's input is empty.
     *
     * @param input input from the end user
     * @return whether is a an empty string
     */
    public static boolean isEmpty(String input) {
        boolean isValid = false;
        if (input.isEmpty() || input.isBlank() || input == null) {
            isValid = true;

        }
        return isValid;
    }

    public static boolean isValidName(String name){
        Pattern p = Pattern.compile("^[a-zA-Z]+\\s[a-zA-Z]+$");//match only letters and one space between
        if(name == null){
            return false;
        }
        Matcher m = p.matcher(name);
        return m.matches();
    }

    public static boolean isValidString(String name){
        Pattern p = Pattern.compile("[a-zA-Z]+\\s+$");//match only letters and one space between
        if(name == null){
            return false;
        }
        Matcher m = p.matcher(name);
        return m.matches();
    }

    public static boolean isValidPhoneNumber(String phoneNumber){
        Pattern p = Pattern.compile("^\\d+$"); //as long as only digits
        if (phoneNumber == null) {
            return false;
        }
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }

    public static boolean isValidAddress(String address){
        Pattern p = Pattern.compile("^\\d+\\s[a-zA-Z]+,\\s[a-zA-Z]+$"); //formatted it as how US address looks like
        if (address == null) {
            return false;
        }
        Matcher m = p.matcher(address);
        return m.matches();
    }

    public static boolean isValidZipCode(String address){
        Pattern p = Pattern.compile("^\\d+$");
        if (address == null) {
            return false;
        }
        Matcher m = p.matcher(address);
        return m.matches();
    }

    public static void displayUnsavedInfo(String msg) {
        errorAlert.setTitle("Alert");
        errorAlert.setHeaderText(("Please save before continuing!"));
        errorAlert.setContentText(msg);
        errorAlert.showAndWait();
    }
}
