package controller;

import Dao.CustomerDaoImpl;
import Dao.FirstLevelDivisionDaoImpl;
import Dao.UserDaoImpl;

import dbConnection.JDBCConnection;
import entity.Customer;
import enums.CountryId;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AddNewCustomerController extends JDBCConnection implements Initializable, Exit {
    @FXML
    private TextField addPhoneField;

    @FXML
    private TextField addAddressField;

    @FXML
    private TextField addZipCodeField;

    @FXML
    private TextField addCustNameField;

    @FXML
    private Button saveCustBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private RadioButton USAId;

    @FXML
    private RadioButton canadaId;

    @FXML
    private RadioButton englandId;

    @FXML
    private ComboBox<String> divisionList;

    Customer customer;
    CustomerDaoImpl customerDao = new CustomerDaoImpl();
    public static CountryId ctryID;

    FirstLevelDivisionDaoImpl divisionDao = new FirstLevelDivisionDaoImpl();

    @FXML
    void CanadaSelected(ActionEvent event) {
        ctryID = CountryId.CANADA;
        canadaId.setSelected(true);
    }

    @FXML
    void EnglandSelected(ActionEvent event) {
        ctryID = CountryId.UK;
        englandId.setSelected(true);
    }

    @FXML
    void USSelected(ActionEvent event) {
        ctryID = CountryId.US;
        USAId.setSelected(true);
//        String s = divisionList.getSelectionModel().getSelectedItem().toString();
    }

    @FXML
    void cancelBtnClicked(ActionEvent event) {
        exit(event, cancelBtn);
    }

    @FXML
    void divisionSelected(ActionEvent event) {

    }

    @FXML
    void saveCustClicked(ActionEvent event) throws SQLException {
        customer = new Customer();
        customer.setCustomer_name(addCustNameField.getText());
        customer.setAddress(addAddressField.getText());
        customer.setPhone(addPhoneField.getText());
        customer.setPostal_code(addZipCodeField.getText());
        customer.setCreated_by(UserDaoImpl.userName);
        customer.setCreate_date(Timestamp.valueOf(LocalDateTime.now()));
        customer.setLast_update(Timestamp.valueOf(LocalDateTime.now()));
        customer.setLast_updated_by(UserDaoImpl.userName);
        customer.setDivision_id(divisionDao.findIdByDivisionName(divisionList.getValue()));
        customerDao.save(customer);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        USAId.setSelected(true);
        ctryID = CountryId.US;
        divisionList.setItems(divisionDao.getAllDivisions());
    }
}
