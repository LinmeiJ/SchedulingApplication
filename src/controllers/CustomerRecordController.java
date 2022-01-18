package controllers;

import Dao.CustomerDaoImpl;
import Dao.Validator;
import entity.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.*;

public class CustomerRecordController implements Initializable {

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
    private TableColumn<Customer, String> delete;

    @FXML
    private TableColumn<Customer, String> update;

    @FXML
    private ComboBox<String> divisionList;

    @FXML
    private RadioButton USBtn;

    @FXML
    private RadioButton CABtn;

    @FXML
    private RadioButton EnglandBtn;

    CustomerDaoImpl customerDao = new CustomerDaoImpl();

    ObservableList<Customer> customers = FXCollections.observableArrayList();
    Logger logger = Logger.getLogger(this.getClass().getName());
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        custID.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        custName.setCellValueFactory(new PropertyValueFactory<>("customer_name"));
        custAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        custZipCode.setCellValueFactory(new PropertyValueFactory<>("postal_code"));
        custPhoneNum.setCellValueFactory(new PropertyValueFactory<>("phone"));
        try{
            customers.addAll(customerDao.getAllCustomers());
        } catch (Exception e) {
           logger.log(Level.WARNING, "initialize() throws an exception", this.getClass().getName());
        }
        recordTable.setItems(customers);
    }

    @FXML
    void exitBtnClicked(ActionEvent event) {
        Stage stage = (Stage) exitId.getScene().getWindow();
        Validator.displayExitConfirmation();
        if (Validator.confirmResult.isPresent() && Validator.confirmResult.get() == ButtonType.OK) {
            stage.close();
        }
    }
}
