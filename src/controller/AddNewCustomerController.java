package controller;

import dao.CustomerDaoImpl;
import dao.FirstLevelDivisionDaoImpl;
import dao.UserDaoImpl;

import dao.Validator;
import dbConnection.JDBCConnection;
import entity.Customer;
import enums.CountryId;

import enums.Views;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AddNewCustomerController extends JDBCConnection implements Initializable, CommonUseHelperIfc {
    @FXML
    private TextField addCustNameField;

    @FXML
    private TextField addPhoneField;

    @FXML
    private TextField addAddressField;

    @FXML
    private TextField addZipCodeField;

    @FXML
    private RadioButton USAId;

    @FXML
    private RadioButton canadaId;

    @FXML
    private RadioButton englandId;

    @FXML
    private ComboBox<String> divisionList;

    @FXML
    private Button backBtn;

    @FXML
    private Button saveCustBtn;

    @FXML
    private Button aptBtn;

    @FXML
    private Button cancelBtn;

    public static Customer customer = new Customer();
    private CustomerDaoImpl customerDao = new CustomerDaoImpl();
    private FirstLevelDivisionDaoImpl divisionDao = new FirstLevelDivisionDaoImpl();
    public static boolean isNewCust = false;

    @FXML
    void canadaSelected(ActionEvent event) {
        CustomerRecordController.ctryId  = CountryId.CANADA;
        setCountry(false, false, true);
        divisionList.setItems(divisionDao.getAllDivisions());
    }

    @FXML
    void englandSelected(ActionEvent event) {
        setCountry(false, true, false);
        CustomerRecordController.ctryId  = CountryId.UK;
        divisionList.setItems(divisionDao.getAllDivisions());
    }

    @FXML
    void uSSelected(ActionEvent event) {
        CustomerRecordController.ctryId  = CountryId.US;
        setCountry(true,false, false);
        divisionList.setItems(divisionDao.getAllDivisions());
    }

    private void setCountry(boolean us, boolean en, boolean ca) {
        USAId.setSelected(us);
        englandId.setSelected(en);
        canadaId.setSelected(ca);
    }

    @FXML
    void aptClicked(ActionEvent event) throws IOException, SQLException {
        addCust(event);
        setScene(event, Views.NEW_APT_VIEW.getView());
    }

    @FXML
    void saveCustClicked(ActionEvent event) throws SQLException {
        addCust(event);
        Validator.displaySuccess("Add");
    }

    private void addCust(ActionEvent event) throws SQLException {
        customer.setCustomer_name(addCustNameField.getText());
        customer.setAddress(addAddressField.getText());
        customer.setPhone(addPhoneField.getText());
        customer.setPostal_code(addZipCodeField.getText());
        customer.setCreated_by(UserDaoImpl.userName);
        customer.setCreate_date(Timestamp.valueOf(LocalDateTime.now()));
        customer.setLast_update(Timestamp.valueOf(LocalDateTime.now()));
        customer.setLast_updated_by(UserDaoImpl.userName);
        customer.setDivision_id(divisionDao.findIdByDivisionName(divisionList.getValue()));

        isNewCust = true;
        customerDao.save(customer);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        divisionList.setPromptText("Division List");
        divisionList.setItems(divisionDao.getAllDivisions());
    }

    @FXML
    void backToRecordPage(ActionEvent event) throws IOException {
        setScene(event, Views.CUSTOMER_RECORD_VIEW.getView());
    }

    @FXML
    void exitBtnClicked(ActionEvent event) {
        exit(event, cancelBtn);
    }
}
