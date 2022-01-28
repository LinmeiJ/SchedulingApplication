package Dao;

import dbConnection.JDBCConnection;
import entity.Appointment;
import entity.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class AppointmentDaoImpl extends JDBCConnection implements ServiceIfc<Appointment>{
    private Appointment appointment;
    private ObservableList<Appointment> allAppointment = FXCollections.observableArrayList();

    @Override
    public ObservableList<Appointment> findAll() throws SQLException {
        return null;
    }

    @Override
    public Appointment findById(long id) {
        return null;
    }

    public ObservableList<Appointment> findAllByCustId(long customerId) throws SQLException {
        ResultSet rs = findRawDataFromDB("SELECT Appointment_ID, Title, a.Description, Location, a.Type, a.Start, a.End, User_ID, Contact_ID FROM appointments as a WHERE Customer_ID = " + customerId);
        while(rs.next()){
            long aptId = rs.getLong("appointment_id");
            String title = rs.getString("title");
            String description =  rs.getString("description");
            String location =  rs.getString("location");
            String type =  rs.getString("type");
            Timestamp startDateTime = rs.getTimestamp("start");
            Timestamp endDateTime = rs.getTimestamp("end");
            long contactId = rs.getLong("contact_id");
            long userId = rs.getLong("user_id");
            System.out.println(contactId);
            appointment  = new Appointment(aptId, title, description, location, type, startDateTime, endDateTime, customerId, contactId, userId);
            allAppointment.add(appointment);
        }
        return allAppointment;
    }

    @Override
    public void update(Appointment obj) {

    }

    @Override
    public void delete(Appointment appointment) {

    }

    @Override
    public void save(Appointment obj) {

    }
}
