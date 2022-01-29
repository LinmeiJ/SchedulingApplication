package controller;

import Dao.CustomerDaoImpl;
import Dao.FirstLevelDivisionDaoImpl;
import Dao.UserDaoImpl;
import Dao.Validator;
import entity.Country;
import entity.Customer;
import enums.CountryId;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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


    CountryId ctryID;
    private static Customer customer = CustomerRecordController.selectedCust;;
    private CustomerDaoImpl customerDao = new CustomerDaoImpl();
    private FirstLevelDivisionDaoImpl divisionDao = new FirstLevelDivisionDaoImpl();
    CountryId ctryID;

    @FXML
    void canadaSelected(ActionEvent event) {
        CustomerRecordController.ctryId = CountryId.CANADA;
        setCountry(false, false, true);
        division.setItems(divisionDao.getAllDivisions());
    }

    @FXML
    void englandSelected(ActionEvent event) {
        setCountry(false, true, false);
        CustomerRecordController.ctryId  = CountryId.UK;
        division.setItems(divisionDao.getAllDivisions());
    }

    @FXML
    void uSSelected(ActionEvent event) {
        CustomerRecordController.ctryId  = CountryId.US;
        setSelectedRadioBtn(true,false, false);
        division.setItems(divisionDao.getAllDivisions());
    }

    private void setSelectedRadioBtn(boolean us, boolean en, boolean ca) {
        USAId.setSelected(us);
        englandId.setSelected(en);
        canadaId.setSelected(ca);
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
        updateCustInfo(event);
        Validator.displaySuccess("Update");
    }

    private void updateCustInfo(ActionEvent event) throws SQLException {
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
    }

    @FXML
    void updateAptClicked(ActionEvent event) throws SQLException, IOException {
        updateCustInfo(event);
        setScene(event, UPDATE_APPOINTMENT_VIEW);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        custId.setText(String.valueOf(customer.getCustomer_id()));
        custNameField.setText(customer.getCustomer_name());
        phoneField.setText(customer.getPhone());
        addressField.setText(customer.getAddress());
        zipCodeField.setText(customer.getPostal_code());
        division.setValue(getCustomerDivision().get(0));
        getCountry();
    }

    private ObservableList<String> getCustomerDivision() {
        ObservableList<String> divisionList = FXCollections.observableArrayList();
        divisionList.add(customer.getFirstLevelDivision().getDivision());
        return divisionList;
    }

    private void getCountry(){
       Long ctyId = customer.getFirstLevelDivision().getCountry_id();
       ctryID = ctyId == 1 ? CountryId.US : ctyId == 2 ? CountryId.UK : CountryId.CANADA;
       switch (ctryID){
           case US:
               setCountry(true, false, false);
               break;
           case UK:
               setCountry(false, true, false);
               break;
           case CANADA:
               setCountry(false, false, true);
               break;
           default:
               // fix me : log something here
               break;
       }
    }

    private void setCountry(boolean us, boolean en, boolean ca) {
        USAId.setSelected(us);
        englandId.setSelected(en);
        canadaId.setSelected(ca);
    }
}
