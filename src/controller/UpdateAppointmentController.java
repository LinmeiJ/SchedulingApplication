package controller;
import Dao.ContactDaoImpl;
import dbConnection.JDBCConnection;
import entity.Appointment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class UpdateAppointmentController extends JDBCConnection implements Initializable, CommonUseHelperIfc {
    @FXML
    private TextField addressField;

    @FXML
    private TextField addressField1;

    @FXML
    private Label aptContactName;

    @FXML
    private Label aptDescription;

    @FXML
    private Label aptID;

    @FXML
    private Label aptLocation;

    @FXML
    private Label aptTitle;

    @FXML
    private Label aptType;

    @FXML
    private Button backBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private Label custID;

    @FXML
    private TextField custId;

    @FXML
    private TextField custNameField;

    @FXML
    private DatePicker endDate;

    @FXML
    private ComboBox<String> endHr;

    @FXML
    private ComboBox<String> endMin;

    @FXML
    private TextField phoneField;

    @FXML
    private Button saveUpdates;

    @FXML
    private DatePicker startDate;

    @FXML
    private ComboBox<String> startHr;

    @FXML
    private ComboBox<String> startMin;

    @FXML
    private Label userId;


    Appointment appointment = AppointmentRecordController.selecteApt;
    ContactDaoImpl contactDao = new ContactDaoImpl();

    @FXML
    void backToRecordPage(ActionEvent event) throws IOException {
        setScene(event,APPOINTMENT_RECORD_VIEW);
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
        aptID.setText(String.valueOf(appointment.getAppointment_id()));
        aptTitle.setText(appointment.getTitle());
        aptDescription.setText(appointment.getDescription());
        aptLocation.setText(appointment.getLocation());
        aptType.setText(appointment.getType());
        startDate.setValue(LocalDate.now()); //fix me.. pass the DB saved start date
        endDate.setValue(LocalDate.now()); //fix me.. pass the DB saved start date
        try {
            aptContactName.setText(contactDao.findNameByID(appointment.getContact_id()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
