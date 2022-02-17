package controller;

import dao.*;
import dateTimeUtil.BookingAvailability;
import dateTimeUtil.DateTimeConverter;
import entity.Appointment;
import entity.Contact;
import enums.Views;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * This class provides a data control flow between the add new appointment view and database tables
 *
 * @author Linmei M.
 */
public class AddNewAppointmentController implements Initializable, CommonUseHelperIfc {
    @FXML
    private TextField aptTitleField;
    @FXML
    private TextField aptTypeField;
    @FXML
    private TextField aptLocationField;
    @FXML
    private TextArea aptDescriptionField;
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
    private ChoiceBox<String> contactList;
    @FXML
    private Label customerId;
    @FXML
    private Label userId;
    @FXML

    private final ContactDaoImpl contactDao = new ContactDaoImpl();
    private final CustomerDaoImpl customerDao = new CustomerDaoImpl();
    private final AppointmentDaoImpl appointmentDao = new AppointmentDaoImpl();
    public static long newCustID; // new customer ID

    /**
     * getting the date from user, validates the user inputs, and save the input to database.
     *
     * <p>
     *     lambda expression #1
     * </p>
     * @param event JavaFX button press event
     */
    @FXML
    public void saveIsClicked(ActionEvent event) {
        String title = aptTitleField.getText();
        String description = aptDescriptionField.getText();
        String type = aptTypeField.getText();
        String location = aptLocationField.getText();
        LocalDate startD = startDate.getValue();
        String startH = startHr.getValue();
        String startM = startMinute.getValue();
        LocalDate endD = endDate.getValue();

        String endH = endHr.getValue();
        String endM = endMinute.getValue();
        String contactName = contactList.getValue();//fix me
        long contactId = contactDao.getContactId(contactName);
        //lambda expression #1: fix me - give a purpose!
        Function<LocalDate, String> localOfficeStartHr = hr -> {
            LocalDateTime estOfficeHrOfTheDay = LocalDateTime.of(hr, LocalTime.of(8, 0));
            return String.valueOf(DateTimeConverter.convertESTToLocal(String.valueOf(estOfficeHrOfTheDay)).toLocalDateTime().toLocalTime());
        };

        if (!areValidInput(type, location, title, description, startD, startH, startM, endD, endH, endM, contactName)) {
            Validator.displayInvalidInput("Invalid input. \n requires:\n\n" +
                    "Only alphabets are allowed for Type, Location, Title \n and all fields can not be empty");
        } else if (!Validator.isValidAppointmentTime(startD, startH, startM, endD, endH, endM)) {
            Validator.displayInfo("Sorry, your appointment can not be in the past or the appointment ending can not be before the appointment starting time. Try again please.");
        } else if (!DateTimeConverter.isWithinOfficeHour(startD, startH, startM)) {
            Validator.displayInfo("Sorry, The time you wish to book is out of the EST timezone office hour. \nThe office hour starts "
                    + DateTimeConverter.getOfficeHourOfTheDay(startD)
                    + " on your local time. Please select a different time.");
        } else if (appointmentDao.isDoubleBooking(contactId, startD, startH, startM, endD, endH, endM)) {
            Validator.displayInfo("Sorry, the time you have selected is booked, please select a different time. \nAvailable office hours for the same date in EST time(please check your localtime if you are not in EST timezone) is below: \n" + getAvailableTime()
                    + "Keep in mind, the EST office hour starts at " + localOfficeStartHr.apply(startD) + " at your time and open for 14 hours a day");
        } else {
            saveNewAppointment(event, title, description, type, location, startD, startH, startM, endD, endH, endM, contactId);
        }
    }

    /**
     * Saves all user inputs after the validations
     *
     * @param event       an event indicates a component-defined action occurred.
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
    private void saveNewAppointment(ActionEvent event, String title, String description, String type, String location, LocalDate startD, String startH, String startM, LocalDate endD, String endH, String endM, long contactId) {
        Appointment appointment = new Appointment();

        appointment.setTitle(title);
        appointment.setDescription(description);
        appointment.setType(type);
        appointment.setLocation(location);
        appointment.setStart(DateTimeConverter.convertAptTimeToUTC(startD, startH, startM));
        appointment.setEnd(DateTimeConverter.convertAptTimeToUTC(endD, endH, endM));

        appointment.setCreated_date(DateTimeConverter.convertLocalTimeToUTC(LocalDateTime.now()));
        appointment.setCreated_by(UserDaoImpl.userName);
        appointment.setLast_update(DateTimeConverter.convertLocalTimeToUTC(LocalDateTime.now()));
        appointment.setLast_updated_by(UserDaoImpl.userName);

        if (AddNewCustomerController.isNewCust) {
            newCustID = customerDao.findIdByNameAndDivisionId(AddNewCustomerController.customer.getCustomer_name(), AddNewCustomerController.customer.getDivision_id());
            appointment.setCustomer_id(newCustID);
        } else {
            appointment.setCustomer_id(CustomerRecordController.selectedCust.getCustomer_id());
        }
        appointment.setUser_id(UserDaoImpl.userId);
        appointment.setContact_id(contactId);

        appointmentDao.save(appointment);
        Validator.displaySuccess("Appointment is saved");
        setScene(event, Views.APPOINTMENT_RECORD_VIEW.getView());

    }

    /**
     * This method exits the view.
     *
     * @param event an event indicates a component-defined action occurred.
     */
    @FXML
    void existIsClicked(ActionEvent event) {
        exit(event, exitBtn);
    }

    /**
     * This method sets the scene to the previous scene.
     *
     * @param actionEvent an event indicates a component-defined action occurred.
     **/
    public void backToLastViewIsClicked(ActionEvent actionEvent) {
        setScene(actionEvent, Views.CUSTOMER_RECORD_VIEW.getView());
    }

    /**
     * Validates user input by checking the input can not be null or whether meet certain requirements.
     *
     * @param title       title field input.
     * @param description description field input.
     * @param type        type field input.
     * @param location    location field input.
     * @param startD      start date field input.
     * @param startH      start hour field input.
     * @param startM      start minute field input.
     * @param endD        end date field input.
     * @param endH        end hour field input.
     * @param endM        end minute field input.
     * @param contact     contact ID.
     * @return boolean not valid returns a false, otherwise returns a true.
     */
    private boolean areValidInput(String type, String location, String title, String description, LocalDate startD, String startH, String startM, LocalDate endD, String endH, String endM, String contact) {
        return Validator.isValidString(type, location, title) && description.length() > 0 && contact != null && startD != null && endD != null && startM != null && startH != null && endM != null && endH != null;
    }

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
     * Initialize all the required fields for creating a new appointment view.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startDate.setValue(LocalDate.now());
        startDate.setShowWeekNumbers(true);

        endDate.setValue(LocalDate.now());
        endDate.setShowWeekNumbers(true);

//        Callback<DatePicker, DateCell> startDayCellFactory = this.getDayCellFactory();
//        startDate.setDayCellFactory(startDayCellFactory);
//        Callback<DatePicker, DateCell> endDayCellFactory = this.getDayCellFactory();
//        endDate.setDayCellFactory(endDayCellFactory);
//
//        startHr.setItems(estHr);
//        startMinute.setItems(initializeMinutes());
//        endHr.setItems(estHr);
//        endMinute.setItems(initializeMinutes());

        contactList.setItems(contactDao.findAll()
                .stream()
                .map(contact -> contact.getContact_name())
                .collect(Collectors.toCollection(FXCollections::observableArrayList)));
//        customerId.setText(customerDao.findAll().stream().map(c -> c.));
        userId.setText("User ID: " + UserDaoImpl.userId);

    }
}