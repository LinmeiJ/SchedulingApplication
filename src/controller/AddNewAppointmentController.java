package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddNewAppointmentController implements Initializable, CommonUseHelperIfc {

    @FXML
    private TextField aptField;

    @FXML
    private TextField aptTitleField;

    @FXML
    private TextField aptTypeField;

    @FXML
    private TextField aptLocationField;

    @FXML
    private TextField aptContactField;

    @FXML
    private TextArea aptDescriptionField;

    @FXML
    private TextField setCustId;

    @FXML
    private TextField setUserId;

    @FXML
    private Button saveBtn;

    @FXML
    private Button BackBtn;

    @FXML
    private Button exitBtn;

    @FXML
    void BackToLastViewIsClicked(ActionEvent event) throws IOException {
        setScene(event, ADD_NEW_CUSTOMER_VIEW);
    }

    @FXML
    void saveIsClicked(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void existIsClicked(ActionEvent event) {
        exit(event, exitBtn);
    }

}
