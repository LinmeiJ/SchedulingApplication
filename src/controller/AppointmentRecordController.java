package controller;

import dao.AppointmentDaoImpl;
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
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * This class provides a data control flow between displaying appointment record view and database tables
 *
 * @author Linmei M.
 */
public class AppointmentRecordController extends JDBCConnection implements Initializable, CommonUseHelperIfc {
    @FXML
    private TableColumn<Appointment, String> aptContact;
    @FXML
    private TableColumn<Appointment, Long> aptCustID;
    @FXML
    private TableColumn<Appointment, String> aptDescription;
    @FXML
    private TableColumn<Appointment, Timestamp> aptStartDateTime;
    @FXML
    private TableColumn<Appointment, Timestamp> aptEndDateTime;
    @FXML
    private TableColumn<Appointment, Long> aptID;
    @FXML
    private TableColumn<Appointment, String> aptLocation;
    @FXML
    private TableColumn<Appointment, String> aptTitle;
    @FXML
    private TableColumn<Appointment, String> aptType;
    @FXML
    private TableColumn<Appointment, Long> aptUserID;
    @FXML
    private TableView<Appointment> appointmentTable;
    @FXML
    private Button exitBtn;
    @FXML
    private RadioButton filterByMonth;
    @FXML
    private RadioButton filterByWeek;
    @FXML
    private RadioButton listAll;

    /**
     * Initialize a list that can contains Appointment object
     */
    ObservableList<Appointment> aptDataTable = FXCollections.observableArrayList();
    /**
     * Initialize an appointment dao object
     */
    AppointmentDaoImpl appointmentDao = new AppointmentDaoImpl();
    /**
     * Initialize isMonthFilter to false
     */
    public static boolean isMonthFilter = false;
    /**
     * Initialize isWeekFilter to false
     */
    public static boolean isWeekFilter = false;
    /**
     * Initialize listAllFilter to false
     */
    public static boolean listAllFilter = false;
    /**
     * Initialize Appointment object to store the appointment that is selected from the appointment record table
     */
    public static Appointment selectApt;

    /**
     * This method deletes selected appointment row
     *
     * @param event an event indicates a component-defined action occurred.
     */
    @FXML
    void deleteClicked(ActionEvent event) {
        selectApt = appointmentTable.getSelectionModel().getSelectedItem();
        if (selectApt != null) {
            Validator.displayDeleteConfirmation("the appointment ID " + selectApt.getAppointment_id() + " and the type is " + selectApt.getType());
            if (Validator.confirmResult.isPresent() && Validator.confirmResult.get() == ButtonType.OK) {
                appointmentDao.delete(selectApt);
                aptDataTable.remove(selectApt);
                Validator.displaySuccess("The appointment ID " + selectApt.getAppointment_id() + " and type as " + selectApt.getType() + " is deleted");
            }
        } else {
            Validator.displayInvalidInput("Please select a row to delete");
        }
    }

    /**
     * This method exits the program.
     *
     * @param event an event indicates a component-defined action occurred.
     */
    @FXML
    void exitBtnClicked(ActionEvent event) {
        exit(event, exitBtn);
    }

    /**
     * This method receives an update action event and lead user to the update appointment view.
     *
     * @param event an event indicates a component-defined action occurred.
     */
    @FXML
    void updateClicked(ActionEvent event) {
        selectApt = appointmentTable.getSelectionModel().getSelectedItem();
        if (selectApt != null) {
            setScene(event, Views.UPDATE_APPOINTMENT_VIEW.getView());
        } else {
            Validator.displayInvalidInput("Please select a row/appointment to update");
        }
    }

    @FXML
    void addNewAptClicked(ActionEvent event) {
        setScene(event, Views.ADD_NEW_APT_VIEW.getView());
    }

    /**
     * This method sets the scene to the previous scene.
     *
     * @param event an event indicates a component-defined action occurred.
     **/
    @FXML
    void backClicked(ActionEvent event) {
        setScene(event, Views.CUSTOMER_RECORD_VIEW.getView());
    }

    /**
     * When filtering by month radio button is selected, this method receives the action and reset the week radio button.
     *
     * @param event an event indicates a component-defined action occurred.
     */
    @FXML
    void filterByMonthSelected(ActionEvent event) {
        filterByWeek.setSelected(false);
        listAll.setSelected(false);
        isMonthFilter = true;
        setScene(event, Views.APPOINTMENT_RECORD_VIEW.getView());
    }

    /**
     * When filtering by week radio button is selected, this method receives the action and reset the month radio button.
     *
     * @param event an event indicates a component-defined action occurred.
     */
    @FXML
    void filterByWeekSelected(ActionEvent event) {
        filterByMonth.setSelected(false);
        listAll.setSelected(false);
        isWeekFilter = true;
        setScene(event, Views.APPOINTMENT_RECORD_VIEW.getView());
    }

    /**
     * When filtering by listing all button is selected, this method receives the action and resets the record.
     *
     * @param event an event indicates a component-defined action occurred.
     */
    public void listAllSelected(ActionEvent event) {
        filterByMonth.setSelected(false);
        filterByWeek.setSelected(false);
        listAllFilter = true;
        setScene(event, Views.APPOINTMENT_RECORD_VIEW.getView());
    }

    /**
     * Initialize appointment record table based on customer ID.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filterByMonth.setSelected(false);
        filterByWeek.setSelected(false);
        initTable();
        appointmentTable.setItems(aptDataTable);
    }

    /**
     * Initialize the appointment table based on the filters.
     */
    private void initTable() {
        initCols();
        try {
//            if (AddNewCustomerController.isNewCust) {//fix me
////                aptDataTable.addAll(appointmentDao.findAllAppointment(AddNewAppointmentController.newCustID));
//                AddNewCustomerController.isNewCust = false;
//            } else
            if (isMonthFilter) {
                filterByMonth.setSelected(true);
                aptDataTable.addAll(getAptsForCurrentMonth(appointmentDao.findAllAppointment()));
                isMonthFilter = false;
            } else if (isWeekFilter) {
                filterByWeek.setSelected(true);
                aptDataTable.addAll(getAptsForCurrentWeek(appointmentDao.findAllAppointment()));
                isWeekFilter = false;
            } else {
                listAll.setSelected(true);
                aptDataTable.addAll(appointmentDao.findAllAppointment());
                listAllFilter = false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Lambda expression #2.This method sets up the appointment list filtering by current month
     *
     * @param appointments an appointment list of all months
     * @return an appointment list of current month
     */
    private ObservableList<Appointment> getAptsForCurrentMonth(ObservableList<Appointment> appointments) {
        ObservableList<Appointment> aptByMonth;
        Month currentMonth = LocalDate.now().getMonth();
        //lambda expression: get the current week appointments by filters starting from sunday to sarturday
        aptByMonth = appointments.stream()
                .filter(apt -> (apt.getStart().toLocalDateTime().toLocalDate().getMonth().equals(currentMonth)))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        return aptByMonth;
    }

    /**
     * This method sets up the appointment list filtering by current week
     *
     * @param appointments an appointment list of all weeks
     * @return an appointment list of current week
     */
    private ObservableList<Appointment> getAptsForCurrentWeek(ObservableList<Appointment> appointments) {
        ObservableList<Appointment> aptByWeek;

        LocalDate sunday = DateTimeConverter.getSundayDate(); // first day of a week
        LocalDate saturday = DateTimeConverter.getSaturdayDate(); // last day of a week

        //lambda expression: filter and save a list of appointments that is only for current week.
        aptByWeek = appointments.stream()
                .filter(apt -> (apt.getStart().toLocalDateTime().toLocalDate().compareTo(saturday) <= 0 &&
                        apt.getStart().toLocalDateTime().toLocalDate().compareTo(sunday) >= 0))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        return aptByWeek;
    }

    /**
     * Initialize the appointment record table columns.
     */
    private void initCols() {
        aptID.setCellValueFactory(new PropertyValueFactory<>("appointment_id"));
        aptTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        aptDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        aptLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        aptType.setCellValueFactory(new PropertyValueFactory<>("type"));
        aptStartDateTime.setCellValueFactory(new PropertyValueFactory<>("start"));
        aptEndDateTime.setCellValueFactory(new PropertyValueFactory<>("end"));
        aptCustID.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        aptUserID.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        aptContact.setCellValueFactory(new PropertyValueFactory<>("contact_name"));
    }
}