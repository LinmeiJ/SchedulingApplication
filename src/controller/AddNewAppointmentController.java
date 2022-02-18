package controller;

import dao.*;
import dateTimeUtil.BookingAvailability;
import dateTimeUtil.DateTimeConverter;
import entity.Appointment;
import entity.Contact;
import entity.Customer;
import entity.User;
import enums.Views;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
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
    private DatePicker endDate;
    @FXML
    private ComboBox<String> endHr;
    @FXML
    private ComboBox<String> endMin;
    @FXML
    private ChoiceBox<String> endMeridiem;
    @FXML
    private DatePicker startDate;
    @FXML
    private ComboBox<String> startHr;
    @FXML
    private ComboBox<String> startMin;
    @FXML
    private ChoiceBox<String> startMeridiem;
    @FXML
    private Button exitBtn;
    @FXML
    private ComboBox<String> contactList;
    @FXML
    private ComboBox<String> customerList;
    @FXML
    private ComboBox<String> userList;

    @FXML
    private Label LocalOfficeHrStart;
    @FXML
    private Label localOfficeHrEnd;


    private final UserDaoImpl userDao = new UserDaoImpl();
    private final ContactDaoImpl contactDao = new ContactDaoImpl();
    private final CustomerDaoImpl customerDao = new CustomerDaoImpl();
    private final AppointmentDaoImpl appointmentDao = new AppointmentDaoImpl();
    public static long newCustID; // new customer ID
    Map<Integer, String> userMap;
    Map<Integer, String> customerMap;
    Map<Integer, String> contactMap;

    /**
     * getting the date from user, validates the user inputs, and save the input to database.
     *
     * @param event JavaFX button press event
     */
    @FXML
    void saveIsClicked(ActionEvent event) {
        String title = aptTitleField.getText();
        String description = aptDescriptionField.getText();
        String type = aptTypeField.getText();
        String location = aptLocationField.getText();
        String contactName = contactList.getValue();
        String customerName = customerList.getValue();
        String userName = userList.getValue();
        int contactId = getID(contactName, contactMap);
        int customerId = getID(contactName, customerMap);
        int userId = getID(contactName, userMap);

        // get appointment time user wishes to book
        LocalDate startD = startDate.getValue();
        LocalDate endD = endDate.getValue();
        String startH = String.valueOf(DateTimeConverter.get24HrTime(Integer.parseInt(startHr.getValue()), startMeridiem.getValue()));
        String endH = String.valueOf(DateTimeConverter.get24HrTime(Integer.parseInt(endHr.getValue()), endMeridiem.getValue()));
        String startM = startMin.getValue();
        String endM = endMin.getValue();

        //convert the time input to LocalDateTime
        LocalDateTime  startLocalDateTime = LocalDateTime.of(startD, LocalTime.of(Integer.valueOf(startH), Integer.valueOf(startM)));
        LocalDateTime  endLocalDateTime = LocalDateTime.of(endD, LocalTime.of(Integer.valueOf(endH), Integer.valueOf(endM)));

        if (!areValidInput(type, location, title, description, startD, startH, startM, endD, endH, endM, contactName, customerName, userName)) {
            Validator.displayInvalidInput("Invalid input. All fields can not be empty");
        } else if (!Validator.isValidAppointmentTime(startD, startH, startM, endD, endH, endM)){
            Validator.displayInfo("Sorry, your appointment can not be in the past or the appointment ending can not be before the appointment starting time. Try again please.");
        } else if (!DateTimeConverter.isWithinOfficeHour(startD, startH, startM)) {
            Validator.displayInfo("Sorry, The time you wish to book is out of office hour.");
        } else if (appointmentDao.isDoubleBooking(contactId, startD, startH, startM, endD, endH, endM)) {
            Validator.displayInfo("Sorry, the time you have selected is already booked, please select a different time.");
        } else {
            saveNewAppointment(event, title, description, type, location, startD, startH, startM, endD, endH, endM, contactId);
        }
    }

    private int getID(String contactName, Map<Integer, String> map) {
        for (Map.Entry<Integer, String> contact : map.entrySet()) {
            if(contact.getValue().equals(contactName))
                return contact.getKey();
        }
        return 0;
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
    private boolean areValidInput(String type, String location, String title, String description, LocalDate startD, String startH, String startM, LocalDate endD, String endH, String endM, String contact, String customer, String user) {
        return Validator.isValidString(type, location, title) && description.length() > 0 && contact != null && customer != null && user != null && startD != null && endD != null && startM != null && startH != null && endM != null && endH != null;
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
        setDateTime();
        setOfficeHrLabels();
        contactList.getItems().addAll(convertContactListToIDNamePair().values());
        customerList.getItems().addAll(convertCustomerListToIDNamePair().values());
        userList.getItems().addAll(convertUserListToIDNamePair().values());

    }

    private void setOfficeHrLabels() {
        LocalOfficeHrStart.setText(DateTimeConverter.convertESTOfficeStartHrToLocal());
        localOfficeHrEnd.setText(DateTimeConverter.convertESTOfficeEndHrToLocal());
    }

    /**
     * This method initialize the data hour time for user to select - weekends are disabled
     */
    private void setDateTime() {
        startDate.setValue(LocalDate.now());
        startDate.setShowWeekNumbers(true);

        endDate.setValue(LocalDate.now());
        endDate.setShowWeekNumbers(true);

        Callback<DatePicker, DateCell> startDayCellFactory = this.getDayCellFactory();
        startDate.setDayCellFactory(startDayCellFactory);
        Callback<DatePicker, DateCell> endDayCellFactory = this.getDayCellFactory();
        endDate.setDayCellFactory(endDayCellFactory);

        startHr.setItems(DateTimeConverter.hrList);
        startMin.setItems(DateTimeConverter.minuteList);
        startMeridiem.setItems(DateTimeConverter.meridiemList);

        endHr.setItems(DateTimeConverter.hrList);
        endMin.setItems(DateTimeConverter.minuteList);
        endMeridiem.setItems(DateTimeConverter.meridiemList);
    }


    /**
     * Lambda expression: Convert a list of user object into a user ID and user Name key value pair map
     *
     * @return a map that contains only user ID and its corresponding user name
     */
    public Map<Integer, String> convertUserListToIDNamePair() {
        userMap = userDao.findAll()
                .stream()
                .collect(Collectors.toMap(User::getUser_id, User::getUser_name));
        return userMap;
    }

    /**
     * Lambda expression #1: Convert a list of contact object into a contact ID and contact Name key value pair map
     *
     * @return a map that contains only contact ID and its corresponding contact name
     */
    public Map<Integer, String> convertContactListToIDNamePair() {
        contactMap = contactDao.findAll()
                .stream()
                .collect(Collectors.toMap(Contact::getContact_id, Contact::getContact_name));
        return contactMap;
    }

    /**
     * Lambda expression: Convert a list of Customer object into a Customer ID and Customer Name key value pair map
     *
     * @return a map that contains only Customer ID and its corresponding Customer name
     */
    public Map<Integer, String> convertCustomerListToIDNamePair() {
        customerMap = customerDao.findAll()
                .stream()
                .collect(Collectors.toMap(Customer::getCustomer_id, Customer::getCustomer_name));
        return customerMap;
    }

}