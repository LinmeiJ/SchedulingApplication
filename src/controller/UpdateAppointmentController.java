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

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * This class provides a data control flow for generating reports between the report UI and the database
 *
 * @author Linmei M.
 */
public class UpdateAppointmentController extends JDBCConnection implements Initializable, CommonUseHelperIfc {
    @FXML
    private TextArea aptDescription;
    @FXML
    private TextField aptID;
    @FXML
    private TextField aptLocation;
    @FXML
    private TextField aptTitle;
    @FXML
    private TextField aptType;
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
    public Appointment appointment = AppointmentRecordController.selectApt;

    /**
     * This method sets the scene to the previous scene.
     *
     * @param event an event indicates a component-defined action occurred.
     **/
    @FXML
    void backToRecordPage(ActionEvent event) {
        setScene(event, Views.APPOINTMENT_RECORD_VIEW.getView());
    }

    /**
     * This method exits the program.
     *
     * @param event an event indicates a component-defined action occurred.
     */
    @FXML
    void exitBtnClicked(ActionEvent event) {
        exit(event, cancelBtn);
    }

    /**
     * The method updates an existing appointment
     *
     * @param event an event indicates a component-defined action occurred.
     */
    @FXML
    void updateClicked(ActionEvent event) {
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
        String contactName = contactList.getValue();
        long contactId = 0;
        contactId = contactDao.getContactId(contactName);

        if (!areValidInput(type, location, title, description, startD, startH, startM, endD, endH, endM, contactName)) {
            Validator.displayInvalidInput("Invalid input. \n requires:\n" +
                    "Only alphabets are allowed for Type, Location, Title and all fields can not be empty");
        } else if(!DateTimeConverter.isWithinOfficeHour(startD, startH,startM)){
            Validator.displayInfo("Sorry, the time you wish to book is out of the EST office hour. The office hour starts "
                    + DateTimeConverter.getOfficeHourOfTheDay(startD)
                    + " on your day today. Please select a time again.");
        } else if (appointmentDao.isDoubleBooking(contactId, startD, startH, startM, endD, endH, endM)) {
            Validator.displayInfo("Sorry, the time you have selected is booked, please select a different time. \nThe available office hours in EST timezone for the same date: " + getAvailableTime());
        } else {
            updateAptRecordForm(event, type, location, title, description, startD, startH, startM, endD, endH, endM, contactId);
        }
    }

    /**
     * The method generates a set of available time to display on users window when the time is booked or out of EST office hour
     */
    private String getAvailableTime() {
        String availableTime = "";
        Iterator iteratorMap = BookingAvailability.availableTimeToDisplay.entrySet().iterator();
        while (iteratorMap.hasNext()) {
            Map.Entry mapElement = (Map.Entry) iteratorMap.next();
            availableTime = availableTime + mapElement.getKey() + " To "
                    + mapElement.getValue() + "\n";
        }
        return availableTime;
    }

    /**
     * This method updates the selected appointment.
     *
     * @param event       JavaFX event that passed in from the log in button. it is there for later to transition from this view to the next view
     * @param title       title field input
     * @param description description field input
     * @param type        type field input
     * @param location    location field input
     * @param startD      start date field input
     * @param startH      start hour field input
     * @param startM      start minute field input
     * @param endD        end date field input
     * @param endH        end hour field input
     * @param endM        end minute field input
     * @param contactId   contact ID
     */
    private void updateAptRecordForm(ActionEvent event, String type, String location, String title, String description, LocalDate startD, String startH, String startM, LocalDate endD, String endH, String endM, long contactId) {
        Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());
        Timestamp start = DateTimeConverter.convertAptTimeToUTC(startD, startH, startM);
        Timestamp end = DateTimeConverter.convertAptTimeToUTC(endD, endH, endM);
        long custId = CustomerRecordController.selectedCust.getCustomer_id();

        appointmentDao.update(new Appointment(title, description, location, type, start, end, currentTime, UserDaoImpl.userName, currentTime, UserDaoImpl.userName, custId, contactId, UserDaoImpl.userId));
        setScene(event, Views.APPOINTMENT_RECORD_VIEW.getView());
    }

    /**
     * Display the original appointment information and user can later update directly on the top of those fields.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initFields();
        initLastAptDateTime();
        initContact();
        disabledWeekends();
    }

    /**
     * Initialize each field of the selected appointment
     */
    private void initFields() {
        aptID.setText(String.valueOf(appointment.getAppointment_id()));
        aptTitle.setText(appointment.getTitle());
        aptDescription.setText(appointment.getDescription());
        aptLocation.setText(appointment.getLocation());
        aptType.setText(appointment.getType());

        custID.setText(String.valueOf(appointment.getCustomer_id()));
        userId.setText(String.valueOf(appointment.getUser_id()));
    }

    /**
     * Initialize the list of contact for user to select.
     */
    private void initContact() {
        contactList.setValue(contactDao.findNameByID(appointment.getContact_id()));
        contactList.setItems(contactDao.findAll());

    }

    /**
     * Initialize the last appointment date and display it on the UI
     */
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
        startMin.setItems(initializeMinutes());
        endHr.setItems(estHr);
        endMin.setItems(initializeMinutes());
    }

    /**
     * This method disabled the weekends option in the date picker.
     */
    private void disabledWeekends() {
        Callback<DatePicker, DateCell> startDayCellFactory = this.getDayCellFactory();
        startDate.setDayCellFactory(startDayCellFactory);
        Callback<DatePicker, DateCell> endDayCellFactory = this.getDayCellFactory();
        endDate.setDayCellFactory(endDayCellFactory);
    }

    /**
     * This method validates user inputs.
     *
     * @param title       title field input
     * @param description description field input
     * @param type        type field input
     * @param location    location field input
     * @param startD      start date field input
     * @param startH      start hour field input
     * @param startM      start minute field input
     * @param endD        end date field input
     * @param endH        end hour field input
     * @param endM        end minute field input
     * @return boolean if all input are valid returns a ture, otherwise, returns a false.
     */
    private boolean areValidInput(String type, String location, String title, String description, LocalDate startD, String startH, String startM, LocalDate endD, String endH, String endM, String contact) {
        return Validator.isValidString(type, location, title) && description != null && contact != null && startD != null && endD != null && Validator.isValidString(startH, startM, endH, endM);
    }
}
