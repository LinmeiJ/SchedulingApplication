package controller;

import Dao.FirstLevelDivisionDaoImpl;
import enums.CountryId;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateCustomerController implements Initializable, CommonUseHelperIfc {
    @FXML
    private TextField updateCustNameField;

    @FXML
    private TextField updatePhoneField;

    @FXML
    private TextField updateAddressField;

    @FXML
    private TextField updateZipCodeField;

    @FXML
    private RadioButton USAId;

    @FXML
    private RadioButton canadaId;

    @FXML
    private RadioButton englandId;

    @FXML
    private ComboBox<String> updateDivision;

    @FXML
    private Button saveUpdates;

    @FXML
    private Button backBtn;

    @FXML
    private Button cancelBtn;

    public static CountryId ctryID;

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
        updateDivision.getItems().clear();
        updateDivision.setItems(divisionDao.getAllDivisions());
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
    void saveUpdateClicked(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
