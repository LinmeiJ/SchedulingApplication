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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.SQLException;
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
    void backIsSelected(ActionEvent event) {
        setScene(event, Views.CUSTOMER_RECORD_VIEW.getView());
    }

    @FXML
    void existIsSelected(ActionEvent event) {
        exit(event, exit);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<Report> reportOne = reportDao.getCountByType();
            aptCountList.setItems(reportOne);
            List<Integer> allContactId = contactDao.findAllContactId();
//            ObservableList<Appointment> reportTwo;
////            for(Integer i : allContactId){
//            for(int i = 0; i < allContactId.size(); i++){
//                reportTwo = appointmentDao.findAllByContactId(allContactId.get(i));
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}
