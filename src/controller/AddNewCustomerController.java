package controller;

import Dao.CustomerDaoImpl;
import Dao.FirstLevelDivisionDaoImpl;
import Dao.UserDaoImpl;

import Dao.Validator;
import dbConnection.JDBCConnection;
import entity.Customer;
import enums.CountryId;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AddNewCustomerController extends JDBCConnection implements Initializable, Exit {
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
    private Customer customer;
    private CustomerDaoImpl customerDao = new CustomerDaoImpl();
    public static CountryId ctryID;
    private FirstLevelDivisionDaoImpl divisionDao = new FirstLevelDivisionDaoImpl();
    private static final String CUSTOMER_RECORD_VIEW = "../views/customerRecordView.fxml";


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

    @FXML
    void USSelected(ActionEvent event) {
        ctryID = CountryId.US;
        setSelectedRadioBtn(true,false, false);
        listDivisionByCountry();
    }

    private void listDivisionByCountry() {
        divisionList.getItems().clear();
        divisionList.setItems(divisionDao.getAllDivisions());
    }


    @FXML
    void aptClicked(ActionEvent event) {

    }

    @FXML
    void cancelBtnClicked(ActionEvent event) {
        exit(event, cancelBtn);
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

        Validator.displayAddSuccess();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        divisionList.setPromptText("Division List");
        divisionList.setItems(divisionDao.getAllDivisions());
    }

    @FXML
    void backToRecordPage(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(CUSTOMER_RECORD_VIEW));
        var scene = new Scene(parent);
        var stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
}
