package controller;

import dao.AppointmentDaoImpl;
import dao.UserDaoImpl;
import dao.Validator;
import dateTimeUtil.DateTimeConverter;
import enums.Views;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class LoginController implements Initializable, CommonUseHelperIfc {
    @FXML
    private Label userName;
    @FXML
    private TextField userNameField;
    @FXML
    private Label password;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginBtn;
    @FXML
    private Label locationField;
    @FXML
    private Button exitId;
    @FXML
    private Label signIn;

    public static ResourceBundle language;
    private String name;
    private String userPassword;
    private int successCount;
    private int failedCount;

    private AppointmentDaoImpl appointmentDao = new AppointmentDaoImpl();

    /**
     * This method received the log button action event with user inputs.
     * @param event an event indicates a component-defined action occurred
     */
    @FXML
    void login(ActionEvent event) {
        name = (userNameField.getText());
        userPassword = (passwordField.getText());

        if(UserDaoImpl.findByUserName(name, userPassword)) {
            displayUpcomingAptsAlert();
            setScene(event, Views.CUSTOMER_RECORD_VIEW.getView());
            successCount++;
            loginAttempt("Success");
        }else{
            loginAttempt("Failed");
            failedCount++;
            Validator.displayLoginInvalidInput(language.getString("userNotFound"));
        }
    }

    /**
     * This method closes the the program.
     * It prompts a dialog window to ensure whether a user wants to exit.
     *
     * @param event an event indicates a component-defined action occurred
     */
    @FXML
    void exitClicked(ActionEvent event) {
        exit(event, exitId);
    }

    /**
     * Initializes the Login view window and translates it based on user local language setting.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.language = resourceBundle;
        locationField.setText(DateTimeConverter.getTimeZoneID());
        signIn.setText(language.getString("signIn"));
        userName.setText(language.getString("name"));
        password.setText(language.getString("password"));
        exitId.setText(language.getString("exit"));
        loginBtn.setText(language.getString("loginBtn"));
    }

    /**
     * Displays whether there is an upcoming appointment.
     * <P>
     *     Lambda expression 2
     * </P>
     */
    private void displayUpcomingAptsAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Upcoming appointment:\n\n " + appointmentDao.getAllUpcomingApts(), ButtonType.OK);
        alert.showAndWait()
                .filter(res -> res == ButtonType.OK);
    }

    /**
     *  Logs user attempt into a file called login_activity.txt
     */
    private void loginAttempt(String loginAttempts) {
        try {
            FileWriter fw = new FileWriter("login_activity.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("Date = " + DateTimeFormatter.ISO_DATE_TIME.format(OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS)) + "\t\t");
            bw.write("User Name = " + userNameField.getText() + "\t\t");
            bw.write("Attempt Status = " + loginAttempts + "\t\t");
            bw.write("Success Total Count = " + successCount + "\t\t");
            bw.write("Failed Total Count = " + failedCount + "\t\t");

            bw.newLine();
            bw.close();
        } catch (IOException e) {
            System.out.println("Failed to log invalid login attempt:" + "Exception Message: " + e.getMessage());
        }
    }
}