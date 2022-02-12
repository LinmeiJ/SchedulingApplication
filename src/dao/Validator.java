package dao;

import controller.LoginController;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.SQLException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class that contains a set of logic to validate input date or display messages to the user
 *
 * @Author Linmei M.
 */
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

    /**
     * Validates 4 strings at a time
     * @param str1 a string
     * @param str2 a string
     * @param str3 a string
     * @param str4 a string
     * @return if they are all valid, returns a true, otherwise returns a false.
     */
    public static boolean isValidString(String str1, String str2, String str3, String str4){
        return checkStringEntry(str1) && checkStringEntry(str2) && checkStringEntry(str3) && checkStringEntry(str4);
    }

    /**
     * validates 3 strings at a time
     * @param str1 a string
     * @param str2 a string
     * @param str3 a string
     * @return if they are all valid, returns a true, otherwise returns a false.
     */
    public static boolean isValidString(String str1, String str2, String str3){
        return checkStringEntry(str1) && checkStringEntry(str2) && checkStringEntry(str3);
    };

    /**
     * Validates a string by checking whether it is empty or null
     * @param str1 a string
     * @return if the string is valid, returns ture, otherwise returns a false.
     */
    public static boolean checkStringEntry(String str1){
        boolean isValid = false;
        if(str1.length() != 0 && !str1.isEmpty() && str1 != null){
            isValid =  true;
        }
        return isValid;
    }

    /**
     * This method sets an error alert that displays to the end user for log in UI.
     * @param msg the message puts in the content of the alert dialog
     */
    public static void displayLoginInvalidInput(String msg) {
        errorAlert.setTitle(LoginController.language.getString("error"));
        errorAlert.setHeaderText(LoginController.language.getString("invalidInput"));
        errorAlert.setContentText(msg);
        errorAlert.showAndWait();
    }


    /**
     * This method sets an error alert that displays to the end user for fields input.
     * @param msg the message puts in the content of the alert dialog
     */
    public static void displayInvalidInput(String msg) {
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText(("Invalid Input"));
        errorAlert.setContentText(msg);
        errorAlert.showAndWait();
    }

    /**
     * Validate user password by comparing the entered user password and the one from the database
     * @param dbPassword a password saved from the database
     * @param userPassword a password that entered by the user
     * @return if both password doesnt match, return false, otherwise, returns true.
     */
    public static boolean validateUserLogin(String dbPassword, String userPassword){
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

    /**
     * This method display a successfully info message to the user UI
     * @param message a additional message that will be displayed on the screen
     */
    public static void displaySuccess(String message) {
        infoAlert.setTitle("Information");
        infoAlert.setHeaderText(message + " Successfully!");
        infoAlert.showAndWait();
    }

    /**
     * This method display information to the user based on the message that is passed in
     * @param message a message that will be displayed on the screen
     */
    public static void displayInfo(String message) {
        infoAlert.setTitle("Information");
        infoAlert.setHeaderText(message);
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
     * checks if the name matches only letters and a space between
     * @param name name that user inputs
     * @return true if it matches the requirement, otherwise returns false.
     */
    public static boolean isValidName(String name){
        Pattern p = Pattern.compile("^[a-zA-Z]+\\s[a-zA-Z]+$");//match only letters and one space between
        if(name == null){
            return false;
        }
        Matcher m = p.matcher(name);
        return m.matches();
    }

//    public static boolean isValidString(String name){
//        Pattern p = Pattern.compile("[a-zA-Z]+\\s+$");//match only letters and one space between
//        if(name == null){
//            return false;
//        }
//        Matcher m = p.matcher(name);
//        return m.matches();
//    }

    /**
     * Check whether it is a valid phone number based on phone number only contains digits
     * @param phoneNumber a phone number user has entered
     * @return returns true if the requirement meets, otherwise return false.
     */
    public static boolean isValidPhoneNumber(String phoneNumber){
        Pattern p = Pattern.compile("^\\d+$"); //as long as only digits
        if (phoneNumber == null) {
            return false;
        }
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }

    /**
     * This method checks the address. the address can contain anything but special characters.
     * @param address the user input address
     * @return true if address is valid, otherwise false.
     */
    public static boolean isValidAddress(String address){
        Pattern p = Pattern.compile("^[#.0-9a-zA-Z\\s,-]+$");
        if (address == null) {
            return false;
        }
        Matcher m = p.matcher(address);
        return m.matches();
    }

    /**
     * validates a zipcode by checking zip code only contains digits
     * @param zipcode the user input zipcode
     * @return true if zipcode only contains digits, otherwise false
     */
    public static boolean isValidZipCode(String zipcode){
        Pattern p = Pattern.compile("^\\d+$");
        if (zipcode == null) {
            return false;
        }
        Matcher m = p.matcher(zipcode);
        return m.matches();
    }

    /**
     * this method display an reminder message
     * @param msg an additional message that will be displayed on user screen
     */
    public static void displayUnsavedInfo(String msg) {
        errorAlert.setTitle("Alert");
        errorAlert.setHeaderText(("Please save before continuing!"));
        errorAlert.setContentText(msg);
        errorAlert.showAndWait();
    }
}
