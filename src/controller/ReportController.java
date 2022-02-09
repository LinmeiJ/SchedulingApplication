package controller;

import dao.AppointmentDaoImpl;
import dao.ContactDaoImpl;
import dao.ReportDaoImpl;
import dao.Validator;
import entity.Appointment;
import entity.Country;
import entity.Customer;
import entity.Report;
import enums.Views;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class ReportController implements Initializable, CommonUseHelperIfc{

    @FXML
    private Button exit;

    @FXML
    private Button back;

    @FXML
    private TableColumn<Appointment, String> description;

    @FXML
    private TableColumn<Appointment, Timestamp> start;

    @FXML
    private TableColumn<Appointment, String> title;

    @FXML
    private TableColumn<Appointment, String> type;

    @FXML
    private TableColumn<Appointment, Long> aptID;

    @FXML
    private ComboBox<String> contactList;

    @FXML
    private TableColumn<Report, String> countOption;

    @FXML
    private TableColumn<Country, String> countryName;

    @FXML
    private TableColumn<Report, Long> custId;

    @FXML
    private TableColumn<Appointment, Timestamp> end;

    @FXML
    private TableColumn<Report, String> typeMonthOption;

    @FXML
    private TableColumn<Report, Integer> typeMonthCount;

    @FXML
    private ComboBox<String> monthTypeCombo;

    @FXML
    private TableColumn<Country, Long> totalCountCustomers;

    @FXML
    private TableView<Report> reportOne;
    @FXML
    private TableView<Appointment> reportTwo;
    @FXML
    private TableView<Country> reportThree;

    private static boolean isReportOneByMonth = true;
    private static boolean isReportOneByType;
    private static boolean isReportTwoContact;
    private static String contactName;
    private ReportDaoImpl reportDao = new ReportDaoImpl();
    ContactDaoImpl contactDao = new ContactDaoImpl();
    AppointmentDaoImpl appointmentDao = new AppointmentDaoImpl();


    @FXML
    void contactNameIsSelected(ActionEvent event) throws SQLException {
        contactName =contactList.getValue();
        isReportTwoContact = true;
        setScene(event, Views.REPORT_VIEW.getView());
    }

    @FXML
    void monthTypeBtnSelected(ActionEvent event) {
        if(monthTypeCombo.getValue().equals("Type")){
            isReportOneByMonth = false;
            isReportOneByType = true;
        }else{
            isReportOneByType = false;
            isReportOneByMonth = true;
        }
        setScene(event, Views.REPORT_VIEW.getView());
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
        monthTypeCombo.setItems(FXCollections.observableArrayList("Month","Type"));
        try {
            if(isReportOneByMonth){
                monthTypeCombo.setValue("Month");
                typeMonthCount .setCellValueFactory(new PropertyValueFactory<>("count"));
                typeMonthOption.setCellValueFactory(new PropertyValueFactory<>("month"));
                reportOne.setItems(reportDao.getCountByMonth());
            }
            else if(isReportOneByType){
                monthTypeCombo.setValue("Type");
                typeMonthCount .setCellValueFactory(new PropertyValueFactory<>("count"));
                typeMonthOption.setCellValueFactory(new PropertyValueFactory<>("Type"));
                reportOne.setItems(reportDao.getCountByType());
            }

            ObservableList<String> contacts = contactDao.findAll();
            contactList.setItems(contacts);

            if(contactName != null) {
                aptID.setCellValueFactory(new PropertyValueFactory<>("appointment_id"));
                title.setCellValueFactory(new PropertyValueFactory<>("title"));
                type.setCellValueFactory(new PropertyValueFactory<>("type"));
                description.setCellValueFactory(new PropertyValueFactory<>("description"));
                start.setCellValueFactory(new PropertyValueFactory<>("start"));
                end.setCellValueFactory(new PropertyValueFactory<>("end"));
                custId.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
                contactList.setValue(contactName);
                long id = contactDao.getContactId(contactName);
                if(id == 0){
                    Validator.displayInfo("there is no appointments.");
                }else {
                    reportTwo.setItems(appointmentDao.findAllByContactId(id));
                }
            }

            countryName.setCellValueFactory(new PropertyValueFactory<>("country"));
            totalCountCustomers.setCellValueFactory(new PropertyValueFactory<>("count"));
            reportThree.setItems(reportDao.getCustCountByCountry());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
