package controller;

import dao.AppointmentDaoImpl;
import dao.ContactDaoImpl;
import dao.UserDaoImpl;
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
import java.time.LocalTime;
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
    @FXML
    private ChoiceBox<String> startMeridiem;

    @FXML
    private ChoiceBox<String> endMeridiem;
    @FXML
    private Label startOfficeHr;

    @FXML
    private Label endOfficeHr;


    private final AppointmentDaoImpl appointmentDao = new AppointmentDaoImpl();
    private final ContactDaoImpl contactDao = new ContactDaoImpl();
    public static Appointment appointment ;

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
        String contactName = contactList.getValue();
        long contactId = contactDao.getContactId(contactName);

        // get appointment time user wishes to book
        LocalDate startD = startDate.getValue();
        LocalDate endD = endDate.getValue();
        String startH = String.valueOf(DateTimeConverter.get24HrTime(Integer.parseInt(startHr.getValue()), startMeridiem.getValue()));
        String endH = String.valueOf(DateTimeConverter.get24HrTime(Integer.parseInt(endHr.getValue()), endMeridiem.getValue()));
        String startM = startMin.getValue();
        String endM = endMin.getValue();

        //convert the time input to LocalDateTime
        LocalDateTime startLocalDateTime = LocalDateTime.of(startD, LocalTime.of(Integer.valueOf(startH), Integer.valueOf(startM)));
        LocalDateTime endLocalDateTime = LocalDateTime.of(endD, LocalTime.of(Integer.valueOf(endH), Integer.valueOf(endM)));

        if (!areValidInput(type, location, title, description, startD, startH, startM, endD, endH, endM, contactName)) {
            Validator.displayInvalidInput("Invalid input. All fields can not be empty");
        } else if (!Validator.isValidAppointmentTime(startD, startH, startM, endD, endH, endM)) {
            Validator.displayInfo("Sorry.\n" +
                    "1. your appointment can not be in the past \n 2. the appointment ending time can not be before the appointment starting time. \n Try again please.");
        } else if (!DateTimeConverter.isWithinOfficeHour(startD, startH, startM, endD, endH, endM)) {
            Validator.displayInfo("Sorry, The time you wish to book is out of office hour.");
        } else if (appointmentDao.isDoubleBooking(appointment.getCustomer_id(),startLocalDateTime, endLocalDateTime)) {
            Validator.displayInfo("Sorry, the time you have selected is already booked, please select a different time.");
        } else {
            Timestamp createdDate = DateTimeConverter.convertLocalTimeToUTC(LocalDateTime.now());
            Timestamp lastUpdate = DateTimeConverter.convertLocalTimeToUTC(LocalDateTime.now());
            Appointment appointmentUpdate = new Appointment(appointment.getAppointment_id(), title, description, location,
                    type, Timestamp.valueOf(startLocalDateTime), Timestamp.valueOf(endLocalDateTime),
                    createdDate, UserDaoImpl.userName, lastUpdate, UserDaoImpl.userName, appointment.getCustomer_id(), contactId, appointment.getUser_id());
            appointmentDao.update(appointmentUpdate);
            Validator.displaySuccess("Appointment is saved");
            setScene(event, Views.APPOINTMENT_RECORD_VIEW.getView());
        }

    }

//    /**
//     * The method generates a set of available time to display on users window when the time is booked or out of EST office hour
//     *
//     * @return
//     * */
//    private String getAvailableTime() {
//        String availableTime = "";
//        Map<LocalTime, LocalTime> availableTimeToDisplay = BookingAvailability.availableTimeToDisplay;
//
//        Iterator iteratorMap = availableTimeToDisplay.entrySet().iterator();
//        while (iteratorMap.hasNext()) {
//            Map.Entry mapElement = (Map.Entry) iteratorMap.next();
//            availableTime = availableTime + mapElement.getKey() + " To "
//                    + mapElement.getValue() + "\n";
//        }
//        return availableTime;
//    }

    /**
     * Display the original appointment information and user can later update directly on the top of those fields.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointment = AppointmentRecordController.selectApt;

        initFields();
        initLastAptDateTime();
        initContact();
//        disabledWeekends();
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
        contactList.setItems(contactDao.findName());
        startOfficeHr.setText(String.valueOf(DateTimeConverter.convertESTOfficeStartHrToLocal()));
        endOfficeHr.setText(String.valueOf(DateTimeConverter.convertESTOfficeEndHrToLocal()));


    }

    /**
     * Initialize the last appointment date and display it on the UI
     */
    private void initLastAptDateTime() {
        LocalDateTime startDateTime = appointment.getStart().toLocalDateTime();
        startDate.setValue(startDateTime.toLocalDate());
        startHr.setValue(DateTimeConverter.getHr(DateTimeConverter.convertHrTo12HrTime(startDateTime.getHour())));
        startMin.setValue(DateTimeConverter.getMint(startDateTime.getMinute()));
        startMeridiem.setValue(DateTimeConverter.getMeridiem(startDateTime.getHour()));

        LocalDateTime endDateTime = appointment.getEnd().toLocalDateTime();
        endDate.setValue(endDateTime.toLocalDate());
        endHr.setValue(DateTimeConverter.getHr(DateTimeConverter.convertHrTo12HrTime(endDateTime.getHour())));
        endMin.setValue(DateTimeConverter.getMint(endDateTime.getMinute()));
        endMeridiem.setValue(DateTimeConverter.getMeridiem(endDateTime.getHour()));

        startHr.setItems(DateTimeConverter.hrList);
        startMin.setItems(DateTimeConverter.minuteList);
        startMeridiem.setItems(DateTimeConverter.meridiemList);

        endHr.setItems(DateTimeConverter.hrList);
        endMin.setItems(DateTimeConverter.minuteList);
        endMeridiem.setItems(DateTimeConverter.meridiemList);
    }

//    /**
//     * This method disabled the weekends option in the date picker.
//     */
//    private void disabledWeekends() {
//        Callback<DatePicker, DateCell> startDayCellFactory = this.getDayCellFactory();
//        startDate.setDayCellFactory(startDayCellFactory);
//        Callback<DatePicker, DateCell> endDayCellFactory = this.getDayCellFactory();
//        endDate.setDayCellFactory(endDayCellFactory);
//    }

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
     * @param contact     a contact id
     * @return boolean if all input are valid returns a ture, otherwise, returns a false.
     */
    private boolean areValidInput(String type, String location, String title, String description, LocalDate startD, String startH, String startM, LocalDate endD, String endH, String endM, String contact) {
        return Validator.isValidString(type, location, title) && description != null && contact != null && startD != null && endD != null && Validator.isValidString(startH, startM, endH, endM);
    }
}
