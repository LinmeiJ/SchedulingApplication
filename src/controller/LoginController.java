package controller;

import Dao.UserDaoImpl;
import Dao.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController extends Location implements Initializable, CommonUseHelperIfc {
    @FXML
    private TextField userNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private static Label locationField;

    @FXML
    private Button exitId;

    private ResourceBundle rb;
    private String userName;
    private String password;

    private static final String USER_NOT_FOUND = "User is not found, please check your user name or password.";

    @FXML
    void login(ActionEvent event) throws IOException {
        userName = (userNameField.getText());
        password = (passwordField.getText());

        if(UserDaoImpl.findByUserName(userName, password)) {
            setScene(event, CUSTOMER_RECORD_VIEW);
        }else{
            Validator.displayInvalidInput(USER_NOT_FOUND);
        }
    }

    @FXML
    void exitClicked(ActionEvent event) {
        exit(event, exitId);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String location = Locale.getDefault() + ", " +ZoneId.systemDefault().getId();

        locationField.setText(location);
    }
}

