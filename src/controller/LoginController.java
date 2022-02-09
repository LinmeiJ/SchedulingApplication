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

public class LoginController extends Location implements Initializable, CommonUseHelperIfc {
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

    @FXML
    void login(ActionEvent event) throws IOException, SQLException {
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

    @FXML
    void exitClicked(ActionEvent event) {
        exit(event, exitId);
    }

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

    private void displayUpcomingAptsAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Upcoming appointment:\n\n " + appointmentDao.getAllUpcomingApts(), ButtonType.OK);
        alert.showAndWait()
                .filter(res -> res == ButtonType.OK);
    }

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