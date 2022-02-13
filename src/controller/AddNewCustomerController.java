package controller;

import dao.CustomerDaoImpl;
import dao.FirstLevelDivisionDaoImpl;
import dao.UserDaoImpl;

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

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * This class provides a data control flow between the add new customer view and database tables
 *
 * @author Linmei M.
 */
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
    private Button cancelBtn;

    public static Customer customer = new Customer();
    private CustomerDaoImpl customerDao = new CustomerDaoImpl();
    private FirstLevelDivisionDaoImpl divisionDao = new FirstLevelDivisionDaoImpl();
    public static boolean isNewCust = false; // whether it is a new customer

    /**
     * Indicates the Canada radio button is selected and set that button to ture and re-select the rest of radio buttons, then
     * displays all the divisions in the Canada.
     *
     * @param event an event indicates a component-defined action occurred.
     */
    @FXML
    void canadaSelected(ActionEvent event) {
        CustomerRecordController.ctryId = CountryId.CANADA;
        setCountry(false, false, true);
        divisionList.setItems(divisionDao.getAllDivisions());
    }

    /**
     * Indicates the England radio button is selected and set that button to ture and re-select the rest of radio buttons, then
     * displays all the divisions in the England.
     *
     * @param event an event indicates a component-defined action occurred.
     */
    @FXML
    void englandSelected(ActionEvent event) {
        setCountry(false, true, false);
        CustomerRecordController.ctryId = CountryId.UK;
        divisionList.setItems(divisionDao.getAllDivisions());
    }

    /**
     * Indicates the US radio button is selected and set that button to ture and re-select the rest of radio buttons, then
     * displays all the divisions in the U.S.
     *
     * @param event an event indicates a component-defined action occurred.
     */
    @FXML
    void uSSelected(ActionEvent event) {
        CustomerRecordController.ctryId = CountryId.US;
        setCountry(true, false, false);
        divisionList.setItems(divisionDao.getAllDivisions());
    }

    /**
     * This method helps to select the radio that is selected by the user and the re-select the rest of radio buttons.
     *
     * @param us the US radio button
     * @param en the UK radio button
     * @param ca the CA raido button
     */
    private void setCountry(boolean us, boolean en, boolean ca) {
        USAId.setSelected(us);
        englandId.setSelected(en);
        canadaId.setSelected(ca);
    }

//    /**
//     * Add a new appointment button action is received here, then set the scene as adding a new appointment view
//     *
//     * @param event an event indicates a component-defined action occurred.
//     */
//    @FXML
//    void aptClicked(ActionEvent event) {
//        addCust(event, Views.ADD_NEW_APT_VIEW.getView());
//    }

    /**
     * The save button clicked action is received here, then call the addCust() method to add the customer information.
     *
     * @param event an event indicates a component-defined action occurred.
     */
    @FXML
    void saveCustClicked(ActionEvent event) {
        addCust(event, Views.ADD_NEW_CUSTOMER_VIEW.getView());
    }

    /**
     * Create a customer based on user input and save it to the database.
     **/
    private void addCust(ActionEvent event, String view) {
        String name = addCustNameField.getText();
        String address = addAddressField.getText();
        String phone = addPhoneField.getText();
        String zipcode = addZipCodeField.getText();
        long divisionId = divisionDao.findIdByDivisionName(divisionList.getValue());

        if (areValidInputs(name, address, phone, zipcode) && divisionList.getValue() != null
                && (USAId != null || canadaId != null || englandId != null)) {
            setCustomer(name, address, phone, zipcode, divisionId);
            isNewCust = true;
            customerDao.save(customer);
            Validator.displaySuccess("Add the customer");
            setScene(event, view);
        } else {
            Validator.displayInvalidInput("Contain invalid entry Or fields can not be empty.\n\n " +
                    "Example:\n Name: Lucy Wang\nAddress: 123 street name, city name \nPhone(US and " +
                    "Canada: XXX-XXX-XXXX, UK: XX-XXX-XXX-XXXX)\nPostCode(US and Canada are digits only and UK format example: AF19B)");
        }
    }

    private void setCustomer(String name, String address, String phone, String zipcode, long divisionId) {
        customer.setCustomer_name(name);
        customer.setAddress(address);
        customer.setPhone(phone);
        customer.setPostal_code(zipcode);
        customer.setCreated_by(UserDaoImpl.userName);
        customer.setCreate_date(Timestamp.valueOf(LocalDateTime.now()));
        customer.setLast_update(Timestamp.valueOf(LocalDateTime.now()));
        customer.setLast_updated_by(UserDaoImpl.userName);
        customer.setDivision_id(divisionId);
    }

    /**
     * This method validates the user input
     *
     * @param name     the user name can only be alphabets with space between
     * @param address  the user address can not contain special characters
     * @param phone    the user phone can not contain special characters
     * @param postCode the user post code can not contain special characters
     * @return
     */
    private boolean areValidInputs(String name, String address, String phone, String postCode) {
        return Validator.isValidName(name) && Validator.isValidAddress(address) && Validator.isValidPhoneNumber(phone) && Validator.isValidZipCode(postCode);
    }

    /**
     * This method sets the scene to the previous scene.
     *
     * @param event an event indicates a component-defined action occurred.
     **/
    @FXML
    void backToRecordPage(ActionEvent event) {
        setScene(event, Views.CUSTOMER_RECORD_VIEW.getView());
    }

    /**
     * This method exits the view.
     *
     * @param event an event indicates a component-defined action occurred.
     */
    @FXML
    void exitBtnClicked(ActionEvent event) {
        exit(event, cancelBtn);
    }

    /**
     * Initialize division list
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        divisionList.setPromptText("Division List");
        CustomerRecordController.ctryId = null;
        divisionList.setItems(divisionDao.getAllDivisions());
    }
}