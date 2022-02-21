package controller;

import dao.CustomerDaoImpl;
import entity.Customer;
import enums.CountryId;
import enums.Views;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.*;

/**
 * This class provides a data control flow between displaying customer record view and database tables
 *
 * @author Linmei M.
 */
public class CustomerRecordController implements Initializable, CommonUseHelperIfc {
    /**
     * The exit button on the customer record screen
     */
    @FXML
    private Button exitId;
    /**
     * The customer record table on the customer record screen
     */
    @FXML
    private TableView<Customer> recordTable;
    /**
     * The customer ID column on the customer record screen
     */
    @FXML
    private TableColumn<Customer, Long> custID;
    /**
     * The customer name column on the customer record screen
     */
    @FXML
    private TableColumn<Customer, String> custName;
    /**
     * The customer address column on the customer record screen
     */
    @FXML
    private TableColumn<Customer, String> custAddress;
    /**
     * The customer zipcode column on the customer record screen
     */
    @FXML
    private TableColumn<Customer, String> custZipCode;
    /**
     * The customer phone number column on the customer record screen
     */
    @FXML
    private TableColumn<Customer, String> custPhoneNum;
    /**
     * The customer division column on the customer record screen
     */
    @FXML
    private TableColumn<Customer, String> custDivision;

    /**
     * Initialize customer dao object
     */
    public CustomerDaoImpl customerDao = new CustomerDaoImpl();
    /**
     * Initialize a list that con contain customer objects
     */
    public ObservableList<Customer> customersDataTable = FXCollections.observableArrayList();
    /**
     * Initialize a customer object that stores the selected customer row
     */
    public static Customer selectedCust;
    /**
     * initialize a country ID
     */
    public static CountryId ctryId;

    /**
     * Initialize a logger for logging user activities
     */
    Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * This method receives an update action event and lead user to the update customer view.
     *
     * @param event an event indicates a component-defined action occurred.
     */
    @FXML
    void UpdateSelected(ActionEvent event) {
        selectedCust = recordTable.getSelectionModel().getSelectedItem();

        if (selectedCust != null) {
            long countryId = selectedCust.getFirstLevelDivision().getCountry_id();
            ctryId = countryId == 1 ? CountryId.US : countryId == 2 ? CountryId.UK : CountryId.CANADA;
            setScene(event, Views.UPDATE_CUSTOMER_VIEW.getView());
        } else {
            Validator.displayInvalidInput("Please select a row/customer to update");
        }
    }

    /**
     * This method deletes selected customer row
     *
     * @param event an event indicates a component-defined action occurred.
     */
    @FXML
    void deleteSelected(ActionEvent event) {
        selectedCust = recordTable.getSelectionModel().getSelectedItem();

        if (selectedCust != null) {
            Validator.displayDeleteConfirmation("the customer ID " + selectedCust.getCustomer_id()+"? \nThe associated appointments will be also deleted!");
            if (Validator.confirmResult.isPresent() && Validator.confirmResult.get() == ButtonType.OK) {
                customerDao.delete(selectedCust);
                customersDataTable.remove(selectedCust);
                Validator.displaySuccess("Deleted");
            }
        } else {
            Validator.displayInvalidInput("Please select a row to delete");
        }
    }

//    /**
//     * This method receives an action of going to the adding a new appointment view.
//     *
//     * @param event an event indicates a component-defined action occurred.
//     */
//    @FXML
//    void addNewAptSelected(ActionEvent event) {
//        selectedCust = recordTable.getSelectionModel().getSelectedItem();
//        if (selectedCust != null) {
//            setScene(event, Views.ADD_NEW_APT_VIEW.getView());
//        } else {
//            Validator.displayInvalidInput("Please select a row/customer to update or Add");
//        }
//    }

    /**
     * This method receives an action of going to the adding a new customer view.
     *
     * @param event an event indicates a component-defined action occurred.
     */
    @FXML
    void addNewCustomer(ActionEvent event) {
        setScene(event, Views.ADD_NEW_CUSTOMER_VIEW.getView());
    }

    /**
     * This method receives an action of going to a view window where displays all the reports.
     *
     * @param event an event indicates a component-defined action occurred.
     */
    @FXML
    void reportsClicked(ActionEvent event) {
        setScene(event, Views.REPORT_VIEW.getView());
    }

    /**
     * This method exits the program.
     *
     * @param event an event indicates a component-defined action occurred.
     */
    @FXML
    void exitBtnClicked(ActionEvent event) {
        exit(event, exitId);
    }

    /**
     * This method switches to a screen where all the appointments will be listed
     *
     * @param actionEvent an event indicates a component-defined action occurred.
     */
    @FXML
    void listAptSelected(ActionEvent actionEvent) {
        setScene(actionEvent, Views.APPOINTMENT_RECORD_VIEW.getView());
    }

    /**
     * Initialize the customer record view.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTable();
        loadData();
    }

    /**
     * Initialize the customer record table.
     */
    private void initTable() {
        initCols();
        try {
            customersDataTable.addAll(customerDao.findAll());
        } catch (Exception e) {
            logger.log(Level.WARNING, "initialize() throws an exception", this.getClass().getName());
        }
    }

    /**
     * Initialize the customer record table columns.
     */
    private void initCols() {
        custID.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        custName.setCellValueFactory(new PropertyValueFactory<>("customer_name"));
        custAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        custZipCode.setCellValueFactory(new PropertyValueFactory<>("postal_code"));
        custPhoneNum.setCellValueFactory(new PropertyValueFactory<>("phone"));
        custDivision.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFirstLevelDivision().getDivision()));
    }

    /**
     * Loads the customer record data.
     */
    private void loadData() {
        recordTable.setItems(customersDataTable);
    }
}
