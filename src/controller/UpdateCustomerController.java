package controller;

import Dao.CustomerDaoImpl;
import Dao.FirstLevelDivisionDaoImpl;
import Dao.UserDaoImpl;
import Dao.Validator;
import entity.Customer;
import enums.CountryId;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class UpdateCustomerController implements Initializable, CommonUseHelperIfc {
    @FXML
    private TextField custId;

    @FXML
    private TextField custNameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField zipCodeField;

    @FXML
    private RadioButton USAId;

    @FXML
    private RadioButton canadaId;

    @FXML
    private RadioButton englandId;

    @FXML
    private ComboBox<String> division;

    @FXML
    private Button saveUpdates;

    @FXML
    private Button backBtn;

    @FXML
    private Button cancelBtn;

    public static CountryId ctryID;
    private Customer customer;
    private CustomerDaoImpl customerDao = new CustomerDaoImpl();


    private FirstLevelDivisionDaoImpl divisionDao = new FirstLevelDivisionDaoImpl();


    @FXML
    void CanadaSelected(ActionEvent event) {
        ctryID = CountryId.CANADA;
        setSelectedRadioBtn(false, false, true);
        listDivisionByCountry();
    }

    @FXML
    void EnglandSelected(ActionEvent event) {
        setSelectedRadioBtn(false, true, false);
        ctryID = CountryId.UK;
        listDivisionByCountry();
    }

    private void setSelectedRadioBtn(boolean us, boolean en, boolean ca) {
        USAId.setSelected(us);
        englandId.setSelected(en);
        canadaId.setSelected(ca);
    }

    private void listDivisionByCountry() {
        division.getItems().clear();
        division.setItems(divisionDao.getAllDivisions());
    }


    @FXML
    void USSelected(ActionEvent event) {
        ctryID = CountryId.US;
        setSelectedRadioBtn(true,false, false);
        listDivisionByCountry();
    }

    @FXML
    void backToRecordPage(ActionEvent event) throws IOException {
        setScene(event, CUSTOMER_RECORD_VIEW);
    }

    @FXML
    void exitBtnClicked(ActionEvent event) {
        exit(event, cancelBtn);
    }

    @FXML
    void saveUpdateClicked(ActionEvent event) throws SQLException {
        customer = new Customer();
        customer.setCustomer_name(custNameField.getText());
        customer.setAddress(addressField.getText());
        customer.setPhone(phoneField.getText());
        customer.setPostal_code(zipCodeField.getText());
        customer.setCreated_by(UserDaoImpl.userName);
        customer.setCreate_date(Timestamp.valueOf(LocalDateTime.now()));
        customer.setLast_update(Timestamp.valueOf(LocalDateTime.now()));
        customer.setLast_updated_by(UserDaoImpl.userName);
        customer.setDivision_id(divisionDao.findIdByDivisionName(division.getValue()));
        customerDao.update(customer);

        Validator.displayAddSuccess();
    }

    @FXML
    void updateAptClicked(ActionEvent event) throws IOException {
        setScene(event, UPDATE_APPOINTMENT_VIEW);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customer = CustomerRecordController.selectedCust;
        custId.setText(String.valueOf(customer.getCustomer_id()));
        custNameField.setText(customer.getCustomer_name());
        phoneField.setText(customer.getPhone());
        addressField.setText(customer.getAddress());
        zipCodeField.setText(customer.getPostal_code());
//        division.setItems(customer.getFirstLevelDivision().getDivision());
    }
}
