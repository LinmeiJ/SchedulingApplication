package controller;

import Dao.FirstLevelDivisionDaoImpl;
import Dao.UserDaoImpl;
import converter.DateTimeConverter;
import dbConnection.JDBCConnection;
import entity.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.ResultSet;
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

    @FXML
    void CanadaSelected(ActionEvent event) {

    }

    @FXML
    void EnglandSelected(ActionEvent event) {

    }

    @FXML
    void USSelected(ActionEvent event) {
        USAId.setSelected(true);
        String s = divisionList.getSelectionModel().getSelectedItem().toString();
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
//            customer.setCustomer_name(addCustNameField.getText());
//            customer.setAddress(addAddressField.getText());
//            customer.setPhone(addPhoneField.getText());
//            customer.setPostal_code(addZipCodeField.getText());
//            customer.setCreated_by(UserDaoImpl.userName);
//            customer.setCreated_date(DateTimeConverter.convertedTimeTOUTC());

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> divisions= FXCollections.observableArrayList();
        USAId.setSelected(true);
        divisionList.getItems().clear();
            ResultSet rs = new FirstLevelDivisionDaoImpl().findAll();
            try {
                while (rs.next()) {
                    divisions.add(rs.getString(1));
                }
            }catch(Exception e){}
            divisionList.setItems(divisions);

    }
}
