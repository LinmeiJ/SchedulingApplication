package controller;

import dao.CustomerDaoImpl;
import dao.FirstLevelDivisionDaoImpl;
import dao.UserDaoImpl;
import entity.Customer;
import enums.CountryId;
import enums.Views;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * This class provides a data control flow between the update a customer UI and database tables
 *
 * @author Linmei M.
 */
public class UpdateCustomerController implements Initializable, CommonUseHelperIfc {
    /**
     * The customer ID field on the customer update screen
     */
    @FXML
    private TextField custId;
    /**
     * The customer name field on the customer update screen
     */
    @FXML
    private TextField custNameField;
    /**
     * The phone number field on the customer update screen
     */
    @FXML
    private TextField phoneField;
    /**
     * The address of the customer on the customer update screen
     */
    @FXML
    private TextField addressField;
    /**
     * The zip code filed on the customer update screen
     */
    @FXML
    private TextField zipCodeField;
    /**
     * The U.S. radio button on the customer update screen
     */
    @FXML
    private RadioButton USAId;
    /**
     * The C.A button on the customer update screen
     */
    @FXML
    private RadioButton canadaId;
    /**
     * The U.K. radio button on the customer update screen
     */
    @FXML
    private RadioButton englandId;
    /**
     * The division comboBox on the customer update screen
     */
    @FXML
    private ComboBox<String> division;
    /**
     * The exit button on the customer update screen
     */
    @FXML
    private Button cancelBtn;

    /**
     * Initialize customerDao object
     */
    private final CustomerDaoImpl customerDao = new CustomerDaoImpl();
    /**
     * Initialize first level division dao object
     */
    private final FirstLevelDivisionDaoImpl divisionDao = new FirstLevelDivisionDaoImpl();
    /**
     * get and save the selected customer row from customer record controller
     */
    private final Customer selectedCust = CustomerRecordController.selectedCust;
    /**
     * Initialize a customer object
     */
    private final Customer customer = new Customer();
    /**
     * create a country ID variable
     */
    public CountryId ctryID;

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
        division.setItems(divisionDao.getAllDivisions());
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
        division.setItems(divisionDao.getAllDivisions());
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
        setSelectedRadioBtn(true, false, false);
        division.setItems(divisionDao.getAllDivisions());
    }

    /**
     * This method helps to select the radio that is selected by the user and the re-select the rest of radio buttons.
     *
     * @param us the US radio button
     * @param en the UK radio button
     * @param ca the CA raido button
     */
    private void setSelectedRadioBtn(boolean us, boolean en, boolean ca) {
        USAId.setSelected(us);
        englandId.setSelected(en);
        canadaId.setSelected(ca);
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
     * The save button clicked action is received here, then call the updateCustInfo method to update the customer information.
     *
     * @param event an event indicates a component-defined action occurred.
     */
    @FXML
    void saveUpdateClicked(ActionEvent event) {
        updateCustInfo(event);
    }

    private void updateCustInfo(ActionEvent event) {
        if (detectAnyChange(event)) {
            if (areValidInputs(custNameField.getText(), addressField.getText(), phoneField.getText(), zipCodeField.getText())) {
                getCustomerUpdatedInfo(custNameField.getText(), addressField.getText(), phoneField.getText(), zipCodeField.getText());
                customerDao.update(customer);
                Validator.displaySuccess("Updated");
                setScene(event, Views.CUSTOMER_RECORD_VIEW.getView());
            } else {
                Validator.displayInvalidInput("Invalid entry, try gain.");
            }
        } else {
            Validator.displayInfo("No change is found.");
        }
    }

    /**
     * This method detects whether there is a change occurs
     *
     * @param event an event indicates a component-defined action occurred.
     * @return boolean if there is a change find, return true, otherwise returns false.
     */
    private boolean detectAnyChange(ActionEvent event) {
        String name = custNameField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();
        String postCode = zipCodeField.getText();
        String div = division.getValue();
        return !name.equals(customer.getCustomer_name()) || !address.equals(selectedCust.getAddress())
                || !phone.equals(selectedCust.getPhone()) || !postCode.equals(selectedCust.getPostal_code())
                || !div.equals(getCustomerDivision().get(0));
    }

    /**
     * Update a customer object based on the input user provides
     *
     * @param name     the customer name
     * @param address  the customer address
     * @param phone    the customer phone
     * @param postCode the customer entered postcode
     */
    private void getCustomerUpdatedInfo(String name, String address, String phone, String postCode) {
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

    /**
     * This method validates the user input
     *
     * @param name     the user name can only be alphabets with space between
     * @param address  the user address can not contain special characters
     * @param phone    the user phone can not contain special characters
     * @param postCode the user post code can not contain special characters
     * @return true if all inputs are valid, otherwise returns a false
     */
    private boolean areValidInputs(String name, String address, String phone, String postCode) {
        return Validator.isValidName(name) && Validator.isValidAddress(address) && Validator.isValidPhoneNumber(phone) && Validator.isValidZipCode(postCode);
    }

//    /**
//     * This method give an option for user to also update the same customers appointment.
//     *
//     * @param event an event indicates a component-defined action occurred.
//     */
//    @FXML
//    void updateAptClicked(ActionEvent event) {
//        if (!detectAnyChange(event) || isSaved) {
//            setScene(event, Views.APPOINTMENT_RECORD_VIEW.getView());
//        } else {
//            Validator.displayUnsavedInfo("Please save customer's information before add/update the appointments");
//        }
//    }

    /**
     * Initialize the customer previous info.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        custId.setText(String.valueOf(selectedCust.getCustomer_id()));
        custNameField.setText(selectedCust.getCustomer_name());
        phoneField.setText(selectedCust.getPhone());
        addressField.setText(selectedCust.getAddress());
        zipCodeField.setText(selectedCust.getPostal_code());
        division.setValue(getCustomerDivision().get(0));
        division.setItems(divisionDao.getAllDivisions());
        getCountry();
    }

    /**
     * This method gets the division list based on the previous saved country ID
     * @return a list of divisions
     */
    private ObservableList<String> getCustomerDivision() {
        ObservableList<String> divisionList = FXCollections.observableArrayList();
        divisionList.add(selectedCust.getFirstLevelDivision().getDivision());
        return divisionList;
    }

    /**
     * This method gets the country that stored in the database based on the selected customer row.
     * then sets them as the default choice.
     */
    private void getCountry() {
        long ctyId = selectedCust.getFirstLevelDivision().getCountry_id();
        ctryID = ctyId == 1 ? CountryId.US : ctyId == 2 ? CountryId.UK : CountryId.CANADA;
        switch (ctryID) {
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
                System.out.println("something wrong, country is not find");
                break;
        }
    }

    /**
     * Sets a default country button
     *
     * @param us the US radio button
     * @param en the UK radio button
     * @param ca the CA radio button
     */
    private void setCountry(boolean us, boolean en, boolean ca) {
        USAId.setSelected(us);
        englandId.setSelected(en);
        canadaId.setSelected(ca);
    }
}
