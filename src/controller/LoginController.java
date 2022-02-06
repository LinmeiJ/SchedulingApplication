package controller;

import Dao.AppointmentDaoImpl;
import Dao.UserDaoImpl;
import Dao.Validator;
import converter.DateTimeConverter;
import entity.Appointment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;

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
    private final static Logger LOGGER = Logger.getLogger(LoggerUtil.class.getName());
    private AppointmentDaoImpl appointmentDao = new AppointmentDaoImpl();

    @FXML
    void login(ActionEvent event) throws IOException, SQLException {
        name = (userNameField.getText());
        userPassword = (passwordField.getText());

        if(UserDaoImpl.findByUserName(name, userPassword)) {
            displayUpcomingAptsAlert();
            setScene(event, CUSTOMER_RECORD_VIEW);

        }else{
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Upcoming appointment:\n\n " + appointmentDao.getUpcomingApts(), ButtonType.OK);
        alert.showAndWait()
                .filter(res -> res == ButtonType.OK);
    }

}

