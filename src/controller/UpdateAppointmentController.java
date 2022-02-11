package controller;
import dao.AppointmentDaoImpl;
import dao.ContactDaoImpl;
import dao.UserDaoImpl;
import dao.Validator;
import dateTimeUtil.BookingAvailability;
import dateTimeUtil.DateTimeConverter;
import dbConnection.JDBCConnection;
import entity.Appointment;
import enums.Views;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;
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
    private ComboBox<String> contactList;

    @FXML
    private Label userId;


    private AppointmentDaoImpl appointmentDao = new AppointmentDaoImpl();
    private ContactDaoImpl contactDao = new ContactDaoImpl();
    ObservableList<String> contact = FXCollections.observableArrayList();

    Appointment appointment = AppointmentRecordController.selectApt;

    @FXML
    void backToRecordPage(ActionEvent event) throws IOException {
        setScene(event, Views.APPOINTMENT_RECORD_VIEW.getView());
    }

    @FXML
    void exitBtnClicked(ActionEvent event) {
        exit(event, cancelBtn);
    }

    private boolean areValidInput(String type, String location, String title, String description, LocalDate startD, String startH, String startM, LocalDate endD, String endH, String endM, String contact) {
        return Validator.isValidString(type, location, title) && description != null && contact != null && startD != null && endD != null && Validator.isValidString(startH, startM, endH, endM);
    }
    @FXML
    void updateClicked(ActionEvent event) throws SQLException, IOException {
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
        String contactName =  contactList.getValue();
        long contactId = contactDao.getContactId(contactName);

        if(!areValidInput(type, location, title, description, startD, startH, startM, endD, endH, endM, contactName)){
            Validator.displayInvalidInput("Invalid input. \n requires:\n" +
                    "Only alphabets are allowed for Type, Location, Title and all fields can not be empty");
        }
        else if(appointmentDao.isDoubleBooking(contactId, startD, startH, startM, endD, endH, endM)){
            Validator.displayInfo("Sorry, the time you have selected is booked, please select a different time. Available Time listed here (in EST timezone): \n" + getAvailableTime());
        }
        else{
            getAptsRecordForm(event, type, location, title, description, startD, startH, startM, endD, endH, endM, contactId);
        }
    }

    private String getAvailableTime() {
        String availableTime = "";
        Iterator iteratorMap = BookingAvailability.availableTimeToDisplay.entrySet().iterator();
        while (iteratorMap.hasNext()) {
            Map.Entry mapElement = (Map.Entry)iteratorMap.next();
            availableTime = availableTime + mapElement.getKey() + " To "
                    + mapElement.getValue() +"\n";
        }
        return availableTime;
    }

    private void getAptsRecordForm(ActionEvent event, String type, String location, String title, String description, LocalDate startD, String startH, String startM, LocalDate endD, String endH, String endM, long contactId) throws SQLException {
        Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());
        Timestamp start = DateTimeConverter.convertAptTimeToUTC(startD, startH, startM);
        Timestamp end = DateTimeConverter.convertAptTimeToUTC(endD, endH, endM);
        long custId = CustomerRecordController.selectedCust.getCustomer_id();

        appointmentDao.update(new Appointment(title, description, location, type, start, end, currentTime, UserDaoImpl.userName, currentTime, UserDaoImpl.userName, custId, contactId, UserDaoImpl.userId));
        setScene(event, Views.APPOINTMENT_RECORD_VIEW.getView());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initFields();
        initLastAptDateTime();

        try {
            initContact();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disabledWeekends();
    }

    private void initFields() {
        aptID.setText(String.valueOf(appointment.getAppointment_id()));
        aptTitle.setText(appointment.getTitle());
        aptDescription.setText(appointment.getDescription());
        aptLocation.setText(appointment.getLocation());
        aptType.setText(appointment.getType());

        custID.setText(String.valueOf(appointment.getCustomer_id()));
        userId.setText(String.valueOf(appointment.getUser_id()));
    }

    private void initContact() throws SQLException {
        contactList.setValue(contactDao.findNameByID(appointment.getContact_id()));
        contactList.setItems(contactDao.findAll());
    }

    private void initLastAptDateTime() {
        LocalDateTime startDateTime = appointment.getStart().toLocalDateTime();
        startDate.setValue(startDateTime.toLocalDate());
        startHr.setValue(DateTimeConverter.getHr(startDateTime.getHour()));
        startMin.setValue(DateTimeConverter.getMint(startDateTime.getMinute()));

        LocalDateTime endDateTime = appointment.getStart().toLocalDateTime();
        endDate.setValue(endDateTime.toLocalDate());
        endHr.setValue(DateTimeConverter.getHr(endDateTime.getHour()));
        endMin.setValue(DateTimeConverter.getMint(endDateTime.getMinute()));

        startHr.setItems(estHr);
        //startHr.setItems(DateTimeConverter.convertToLocalHr(estHR));
        startMin.setItems(initializeMinutes());
        endHr.setItems(estHr);
        endMin.setItems(initializeMinutes());
    }

    private void disabledWeekends() {
        Callback<DatePicker, DateCell> startDayCellFactory= this.getDayCellFactory();
        startDate.setDayCellFactory(startDayCellFactory);
        Callback<DatePicker, DateCell> endDayCellFactory= this.getDayCellFactory();
        endDate.setDayCellFactory(endDayCellFactory);
    }
}
