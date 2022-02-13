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
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.*;
/**
 * This class provides a data control flow between displaying customer record view and database tables
 *
 * @author Linmei M.
 */
public class CustomerRecordController implements Initializable, CommonUseHelperIfc {
    @FXML
    private Button exitId;
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

    /**
     * This method receives an update action event and lead user to the update customer view.
     * @param event an event indicates a component-defined action occurred.
     * */
    @FXML
    void UpdateSelected(ActionEvent event){
        selectedCust =  recordTable.getSelectionModel().getSelectedItem();

        if(selectedCust != null) {
            long countryId = selectedCust.getFirstLevelDivision().getCountry_id();
            ctryId = countryId == 1? CountryId.US : countryId == 2? CountryId.UK : CountryId.CANADA;
            setScene(event, Views.UPDATE_CUSTOMER_VIEW.getView());
        }else{
            Validator.displayInvalidInput("Please select a row/customer to update");
        }
    }

    /**
     * This method deletes selected customer row
     * @param event an event indicates a component-defined action occurred.
     * */
    @FXML
    void deleteSelected(ActionEvent event){
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

    /**
     * This method receives an action of going to the adding a new appointment view.
     * @param event an event indicates a component-defined action occurred.
     * */
    @FXML
    void addNewAptSelected(ActionEvent event) throws IOException {
        selectedCust = recordTable.getSelectionModel().getSelectedItem();

        if(selectedCust != null) {
            setScene(event,  Views.ADD_NEW_APT_VIEW.getView());
        }else{
            Validator.displayInvalidInput("Please select a row/customer to update or Add");
        }
    }

    /**
     * This method receives an action of going to the adding a new customer view.
     *
     * @param event an event indicates a component-defined action occurred.
     * */
    @FXML
    void addNewCustomer(ActionEvent event) throws IOException {
        setScene(event, Views.ADD_NEW_CUSTOMER_VIEW.getView());
    }

    /**
     * This method receives an action of going to a view window where displays all the reports.
     * @param event an event indicates a component-defined action occurred.
     * */
    @FXML
    void reportsClicked(ActionEvent event) {
        setScene(event, Views.REPORT_VIEW.getView());
    }

    /**
     * This method exits the program.
     * @param event an event indicates a component-defined action occurred.
     * */
    @FXML
    void exitBtnClicked(ActionEvent event) {
        exit(event, exitId);
    }

    /**
     * This method lists all the appointment based on a selected customer row.
     * @param actionEvent an event indicates a component-defined action occurred.
     */
    @FXML
    void listAptSelected(ActionEvent actionEvent) {
        selectedCust =  recordTable.getSelectionModel().getSelectedItem();
        if(selectedCust != null) {
            setScene(actionEvent, Views.APPOINTMENT_RECORD_VIEW.getView());
        }else{
            Validator.displayInvalidInput("Please select a row/customer to update");
        }
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
