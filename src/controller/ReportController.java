package controller;

import dao.AppointmentDaoImpl;
import dao.ContactDaoImpl;
import dao.ReportDaoImpl;
import entity.Appointment;
import entity.Customer;
import entity.Report;
import enums.Views;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;

public class ReportController implements Initializable, CommonUseHelperIfc{
    ReportDaoImpl reportDao = new ReportDaoImpl();
    ContactDaoImpl contactDao = new ContactDaoImpl();
    AppointmentDaoImpl appointmentDao = new AppointmentDaoImpl();

    @FXML
    private Label reportType;

    @FXML
    private Button exit;

    @FXML
    private Button back;

    @FXML
    private ListView<Report> aptCountList;

    @FXML
    private ListView<Appointment> ScheduleByContact;

    @FXML
    private ListView<Customer> CountCountByCountry;

    @FXML
    private TableColumn<Appointment, String> Description;

    @FXML
    private TableColumn<Appointment, Timestamp> Start;

    @FXML
    private TableColumn<Appointment, String> Title;

    @FXML
    private TableColumn<Appointment, String> Type;

    @FXML
    private TableColumn<Appointment, Long> aptID;

    @FXML
    private ComboBox<String> contactList;

    @FXML
    private TableColumn<Appointment, String> countOption;

    @FXML
    private TableColumn<Appointment, String> countryName;

    @FXML
    private TableColumn<Appointment, Long> custId;

    @FXML
    private TableColumn<Appointment, Timestamp> end;

    @FXML
    private ComboBox<String> monthTypeCombo;

    @FXML
    private TableColumn<Customer, Long> totalCountCustomers;

    @FXML
    private TableColumn<Appointment, Long> typeMonthOption;

    private boolean isReportOneMonth;
    private boolean isReportOneType;
    private boolean isReportTwoContact;

    @FXML
    void contactNameIsSelected(ActionEvent event) {

    }

    @FXML
    void monthTypeBtnSelected(ActionEvent event) {

    }

    @FXML
    void backIsSelected(ActionEvent event) {
        setScene(event, Views.CUSTOMER_RECORD_VIEW.getView());
    }

    @FXML
    void existIsSelected(ActionEvent event) {
        exit(event, exit);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
