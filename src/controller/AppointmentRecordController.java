package controller;

import dao.AppointmentDaoImpl;
import dao.Validator;
import dateUtil.DateTimeConverter;
import dbConnection.JDBCConnection;
import entity.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private String[] filterOptions = {"By Month", "By Week"};
    ObservableList<Appointment> aptDataTable = FXCollections.observableArrayList();
    AppointmentDaoImpl appointmentDao = new AppointmentDaoImpl();
    public static Appointment selecteApt;
    public static boolean isMonthFilter = false;
    public static boolean isWeekFilter = false;

    @FXML
    void addNewClicked(ActionEvent event) throws IOException {
        setScene(event, NEW_APT_VIEW);
    }

    @FXML
    void deleteClicked(ActionEvent event) throws IOException {
        selecteApt = appointmentTable.getSelectionModel().getSelectedItem();
        if(selecteApt != null) {
            appointmentDao.delete(selecteApt);
            aptDataTable.remove(selecteApt);
        }else{
            Validator.displayInvalidInput("Please select a row to delete");
        }
    }

    @FXML
    void exitBtnClicked(ActionEvent event) {
        exit(event, exitBtn);
    }


    @FXML
    void updateClicked(ActionEvent event) throws IOException {
        selecteApt = appointmentTable.getSelectionModel().getSelectedItem();
        setScene(event, UPDATE_APPOINTMENT_VIEW);
    }

    @FXML
    void backExitClicked(ActionEvent event) throws IOException {
        setScene(event, CUSTOMER_RECORD_VIEW);
    }
    @FXML
    void filterByMonthSelected(ActionEvent event) throws SQLException, IOException {
        filterByWeek.setSelected(false);
        filterByMonth.setSelected(true);
        isMonthFilter = true;
        setScene(event, APPOINTMENT_RECORD_VIEW);
    }

    @FXML
    void filterByWeekSelected(ActionEvent event) throws IOException {
        filterByMonth.setSelected(false);
        filterByWeek.setSelected(true);
        isWeekFilter = true;
        setScene(event, APPOINTMENT_RECORD_VIEW);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filterByMonth.setSelected(false);
        filterByWeek.setSelected(false);
        initTable();
        loadData();
    }

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

//        editableCol();
    }
//
//    private void editableCol() {
//        aptTitle.setCellFactory(TextFieldTableCell.forTableColumn());
//        aptTitle.setOnEditCommit( e ->{
//            e.getTableView().getItems().get(e.getTablePosition().getRow()).setTitle(e.getNewValue());
//        });
//        appointmentTable.setEditable(true);
//    }

    private void loadData() {
        appointmentTable.setItems(aptDataTable);
    }
}