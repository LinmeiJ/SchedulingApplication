package controller;

import Dao.CustomerDaoImpl;
import Dao.Validator;
import entity.Customer;
import enums.CountryId;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.*;

public class CustomerRecordController implements Initializable, CommonUseHelperIfc {

    @FXML
    private Button exitId;

    @FXML
    private AnchorPane CustomerRecordPane;

    @FXML
    private TableView<Customer> recordTable;

    @FXML
    private TableColumn<Customer, Long> custID;

    @FXML
    private TableColumn<Customer, String> custName;

    @FXML
    private TableColumn<Customer, String> custAddress;

    @FXML
    private TableColumn<Customer, String> custZipCode;

    @FXML
    private TableColumn<Customer, String> custPhoneNum;

    @FXML
    private TableColumn<Customer, String> custDivision;

    public  CustomerDaoImpl customerDao = new CustomerDaoImpl();
    public  ObservableList<Customer> customersDataTable = FXCollections.observableArrayList();
    public static Customer selectedCust;
    public static CountryId ctryId;

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTable();
        loadData();
    }
    private void initTable() {
        initCols();
        try {
            customersDataTable.addAll(customerDao.findAll());
        } catch (Exception e) {
            logger.log(Level.WARNING, "initialize() throws an exception", this.getClass().getName());
        }
    }

    private void initCols() {
        custID.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        custName.setCellValueFactory(new PropertyValueFactory<>("customer_name"));
        custAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        custZipCode.setCellValueFactory(new PropertyValueFactory<>("postal_code"));
        custPhoneNum.setCellValueFactory(new PropertyValueFactory<>("phone"));
        custDivision.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFirstLevelDivision().getDivision()));
    }


    private void loadData() {
        recordTable.setItems(customersDataTable);
    }

    @FXML
    void UpdateSelected(ActionEvent event) throws IOException {
        selectedCust =  recordTable.getSelectionModel().getSelectedItem();
        long countryId = selectedCust.getFirstLevelDivision().getCountry_id();
        ctryId = countryId == 1? CountryId.US : countryId == 2? CountryId.UK : CountryId.CANADA;

        if(selectedCust != null) {
            setScene(event, UPDATE_CUSTOMER_VIEW);
        }else{
            Validator.displayInvalidInput("Please select a row/customer to update");
        }
    }

    @FXML
    void deleteSelected(ActionEvent event) throws IOException {
        selectedCust = recordTable.getSelectionModel().getSelectedItem();

        if(selectedCust != null) {
            customerDao.delete(selectedCust);
            customersDataTable.remove(selectedCust);
        }
        else
        {
            Validator.displayInvalidInput("Please select a row to delete");
        }
    }

    @FXML
    void addNewAptSelected(ActionEvent event) throws IOException {
        selectedCust = recordTable.getSelectionModel().getSelectedItem();

        if(selectedCust != null) {
            setScene(event,  NEW_APT_VIEW);
        }else{
            Validator.displayInvalidInput("Please select a row/customer to update");
        }
    }

    @FXML
    void addNewCustomer(ActionEvent event) throws IOException {
        setScene(event, ADD_NEW_CUSTOMER_VIEW);
    }

    @FXML
    void exitBtnClicked(ActionEvent event) {
        exit(event, exitId);
    }

    @FXML
    void listAptSelected(ActionEvent actionEvent) throws IOException {
        selectedCust =  recordTable.getSelectionModel().getSelectedItem();
        if(selectedCust != null) {
            setScene(actionEvent, APPOINTMENT_RECORD_VIEW);
        }else{
            Validator.displayInvalidInput("Please select a row/customer to update");
        }
    }
}
