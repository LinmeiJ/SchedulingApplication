package controller;

import Dao.UserDaoImpl;
import Dao.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable, Exit {
    @FXML
    private TextField userNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private static Label locationID;

    @FXML
    private Button exitId;

    ResourceBundle rb;

//    private DBService dbService = new DBService();

    private String userName;
    private String password;

    private static final String CUSTOMER_RECORD_VIEW = "../views/customerRecordView.fxml";
    private static final String USER_NOT_FOUND = "User is not found, please check your user name or password.";

    @FXML
    void login(ActionEvent event) throws IOException {
        userName = (userNameField.getText());
        password = (passwordField.getText());

        if(UserDaoImpl.findByUserName(userName, password)) {
            setNextScene(event);
        }else{
            Validator.displayInvalidInput(USER_NOT_FOUND);
        }
    }

    private void setNextScene(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(CUSTOMER_RECORD_VIEW));
        var scene = new Scene(parent);
        var stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    void exitClicked(ActionEvent event) {
        exit(event, exitId);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        locationID.setText(Locale.getDefault().toString());
    }
}

