package controller;
import Dao.AppointmentDaoImpl;
import Dao.ContactDaoImpl;
import Dao.UserDaoImpl;
import Dao.Validator;
import converter.DateTimeConverter;
import dbConnection.JDBCConnection;
import entity.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class UpdateAppointmentController extends JDBCConnection implements Initializable, CommonUseHelperIfc {
//    @FXML
//    private TextField aptContactName;

    @FXML
    private TextArea aptDescription;

    @FXML
    private TextField aptID;

    @FXML
    private Label location;

    @FXML
    private TextField aptLocation;

    @FXML
    private TextField aptTitle;

    @FXML
    private TextField aptType;

    @FXML
    private Button backBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private Label custID;

    @FXML
    private DatePicker endDate;

    @FXML
    private ComboBox<String> endHr;

    @FXML
    private ComboBox<String> endMin;

    @FXML
    private Label orgEnd;

    @FXML
    private Label orgStart;

    @FXML
    private TextField phoneField;

    @FXML
    private Button saveUpdates;

    @FXML
    private DatePicker startDate;

    @FXML
    private ComboBox<String> startHr;

    @FXML
    private ComboBox<String> startMin;

    @FXML
    private ChoiceBox<String> contactList;

    @FXML
    private Label userId;


    private AppointmentDaoImpl appointmentDao = new AppointmentDaoImpl();
    private ContactDaoImpl contactDao = new ContactDaoImpl();
    ObservableList<String> contact = FXCollections.observableArrayList();

    Appointment appointment = AppointmentRecordController.selecteApt;

    @FXML
    void backToRecordPage(ActionEvent event) throws IOException {
        setScene(event,APPOINTMENT_RECORD_VIEW);
    }

    @FXML
    void exitBtnClicked(ActionEvent event) {
        exit(event, cancelBtn);
    }

    private boolean areValidInput(String type, String location, String title, String description, LocalDate startD, String startH, String startM, LocalDate endD, String endH, String endM) {
        return Validator.isValidString(type, location, title, description) && startD != null && endD != null && Validator.isValidString(startH, startM, endH, endM);
    }
    @FXML
    void saveUpdateClicked(ActionEvent event) throws SQLException, IOException {
        String type = aptType.getText();
        String location = aptLocation.getText();
        String title = aptTitle.getText();
        String description = aptDescription.getText();
        LocalDate startD = startDate.getValue();
        String startH = startHr.getValue();
        String startM = startMin.getValue();
        LocalDate endD = endDate.getValue();
        String endH = endHr.getValue();
        String endM = endMin.getValue();
        if(areValidInput(type, location, title, description, startD, startH, startM, endD, endH, endM)){

        }
       appointment.setType(aptType.getText());
       appointment.setLocation(aptLocation.getText());
       appointment.setTitle(aptTitle.getText());
       appointment.setContact_id(contactDao.getContactId(contactList.getValue()));
       appointment.setDescription(aptDescription.getText());
       appointment.setStart(DateTimeConverter.formatTime(startDate.getValue(), startHr.getValue(), startMin.getValue()));
       appointment.setEnd(DateTimeConverter.formatTime(endDate.getValue(), endHr.getValue(), startMin.getValue()));
       appointment.setCreated_date(Timestamp.valueOf(LocalDateTime.now()));
       appointment.setCreated_by(UserDaoImpl.userName);
       appointment.setLast_update(Timestamp.valueOf(LocalDateTime.now()));
       appointment.setLast_updated_by(UserDaoImpl.userName);
       appointment.setCustomer_id(CustomerRecordController.selectedCust.getCustomer_id());
       appointment.setUser_id(UserDaoImpl.userId);

       appointmentDao.update(appointment);
       Validator.displaySuccess("Appointment is saved");
       setScene(event, APPOINTMENT_RECORD_VIEW);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        aptID.setText(String.valueOf(appointment.getAppointment_id()));
        aptTitle.setText(appointment.getTitle());
        aptDescription.setText(appointment.getDescription());
        aptLocation.setText(appointment.getLocation());
        aptType.setText(appointment.getType());
        String orgStartTime = appointment.getStart().toString();
        String orgEndTime = appointment.getEnd().toString();
        orgStart.setText(orgStartTime.substring(0, orgStartTime.length() - 2) + " - " + orgEndTime.substring(0, orgEndTime.length() - 2)); //fix me.. pass the DB saved start date

        custID.setText(String.valueOf(appointment.getCustomer_id()));
        userId.setText(String.valueOf(appointment.getUser_id()));

        try {
//            contactList.setItems(contactDao.findAll());
            contactList.setValue(contactDao.findNameByID(appointment.getContact_id()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        startHr.setItems(initHrs);
        startMin.setItems(initializeMinutes());
        endHr.setItems(initHrs);
        endMin.setItems(initializeMinutes());
    }
}
