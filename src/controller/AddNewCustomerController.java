package controller;

import Dao.UserDaoImpl;
import converter.DateTimeConverter;
import entity.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddNewCustomerController implements Initializable, Exit {
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
    private RadioButton USId;

    @FXML
    private RadioButton canadaId;

    @FXML
    private RadioButton englandId;

    @FXML
    private ComboBox<?> divisionList;

    Customer customer;

    @FXML
    void CanadaSelected(ActionEvent event) {

    }

    @FXML
    void EnglandSelected(ActionEvent event) {

    }

    @FXML
    void USSelected(ActionEvent event) {

    }

    @FXML
    void cancelBtnClicked(ActionEvent event) {
        exit(event, cancelBtn);
    }

    @FXML
    void divisionSelected(ActionEvent event) {

    }

    @FXML
    void saveCustClicked(ActionEvent event) {
            customer.setCustomer_name(addCustNameField.getText());
            customer.setAddress(addAddressField.getText());
            customer.setPhone(addPhoneField.getText());
            customer.setPostal_code(addZipCodeField.getText());
            customer.setCreated_by(UserDaoImpl.userName);
            customer.setCreated_date(DateTimeConverter.convertedTimeTOUTC());

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
