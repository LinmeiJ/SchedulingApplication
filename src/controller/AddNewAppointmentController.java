package controller;

import Dao.*;
import entity.Appointment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;

    @FXML
    private ComboBox<String> startHr;

    @FXML
    private ComboBox<String> startMinute;

    @FXML
    private ComboBox<String> endMinute;

    @FXML
    private ComboBox<String> endHr;

    @FXML
    private Button exitBtn;

    @FXML
    private Button saveBtn;

    private Appointment appointment;
    private ContactDaoImpl contactDao = new ContactDaoImpl();
    private CustomerDaoImpl customerDao = new CustomerDaoImpl();
    private AppointmentDaoImpl appointmentDao = new AppointmentDaoImpl();
    public static long newCustID;

//    @FXML
//    void BackToLastViewIsClicked(ActionEvent event) throws IOException {
//        setScene(event, CUSTOMER_RECORD_VIEW);
//    }

    @FXML
    void saveIsClicked(ActionEvent event) throws SQLException, IOException {
        appointment = new Appointment();
        appointment.setTitle(aptTitleField.getText());
        appointment.setDescription(aptDescriptionField.getText());
        appointment.setType(aptTypeField.getText());
        appointment.setLocation(aptLocationField.getText());
        appointment.setStart(appointmentDao.formatTime(startDate.getValue(), startHr.getValue(), startMinute.getValue()));
        appointment.setEnd(appointmentDao.formatTime(endDate.getValue(), endHr.getValue(), startMinute.getValue()));
        appointment.setCreated_date(Timestamp.valueOf(LocalDateTime.now()));
        appointment.setCreated_by(UserDaoImpl.userName);
        appointment.setLast_update(Timestamp.valueOf(LocalDateTime.now()));
        appointment.setLast_updated_by(UserDaoImpl.userName);
        if(AddNewCustomerController.isNewCust){
             newCustID = customerDao.findIdByNameAndDivisionId(AddNewCustomerController.customer.getCustomer_name(), AddNewCustomerController.customer.getDivision_id());
            appointment.setCustomer_id(newCustID);
        }else{
            appointment.setCustomer_id(CustomerRecordController.selectedCust.getCustomer_id())
        ;}
        appointment.setUser_id(UserDaoImpl.userId);
        appointment.setContact_id(contactDao.getContactId(aptContactField.getText()));

        appointmentDao.save(appointment);
        Validator.displaySuccess("Appointment is saved");
        setScene(event, APPOINTMENT_RECORD_VIEW);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startHr.setItems(initHrs);
        startMinute.setItems(initializeMinutes());
        endHr.setItems(initHrs);
        endMinute.setItems(initializeMinutes());
    }

    @FXML
    void existIsClicked(ActionEvent event) {
        exit(event, exitBtn);
    }

    public void BackToLastViewIsClicked(ActionEvent actionEvent) throws IOException {
        setScene(actionEvent, CUSTOMER_RECORD_VIEW);
    }
}
