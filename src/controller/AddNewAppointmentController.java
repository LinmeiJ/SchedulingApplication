package controller;

import Dao.AppointmentDaoImpl;
import Dao.ContactDaoImpl;
import Dao.CustomerDaoImpl;
import Dao.UserDaoImpl;
import entity.Appointment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
    private Appointment appointment;
    private ContactDaoImpl contactDao = new ContactDaoImpl();
    private CustomerDaoImpl customerDao = new CustomerDaoImpl();
    private AppointmentDaoImpl appointmentDao = new AppointmentDaoImpl();

    @FXML
    void BackToLastViewIsClicked(ActionEvent event) throws IOException {
        setScene(event, ADD_NEW_CUSTOMER_VIEW);
    }

    @FXML
    void saveIsClicked(ActionEvent event) throws SQLException {
        appointment = new Appointment();
        appointment.setTitle(aptTitleField.getText());
        appointment.setType(aptTypeField.getText());
        appointment.setLocation(aptLocationField.getText());
        aptDescriptionField.getText();
        appointment.setCustomer_id(CustomerRecordController.selectedCust.getCustomer_id());
//        appointment.setStart();
        appointment.setUser_id(UserDaoImpl.userId);
        appointment.setContact_id(contactDao.getContactId(aptContactField.getText()));

        appointmentDao.save(appointment);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        aptStart
    }

    @FXML
    void existIsClicked(ActionEvent event) {
        exit(event, exitBtn);
    }

}
