package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateAppointmentController implements CommonUseHelperIfc, Initializable {

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
    private DatePicker startDate;

    @FXML
    private ComboBox<?> startHr;

    @FXML
    private ComboBox<?> startMinute;

    @FXML
    private ChoiceBox<?> startMeridiem;

    @FXML
    private DatePicker endDate;

    @FXML
    private ComboBox<?> endHr;

    @FXML
    private ComboBox<?> endMinute;

    @FXML
    private ChoiceBox<?> endMeridiem;

    @FXML
    private Button saveBtn;

    @FXML
    private Button BackBtn;

    @FXML
    private Button exitBtn;

    @FXML
    void BackToLastViewIsClicked(ActionEvent event) throws IOException {
        setScene(event, UPDATE_CUSTOMER_VIEW);
    }

    @FXML
    void exitBtnClicked(ActionEvent event) {
        exit(event, exitBtn);
    }

    @FXML
    void saveIsClicked(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void addNewAptClicked(ActionEvent actionEvent) {
    }
    @FXML
    public void UpdateSelected(ActionEvent actionEvent) {
    }
    @FXML
    public void deleteSelected(ActionEvent actionEvent) {
    }

}
