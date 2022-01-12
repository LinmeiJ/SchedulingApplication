package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class CustomerRecordController {

    @FXML
    private Button exitId;

    @FXML
    private AnchorPane partPane;

    @FXML
    private TableView<?> partsTable;

    @FXML
    private TableColumn<?, ?> custID;

    @FXML
    private TableColumn<?, ?> custName;

    @FXML
    private TableColumn<?, ?> custAddress;

    @FXML
    private TableColumn<?, ?> custZipCode;

    @FXML
    private TableColumn<?, ?> custPhoneNum;

    @FXML
    private TableColumn<?, ?> custPhoneNum1;

    @FXML
    private TableColumn<?, ?> custPhoneNum2;

    @FXML
    private ComboBox<?> divisionList;

    @FXML
    private RadioButton USBtn;

    @FXML
    private RadioButton CABtn;

    @FXML
    private RadioButton EnglandBtn;

    @FXML
    void exitBtnClicked(ActionEvent event) {

    }
}
