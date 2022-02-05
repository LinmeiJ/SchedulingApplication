package controller;

import Dao.CustomerDaoImpl;
import Dao.FirstLevelDivisionDaoImpl;
import Dao.UserDaoImpl;
import Dao.Validator;
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
    private boolean isSaved = false;
    private Customer selectedCust = CustomerRecordController.selectedCust;;
    private CustomerDaoImpl customerDao = new CustomerDaoImpl();
    private FirstLevelDivisionDaoImpl divisionDao = new FirstLevelDivisionDaoImpl();
    private Customer customer = new Customer();
    private  boolean isAnyChange = false;

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
    }

    private void updateCustInfo(ActionEvent event) {
        try {
            if (detectAnyChange(event)) {
                if (areValidInputs(custNameField.getText(), addressField.getText(), phoneField.getText(), zipCodeField.getText())) {
                    getCustomerUpdatedInfo(custNameField.getText(), addressField.getText(), phoneField.getText(), zipCodeField.getText());
                    customerDao.update(customer);
                    Validator.displaySuccess("Update");
                    isSaved = true;
                } else {
                    Validator.displayInvalidInput("Contain invalid entry! E.g: name only contains alphabets and a space, phone number and zipcode has to be only digits.");
                    setScene(event, UPDATE_CUSTOMER_VIEW);
                }
            }
            else {
                Validator.displayInfo("No change is found.");
            }
        } catch(SQLException | RuntimeException e){
            e.printStackTrace();
        }
    }

    private boolean detectAnyChange(ActionEvent event) {
            String name = custNameField.getText();
            String address = addressField.getText();
            String phone = phoneField.getText();
            String postCode = zipCodeField.getText();
            String div = division.getValue();
        return name.equals(selectedCust.getCustomer_name()) && address.equals(selectedCust.getAddress())
        && phone.equals(selectedCust.getPhone()) && postCode.equals(selectedCust.getPostal_code())
        && div.equals(getCustomerDivision().get(0)) ? false : true;
    }

    private void getCustomerUpdatedInfo(String name, String address, String phone, String postCode) throws SQLException {
        customer.setCustomer_name(name);
        customer.setAddress(address);
        customer.setPhone(phone);
        customer.setPostal_code(postCode);
        customer.setCreated_by(UserDaoImpl.userName);
        customer.setCreate_date(Timestamp.valueOf(LocalDateTime.now()));
        customer.setLast_update(Timestamp.valueOf(LocalDateTime.now()));
        customer.setLast_updated_by(UserDaoImpl.userName);
        customer.setDivision_id(divisionDao.findIdByDivisionName(division.getValue()));
    }

    private boolean areValidInputs(String name, String address, String phone, String postCode) throws SQLException {
        return Validator.isValidName(name) && Validator.isValidAddress(address) && Validator.isValidPhoneNumber(phone) && Validator.isValidZipCode(postCode);
    }

    @FXML
    void updateAptClicked(ActionEvent event){
        if(!detectAnyChange(event) || isSaved) {
            setScene(event, APPOINTMENT_RECORD_VIEW);
        }else{
            Validator.displayUnsavedInfo("Please save customer's information before add/update the appointments");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        custId.setText(String.valueOf(selectedCust.getCustomer_id()));
        custNameField.setText(selectedCust.getCustomer_name());
        phoneField.setText(selectedCust.getPhone());
        addressField.setText(selectedCust.getAddress());
        zipCodeField.setText(selectedCust.getPostal_code());
        division.setValue(getCustomerDivision().get(0));
        getCountry();
    }

    private ObservableList<String> getCustomerDivision() {
        ObservableList<String> divisionList = FXCollections.observableArrayList();
        divisionList.add(selectedCust.getFirstLevelDivision().getDivision());
        return divisionList;
    }

    private void getCountry(){
       Long ctyId = selectedCust.getFirstLevelDivision().getCountry_id();
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
