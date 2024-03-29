package controller;

import dao.AppointmentDaoImpl;
import dao.ContactDaoImpl;
import dao.ReportDaoImpl;
import entity.Appointment;
import entity.Contact;
import entity.Country;
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
import java.sql.Timestamp;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * This class provides a data control flow for generating reports between the report UI and the database
 *
 * @author Linmei M.
 */
public class ReportController implements Initializable, CommonUseHelperIfc {
    /**
     * The exit button on the report screen
     */
    @FXML
    private Button exit;
    /**
     * The appointment description on the report two table view on the report screen
     */
    @FXML
    private TableColumn<Appointment, String> description;
    /**
     * The appointment start date time on the report two table view on the report screen
     */
    @FXML
    private TableColumn<Appointment, Timestamp> start;
    /**
     * The appointment title on the report two table view on the report screen
     */
    @FXML
    private TableColumn<Appointment, String> title;
    /**
     * The appointment type on the report two table view on the report screen
     */
    @FXML
    private TableColumn<Appointment, String> type;
    /**
     * The appointment ID on the report two table view on the report screen
     */
    @FXML
    private TableColumn<Appointment, Long> aptID;
    /**
     * A contact list for user to select to view a list of appointment that associated to the contact ID/name
     */
    @FXML
    private ComboBox<String> contactList;
    /**
     * The country name header on the report three table on the report screen
     */
    @FXML
    private TableColumn<Country, String> countryName;
    /**
     * The customer ID on the report two table view on the report screen
     */
    @FXML
    private TableColumn<Report, Long> custId;
    /**
     * The appointment end date time on the report two table view on the report screen
     */
    @FXML
    private TableColumn<Appointment, Timestamp> end;
    /**
     * The total appointment count by month or type table column on the report one table view on the report screen
     */
    @FXML
    private TableColumn<Report, String> typeMonthOption;
    /**
     * The total appointment count column on the report one table view on the report screen
     */
    @FXML
    private TableColumn<Report, Integer> typeMonthCount;
    /**
     * The month type comBox on the report screen
     */
    @FXML
    private ComboBox<String> monthTypeCombo;
    /**
     * The total count of customers for the report three on the report screen
     */
    @FXML
    private TableColumn<Country, Long> totalCountCustomers;
    /**
     * The report one table view on the report screen
     */
    @FXML
    private TableView<Report> reportOne;
    /**
     * The report two table view on the report screen
     */
    @FXML
    private TableView<Appointment> reportTwo;
    /**
     * The report three table view on the report screen
     */
    @FXML
    private TableView<Country> reportThree;

    /**
     * Initialize the isReportOneByMonth to true;
     */
    private static boolean isReportOneByMonth = true;
    /**
     * Initialize the isReportOneByType;
     */
    private static boolean isReportOneByType;
    /**
     * Initialize a contact name
     */
    private static String contactName;
    /**
     * Initialize a report dao object
     */
    private final ReportDaoImpl reportDao = new ReportDaoImpl();
    /**
     * Initialize a contact dao object
     */
    private final ContactDaoImpl contactDao = new ContactDaoImpl();
    /**
     * Initialize a appointment dao object
     */
    private final AppointmentDaoImpl appointmentDao = new AppointmentDaoImpl();

    /**
     * When a contact name is select in filter by a user, this method takes in the action
     * and displays the total appointment count by the selected contact name.
     *
     * @param event an event indicates a component-defined action occurred.
     */
    @FXML
    void contactNameIsSelected(ActionEvent event) {
        contactName = contactList.getValue();
        setScene(event, Views.REPORT_VIEW.getView());
    }

    /**
     * When any of the Type or Month filter is select by the user, this method takes in the action
     * and displays the total appointment count by type or month based on the input.
     *
     * @param event an event indicates a component-defined action occurred.
     */
    @FXML
    void monthTypeBtnSelected(ActionEvent event) {
        if (monthTypeCombo.getValue().equals("Type")) {
            isReportOneByMonth = false;
            isReportOneByType = true;
        } else {
            isReportOneByType = false;
            isReportOneByMonth = true;
        }
        setScene(event, Views.REPORT_VIEW.getView());
    }

    /**
     * This method sets the scene to the previous scene.
     *
     * @param event an event indicates a component-defined action occurred.
     **/
    @FXML
    void backIsSelected(ActionEvent event) {
        setScene(event, Views.CUSTOMER_RECORD_VIEW.getView());
    }

    /**
     * This method exits the view.
     *
     * @param event an event indicates a component-defined action occurred.
     */
    @FXML
    void existIsSelected(ActionEvent event) {
        exit(event, exit);
    }

    /**
     * Initialize the report view window. set month as default for report one and count the customer total by country name.
     * <p>
     * Report three : count customer total by country name.
     * </p>
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayCountsByTypeOrMonth();
        displayAppointmentsByContactName();
        displayCustCountsByCountryName();
    }

    /**
     * This is the report there where display the total customer count by country name
     */
    private void displayCustCountsByCountryName() {
        countryName.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        totalCountCustomers.setCellValueFactory(new PropertyValueFactory<>("count"));
        reportThree.setItems(reportDao.getCustCountByCountry());
    }

    /**
     * This is the report two where displaying a list of appointment by contact name
     *
     * <p>
     *     Lambda expression # 1
     * </p>
     */
    private void displayAppointmentsByContactName() {
        ObservableList<Contact> contacts = contactDao.findAll();
        // lambda expression: using steam to map a list of contact names and save it to a list for displaying them on the user UI
        ObservableList<String> contactNames = contacts.stream()
                .map(c -> c.getContact_name())
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        contactList.setItems(contactNames);

        if (contactName != null) {
            contactList.setValue(contactName);
            aptID.setCellValueFactory(new PropertyValueFactory<>("appointment_id"));
            title.setCellValueFactory(new PropertyValueFactory<>("title"));
            type.setCellValueFactory(new PropertyValueFactory<>("type"));
            description.setCellValueFactory(new PropertyValueFactory<>("description"));
            start.setCellValueFactory(new PropertyValueFactory<>("start"));
            end.setCellValueFactory(new PropertyValueFactory<>("end"));
            custId.setCellValueFactory(new PropertyValueFactory<>("customer_id"));

            long id = contactDao.getContactId(contactName);
            if (id == 0) {
                Validator.displayInfo("there is no appointments.");
            } else {
                reportTwo.setItems(appointmentDao.findAllByContactId(id));
            }
        }
    }

    /**
     * This is report one where display the total count either by type or by country
     */
    private void displayCountsByTypeOrMonth() {
        monthTypeCombo.setItems(FXCollections.observableArrayList("Month", "Type"));

        if (isReportOneByMonth) {
            monthTypeCombo.setValue("Month");
            typeMonthCount.setCellValueFactory(new PropertyValueFactory<>("count"));
            typeMonthOption.setCellValueFactory(new PropertyValueFactory<>("month"));
            reportOne.setItems(reportDao.getCountByMonth());
        } else if (isReportOneByType) {
            monthTypeCombo.setValue("Type");
            typeMonthCount.setCellValueFactory(new PropertyValueFactory<>("count"));
            typeMonthOption.setCellValueFactory(new PropertyValueFactory<>("Type"));
            reportOne.setItems(reportDao.getCountByType());
        }
    }
}
