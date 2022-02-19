package controller;

import dateTimeUtil.DateTimeConverter;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.time.*;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class that contains a set of logic to validate input date or display messages to the user
 *
 * @author Linmei M.
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
     *
     * @param str1 a string
     * @param str2 a string
     * @param str3 a string
     * @param str4 a string
     * @return if they are all valid, returns a true, otherwise returns a false.
     */
    public static boolean isValidString(String str1, String str2, String str3, String str4) {
        return checkStringEntry(str1) && checkStringEntry(str2) && checkStringEntry(str3) && checkStringEntry(str4);
    }

    /**
     * validates 3 strings at a time
     *
     * @param str1 a string
     * @param str2 a string
     * @param str3 a string
     * @return if they are all valid, returns a true, otherwise returns a false.
     */
    public static boolean isValidString(String str1, String str2, String str3) {
        return checkStringEntry(str1) && checkStringEntry(str2) && checkStringEntry(str3);
    }

    /**
     * Validates a string by checking whether it is empty or null
     *
     * @param str1 a string
     * @return if the string is valid, returns ture, otherwise returns a false.
     */
    public static boolean checkStringEntry(String str1) {
        return str1.length() != 0 && !str1.isEmpty() && str1 != null;
    }

    /**
     * This method sets an error alert that displays to the end user for log in UI.
     *
     * @param msg the message puts in the content of the alert dialog
     */
    public static void displayLoginInvalidInput(String msg) {
        errorAlert.setTitle(LoginController.language.getString("error"));
        errorAlert.setHeaderText(LoginController.language.getString("invalidInput"));
        errorAlert.setContentText(msg);
        errorAlert.showAndWait().filter(res -> res == ButtonType.OK);
    }

    /**
     * This method sets an error alert that displays to the end user for fields input.
     *
     * @param msg the message puts in the content of the alert dialog
     */
    public static void displayInvalidInput(String msg) {
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText(("Invalid Input"));
        errorAlert.setContentText(msg);
        errorAlert.showAndWait().filter(res -> res == ButtonType.OK);
    }

    /**
     * Validate user password by comparing the entered user password and the one from the database
     *
     * @param dbPassword   a password saved from the database
     * @param userPassword a password that entered by the user
     * @return if both password doesnt match, return false, otherwise, returns true.
     */
    public static boolean validateUserLogin(String dbPassword, String userPassword) {
        return dbPassword.equals(userPassword);
    }

    /**
     * This method sets a confirmation window to ensure the end user wants to exit the program.
     */
    public static void displayExitConfirmation() {
        confirmAlert.setTitle("Exit");
        confirmAlert.setHeaderText("Close the program");
        confirmAlert.setContentText("Are you sure you want to close the program?");
        confirmResult = confirmAlert.showAndWait().filter(res -> res == ButtonType.OK);
    }


    /**
     * This method sets a confirmation window to ensure the end user wants to exit the program.
     *
     * @param language A resource folder where contains languages that are in english and french
     */
    public static void displayExitConfirmation(ResourceBundle language) {
        confirmAlert.setTitle(language.getString("exit"));
        confirmAlert.setHeaderText(language.getString("close"));
        confirmAlert.setContentText(language.getString("confirm"));
        confirmResult = confirmAlert.showAndWait().filter(res -> res == ButtonType.OK);
    }

    /**
     * This method display a successfully info message to the user UI
     *
     * @param message a additional message that will be displayed on the screen
     */
    public static void displaySuccess(String message) {
        infoAlert.setTitle("Information");
        infoAlert.setHeaderText(message + " successfully!");
        infoAlert.showAndWait().filter(res -> res == ButtonType.OK);
    }

    /**
     * This method display information to the user based on the message that is passed in
     *
     * @param message a message that will be displayed on the screen
     */
    public static void displayInfo(String message) {
        infoAlert.setTitle("Information");
        infoAlert.setHeaderText(message);
        infoAlert.showAndWait()
                .filter(res -> res == ButtonType.OK);
    }

    /**
     *
     * @param message a custom message that displays on the user screen
     */
    public static void displayConfirmation(String message) {
        infoAlert.setTitle("Confirmation");
        infoAlert.setHeaderText(message);
        infoAlert.showAndWait()
                .filter(res -> res == ButtonType.OK);
    }

    /**
     * This method generates a message to confirm whether the end user want to delete a selected item.
     *
     * @param message  a custom message that displays on the screen
     */
    public static void displayDeleteConfirmation(String message) {
        confirmAlert.setTitle("Delete Confirmation");
        confirmAlert.setHeaderText("Delete");
        confirmAlert.setContentText("Are you sure you want to delete " + message);
        confirmResult = confirmAlert.showAndWait();
    }

    /**
     * checks if the name matches only letters and a space between
     *
     * @param name name that user inputs
     * @return true if it matches the requirement, otherwise returns false.
     */
    public static boolean isValidName(String name) {
        Pattern p = Pattern.compile("^[a-zA-Z]+\\s[a-zA-Z]+$");//match only letters and one space between
        if (name == null) {
            return false;
        }
        Matcher m = p.matcher(name);
        return m.matches();
    }

    /**
     * Check whether it is a valid phone number based on phone number only contains digits
     *
     * @param phoneNumber a phone number user has entered
     * @return returns true if the requirement meets, otherwise return false.
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        Pattern p = null;
        if (phoneNumber == null) {
            return false;
        }
        switch (CustomerRecordController.ctryId) {
            case US:
            case CANADA:
                p = Pattern.compile("^\\d{3}-\\d{3}-\\d{4}$");
                break;
            case UK:
                p = Pattern.compile("^\\d{2}-\\d{3}-\\d{3}-\\d{4}$");
                break;
            default:
                Validator.displayInfo("Country can not be empty.");
                break;
        }
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }

    /**
     * This method checks the address. the address can contain anything but special characters.
     *
     * @param address the user input address
     * @return true if address is valid, otherwise false.
     */
    public static boolean isValidAddress(String address) {
        Pattern p = Pattern.compile("^[#.0-9a-zA-Z\\s,-]+$");
        if (address == null) {
            return false;
        }
        Matcher m = p.matcher(address);
        return m.matches();
    }

    /**
     * validates a zipcode by checking zip code only contains digits
     *
     * @param zipcode the user input zipcode
     * @return true if zipcode only contains digits, otherwise false
     */
    public static boolean isValidZipCode(String zipcode) {
        if (zipcode == null) {
            return false;
        }
        Pattern p = null;
        switch (CustomerRecordController.ctryId) {
            case US:
            case CANADA:
                p = Pattern.compile("^\\d+$");
                break;
            case UK:
                p = Pattern.compile("^[A-Z]{1,2}[0-9]{2}[A-Z]$");
                break;
            default:
                Validator.displayInfo("country ID is empty");
                break;
        }
        Matcher m = p.matcher(zipcode);
        return m.matches();
    }

    /**
     * this method display an reminder message
     *
     * @param msg an additional message that will be displayed on user screen
     */
    public static void displayUnsavedInfo(String msg) {
        errorAlert.setTitle("Alert");
        errorAlert.setHeaderText(("Please save before continuing!"));
        errorAlert.setContentText(msg);
        errorAlert.showAndWait()
                .filter(res -> res == ButtonType.OK);
    }

    /**
     * Validate whether an appointment time is valid. if the end date time is before the start date time for example, it results an invalid appointment time.
     *
     * @param startD an appointment starting date
     * @param startH an appointment starting hour
     * @param startM an appointment starting minute
     * @param endD an appointment ending date
     * @param endH an appointment ending hour
     * @param endM an appointment ending minute
     * @return true if the appointment time is valid, otherwise returns a false.
     */
    public static boolean isValidAppointmentTime(LocalDate startD, String startH, String startM, LocalDate endD, String endH, String endM) {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalTime startLocalTime = LocalTime.of(Integer.valueOf(startH), Integer.valueOf(startM));
        ZonedDateTime startDateTime = ZonedDateTime.of(startD, startLocalTime, zoneId);
        LocalTime endLocalTime = LocalTime.of(Integer.valueOf(endH), Integer.valueOf(endM));
        ZonedDateTime endDateTime =ZonedDateTime.of(endD, endLocalTime, zoneId);;

        return endDateTime.isAfter(startDateTime) && LocalDateTime.now().isBefore(ChronoLocalDateTime.from(startDateTime));
//        LocalTime startTime = LocalTime.of(Integer.parseInt(startH), Integer.parseInt(startM));
//        LocalTime endTime = LocalTime.of(Integer.parseInt(endH), Integer.parseInt(endM));
//        LocalDateTime startDateTime = LocalDateTime.of(startD, startTime);
//        LocalDateTime endDateTime = LocalDateTime.of(endD, endTime);
//        return endDateTime.isAfter(startDateTime) && LocalDateTime.now().isBefore(startDateTime);
    }


    public static boolean isOutOfOfficeHr(LocalDateTime startLocalDateTime, LocalDateTime endLocalDateTime) {
        return (startLocalDateTime.toLocalTime()).isBefore(DateTimeConverter.convertESTOfficeStartHrToLocal())
                || (startLocalDateTime.toLocalTime().isAfter(DateTimeConverter.convertESTOfficeEndHrToLocal()))
                || (endLocalDateTime.toLocalTime()).isBefore(DateTimeConverter.convertESTOfficeStartHrToLocal())
                || (endLocalDateTime.toLocalTime().isAfter(DateTimeConverter.convertESTOfficeEndHrToLocal()));
    }
}
