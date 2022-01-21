package controller;

import Dao.CustomerDaoImpl;
import entity.Customer;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.*;

public class CustomerRecordController implements Initializable, Exit{

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

    @FXML
    private TableColumn<Customer, String> delete;

    @FXML
    private TableColumn<Customer, String> update;

    private static final String ADD_NEW_CUSTOMER_VIEW_PATH = "../views/addNewCustomerView.fxml";

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
        custDivision.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFirstLevelDivision().getDivision()));
        try{
            customers.addAll(customerDao.findAll());
        } catch (Exception e) {
           logger.log(Level.WARNING, "initialize() throws an exception", this.getClass().getName());
        }
        recordTable.setItems(customers);

    }

    @FXML
    void exitBtnClicked(ActionEvent event) {
        exit(event, exitId);
    }

    @FXML
    void addNewCustomer(ActionEvent event) throws IOException {
        setScene(event, ADD_NEW_CUSTOMER_VIEW_PATH);
    }

    /**
     * set a scene based on this particular action and fxml path passed over to the params.
     *
     * @param event an event indicates a component-defined action occurred
     * @param s     the file path where the fxml is located at
     * @throws IOException it happens when the fxml file is not found
     */
    private void setScene(ActionEvent event, String s) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(s));
        var scene = new Scene(parent);
        var stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

}
