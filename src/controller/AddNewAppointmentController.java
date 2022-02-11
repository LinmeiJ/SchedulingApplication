package controller;

import dao.*;
import dateTimeUtil.BookingAvailability;
import dateTimeUtil.DateTimeConverter;
import entity.Appointment;
import enums.Views;
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

    private Appointment appointment;
    private ContactDaoImpl contactDao = new ContactDaoImpl();
    private CustomerDaoImpl customerDao = new CustomerDaoImpl();
    private AppointmentDaoImpl appointmentDao = new AppointmentDaoImpl();
    public static long newCustID;

    @FXML
    void saveIsClicked(ActionEvent event) throws SQLException, IOException {
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
        String contactName = contactList.getValue();
        long contactId = contactDao.getContactId(contactName);


        if(!areValidInput(type, location, title, description, startD, startH, startM, endD, endH, endM, contactName)){
            Validator.displayInvalidInput("Invalid input. \n requires:\n" +
                    "Only alphabets are allowed for Type, Location, Title and all fields can not be empty");
        }else if(appointmentDao.isDoubleBooking(contactId, startD, startH, startM, endD, endH, endM)){
            Validator.displayInfo("Sorry, the time you have selected is booked, please select a different time. Available Time listed here (in EST timezone): \n" + getAvailableTime());
        }else{
            saveNewAppointment(event, title, description, type, location, startD, startH, startM, endD, endH, endM, contactId);
        }
    }

    private void saveNewAppointment(ActionEvent event, String title, String description, String type, String location, LocalDate startD, String startH, String startM, LocalDate endD, String endH, String endM, long contactId) throws SQLException {
        appointment = new Appointment();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startDate.setValue(LocalDate.now());
        startDate.setShowWeekNumbers(true);
        endDate.setValue(LocalDate.now());
        endDate.setShowWeekNumbers(true);

        Callback<DatePicker, DateCell> startDayCellFactory = this.getDayCellFactory();
        startDate.setDayCellFactory(startDayCellFactory);
        Callback<DatePicker, DateCell> endDayCellFactory = this.getDayCellFactory();
        endDate.setDayCellFactory(endDayCellFactory);

        startHr.setItems(estHr);
        startMinute.setItems(initializeMinutes());
        endHr.setItems(estHr);
        endMinute.setItems(initializeMinutes());
        try {
            contactList.setItems(contactDao.findAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void existIsClicked(ActionEvent event) {
        exit(event, exitBtn);
    }

    public void backToLastViewIsClicked(ActionEvent actionEvent) throws IOException {
        setScene(actionEvent, Views.CUSTOMER_RECORD_VIEW.getView());
    }

    private boolean areValidInput(String type, String location, String title, String description, LocalDate startD, String startH, String startM, LocalDate endD, String endH, String endM, String contact) {
        return Validator.isValidString(type, location, title) && description != null && contact != null && startD != null && endD != null && Validator.isValidString(startH, startM, endH, endM);
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

}