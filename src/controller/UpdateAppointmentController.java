package controller;

import Dao.AppointmentDaoImpl;
import Dao.Validator;
import dbConnection.JDBCConnection;
import entity.Appointment;
import entity.Customer;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateAppointmentController extends JDBCConnection implements Initializable, CommonUseHelperIfc {

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
    private Button leftArrow;

    @FXML
    private ChoiceBox<String> dateTimefilter;

    @FXML
    private Button rightArrow;

    @FXML
    private Button exitBtn;

    Logger logger = Logger.getLogger(this.getClass().getName());
    private String[] filterOptions = {"By Month", "By Week"};
    ObservableList<Appointment> aptDataTable = FXCollections.observableArrayList();
    AppointmentDaoImpl appointmentDao = new AppointmentDaoImpl();
    public static Appointment selecteApt;

    @FXML
    void FilterButton(MouseEvent event) {

    }

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
    void rightArrowClicked(ActionEvent event) {

    }

    @FXML
    void updateClicked(ActionEvent event) {
        selecteApt = appointmentTable.getSelectionModel().getSelectedItem();
        appointmentDao.update(selecteApt);
    }

    @FXML
    void backExitClicked(ActionEvent event) throws IOException {
        setScene(event, CUSTOMER_RECORD_VIEW);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateTimefilter.getItems().addAll(filterOptions);
        initTable();
        loadData();
    }

    private void initTable() {
        initCols();
        try {
            aptDataTable.addAll(appointmentDao.findAllByCustId(CustomerRecordController.selectedCust.getCustomer_id()));
        } catch (Exception e) {
            logger.log(Level.WARNING, "initialize() throws an exception", this.getClass().getName());
        }
    }

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
        aptContact.setCellValueFactory(new PropertyValueFactory<>("contact_id"));
    }

    private void loadData() {
        appointmentTable.setItems(aptDataTable);
    }
}