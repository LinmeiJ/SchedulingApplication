package controller;

import dao.AppointmentDaoImpl;
import dao.Validator;
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

    Logger logger = Logger.getLogger(this.getClass().getName());
    ObservableList<Appointment> aptDataTable = FXCollections.observableArrayList();
    AppointmentDaoImpl appointmentDao = new AppointmentDaoImpl();

    private String[] filterOptions = {"By Month", "By Week"};
    public static boolean isMonthFilter = false;
    public static boolean isWeekFilter = false;
    public static Appointment selectApt;

    /**
     * This method receives an action of going to adding a new appointment view.
     *
     * @param event an event indicates a component-defined action occurred.
     * */
    @FXML
    void addNewClicked(ActionEvent event){
        setScene(event, Views.NEW_APT_VIEW.getView());
    }

    /**
     * This method deletes selected appointment row
     *
     * @param event an event indicates a component-defined action occurred.
     * */
    @FXML
    void deleteClicked(ActionEvent event){
        selectApt = appointmentTable.getSelectionModel().getSelectedItem();
        if(selectApt != null) {
            appointmentDao.delete(selectApt);
            aptDataTable.remove(selectApt);
        }else{
            Validator.displayInvalidInput("Please select a row to delete");
        }
    }

    /**
     * This method exits the view.
     *
     * @param event an event indicates a component-defined action occurred.
     * */
    @FXML
    void exitBtnClicked(ActionEvent event) {
        exit(event, exitBtn);
    }

    /**
     * This method receives an update action event and lead user to the update appointment view.
     *
     * @param event an event indicates a component-defined action occurred.
     * */
    @FXML
    void updateClicked(ActionEvent event) {
        selectApt = appointmentTable.getSelectionModel().getSelectedItem();
        if(selectApt != null) {
            setScene(event, Views.UPDATE_APPOINTMENT_VIEW.getView());
        }else{
            Validator.displayInvalidInput("Please select a row/appointment to update");
        }
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
     * @param event an event indicates a component-defined action occurred.
     */
    @FXML
    void filterByMonthSelected(ActionEvent event){
        filterByWeek.setSelected(false);
        filterByMonth.setSelected(true);
        isMonthFilter = true;
        setScene(event, Views.APPOINTMENT_RECORD_VIEW.getView());
    }

    /**
     * When filtering by week radio button is selected, this method receives the action and reset the month radio button.
     * @param event an event indicates a component-defined action occurred.
     */
    @FXML
    void filterByWeekSelected(ActionEvent event){
        filterByMonth.setSelected(false);
        filterByWeek.setSelected(true);
        isWeekFilter = true;
        setScene(event, Views.APPOINTMENT_RECORD_VIEW.getView());
    }

    /**
     * Initialize appointment record table based on customer ID.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
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
            if(AddNewCustomerController.isNewCust) {
                aptDataTable.addAll(appointmentDao.findAllByCustId(AddNewAppointmentController.newCustID));
                AddNewCustomerController.isNewCust = false;
            }else if(isMonthFilter){
                aptDataTable.addAll(getAptsForCurrentMonth(appointmentDao.findAllByCustId(CustomerRecordController.selectedCust.getCustomer_id())));
                isMonthFilter = false;
            }else if(isWeekFilter){
                aptDataTable.addAll(getAptsForCurrentWeek(appointmentDao.findAllByCustId(CustomerRecordController.selectedCust.getCustomer_id())));
                isWeekFilter = false;
            }
            else {
                aptDataTable.addAll(appointmentDao.findAllByCustId(CustomerRecordController.selectedCust.getCustomer_id()));
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "initialize() throws an exception", this.getClass().getName());
        }
    }

    /**
     * This method sets up the appointment list filtering by current month
     * @param appointments an appointment list of all months
     * @return an appointment list of current month
     */
    private ObservableList<Appointment>  getAptsForCurrentMonth(ObservableList<Appointment> appointments) {
        ObservableList<Appointment> aptByMonth = FXCollections.observableArrayList();
        Month currentMonth = LocalDate.now().getMonth();
        for(Appointment apt : appointments){
            if(apt.getStart().toLocalDateTime().toLocalDate().getMonth().equals(currentMonth)){
                aptByMonth.add(apt);
            }
        }
        return aptByMonth;
    }

    /**
     * This method sets up the appointment list filtering by current week
     * @param appointments an appointment list of all weeks
     * @return an appointment list of current week
     */
    private ObservableList<Appointment> getAptsForCurrentWeek(ObservableList<Appointment> appointments) {
       ObservableList<Appointment> aptByWeek = FXCollections.observableArrayList();

       LocalDate monday = DateTimeConverter.getMondayDate();
       LocalDate sunday = DateTimeConverter.getSundayDate();

       for(Appointment apt : appointments){
           if(apt.getStart().toLocalDateTime().toLocalDate().compareTo(monday) >= 0 &&
                   apt.getStart().toLocalDateTime().toLocalDate().compareTo(sunday) <= 0){
               aptByWeek.add(apt);
           }
       }
      return aptByWeek;
    }

    /**
     * Initialize the appointment record table columns.
     */
    private void initCols() {
        aptID.setCellValueFactory(new PropertyValueFactory<>("appointment_id"));
        aptTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        aptTitle.setEditable(true);
        aptDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        aptLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        aptType.setCellValueFactory(new PropertyValueFactory<>("type"));
        aptStartDateTime.setCellValueFactory(new PropertyValueFactory<>("start"));
        aptEndDateTime.setCellValueFactory(new PropertyValueFactory<>("end"));
        aptCustID.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        aptUserID.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        aptContact.setCellValueFactory(new PropertyValueFactory<>("contact_id"));
    }
}