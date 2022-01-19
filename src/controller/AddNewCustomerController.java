package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class AddNewCustomerController {
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

        }

        @FXML
        void divisionSelected(ActionEvent event) {

        }

        @FXML
        void saveCustClicked(ActionEvent event) {

        }


}
