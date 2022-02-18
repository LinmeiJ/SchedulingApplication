package dao;

import controller.AppointmentRecordController;
import controller.Validator;
import dateTimeUtil.BookingAvailability;
import dateTimeUtil.DateTimeConverter;
import dbConnection.JDBCConnection;
import entity.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * This class contains logics for the data flow between the controller and the appointments table in the database.
 *
 * @author Linmei M.
 */
public class AppointmentDaoImpl extends JDBCConnection implements ServiceIfc<Appointment> {
    private final ContactDaoImpl contactDao = new ContactDaoImpl();
    private ObservableList<Appointment> allAppointment = FXCollections.observableArrayList();
    private Appointment appointment;

    /**
     * This method query all the appointment from the database table
     *
     * @return returns a list of appointments
     */
    public ObservableList<Appointment> findAll() {
        ResultSet rs = findRawDataFromDB("SELECT Appointment_ID, Start, End FROM appointments");
        try {
            while (rs.next()) {
                long aptId = rs.getLong("appointment_id");
                Timestamp startDateTime = DateTimeConverter.convertUTCToLocal(String.valueOf(rs.getTimestamp("start")));
                Timestamp endDateTime = DateTimeConverter.convertUTCToLocal(String.valueOf(rs.getTimestamp("end")));

                appointment = new Appointment(aptId, startDateTime, endDateTime);
                allAppointment.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allAppointment;
    }

    /**
     * This method finds all the appointment based a customer ID
     *
     * @return a list of appointment
     */
    public ObservableList<Appointment> findAllAppointment() {
        ResultSet rs = findRawDataFromDB("SELECT Appointment_ID, Title, Description, Location, Type, Start, End, User_ID, Contact_ID, Customer_ID FROM appointments");
        convertToObj(rs);
        return allAppointment;
    }

    /**
     * This method find all the appointments by a contact ID
     *
     * @param id a contact ID
     * @return a list of appointments
     */
    public ObservableList<Appointment> findAllByContactId(long id) {
        ResultSet rs = findRawDataFromDB("SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID FROM appointments WHERE Contact_ID = " + id);
        try {
            while (rs.next()) {
                long aptId = rs.getLong("appointment_id");
                String title = rs.getString("title");
                String location = rs.getString("location");
                String type = rs.getString("type");
                String description = rs.getString("description");
                Timestamp startDateTime = DateTimeConverter.convertUTCToLocal(String.valueOf(rs.getTimestamp("start")));
                Timestamp endDateTime = DateTimeConverter.convertUTCToLocal(String.valueOf(rs.getTimestamp("end")));
                long customerId = rs.getLong("Customer_id");

                appointment = new Appointment(aptId, title, location, type, description, startDateTime, endDateTime, customerId);
                allAppointment.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allAppointment;
    }

    /**
     * This method accepts a raw data from the database then pass in and convert the data into an appointment object.
     *
     * @param rs         the raw data returned from database
     */
    private void convertToObj( ResultSet rs) {
        try {
            while (rs.next()) {
                long aptId = rs.getLong("appointment_id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String location = rs.getString("location");
                String type = rs.getString("type");
                Timestamp startDateTime = rs.getTimestamp("start");
                Timestamp endDateTime =rs.getTimestamp("end");

                int contactId = rs.getInt("contact_id");
                int userId = rs.getInt("user_id");
                int customerId = rs.getInt("customer_id");

                appointment = new Appointment(aptId, title, description, location, type, startDateTime, endDateTime, customerId, contactDao.findNameByID(contactId), userId);
                appointment.setContact_id(contactId);
                allAppointment.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * THis method updates the appointment based on an appointment ID.
     *
     * @param appointment the appointment that needs to be updated
     */
    public void update(Appointment appointment) {
        String sql = "UPDATE appointments SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Create_Date=?,Created_By=?, Last_Update=?, Last_Updated_By=?, Customer_ID=?, User_ID=?, Contact_ID=? WHERE Appointment_ID = " + appointment.getAppointment_id();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = JDBCConnection.connection.prepareStatement(sql);

            preparedStatement.setString(1, appointment.getTitle());
            preparedStatement.setString(2, appointment.getDescription());
            preparedStatement.setString(3, appointment.getLocation());
            preparedStatement.setString(4, appointment.getType());
            preparedStatement.setTimestamp(5, appointment.getStart());
            preparedStatement.setTimestamp(6, appointment.getEnd());
            preparedStatement.setTimestamp(7,Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(8, UserDaoImpl.userName);
            preparedStatement.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(10, UserDaoImpl.userName);
            preparedStatement.setLong(11, appointment.getCustomer_id());
            preparedStatement.setLong(12, appointment.getUser_id());
            preparedStatement.setLong(13, appointment.getContact_id());

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method deletes an appointment from the appointment table
     *
     * @param appointment the appointment that required to be deleted
     */
    public void delete(Appointment appointment) {
        try {
            statement = connection.createStatement();
            String sql = "DELETE FROM appointments WHERE Appointment_ID = " + appointment.getAppointment_id();

            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("something wrong with executing delete sql");
            e.printStackTrace();
        }
    }

    /**
     * This method deletes an appointment/an appointments by customer ID
     *
     * @param id the customer ID
     */
    public void deleteByCustID(long id) {
        try {
            statement = connection.createStatement();
            String sql = "DELETE FROM appointments WHERE Customer_ID = " + id;
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method saves an appointment that passed in.
     *
     * @param appointment an new appointment that need to save to the database.
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    public void save(Appointment appointment) {

        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID, Created_By, Last_Update, create_date, Last_Updated_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = JDBCConnection.connection.prepareStatement(sql);

            preparedStatement.setString(1, appointment.getTitle());
            preparedStatement.setString(2, appointment.getDescription());
            preparedStatement.setString(3, appointment.getLocation());
            preparedStatement.setString(4, appointment.getType());
            preparedStatement.setTimestamp(5, appointment.getStart());
            preparedStatement.setTimestamp(6, appointment.getEnd());
            preparedStatement.setLong(7, appointment.getCustomer_id());
            preparedStatement.setLong(8, appointment.getUser_id());
            preparedStatement.setLong(9, appointment.getContact_id());
            preparedStatement.setString(10, appointment.getCreated_by());
            preparedStatement.setTimestamp(11, appointment.getLast_update());
            preparedStatement.setTimestamp(12, appointment.getCreated_date());
            preparedStatement.setString(13, appointment.getLast_updated_by());

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method checks whether there is an upcoming appointments within 15 minutes.
     *
     * @return a string that will be displayed on the user UI
     */
    public String getAllUpcomingApts(ResourceBundle language) {
        String message = "";
        List<Appointment> upcomingApt;
        upcomingApt = findAll()
                .stream()
                .filter(apt -> DateTimeConverter.isWithin15mins(apt.getStart()))
                .collect(Collectors.toList());
        if (upcomingApt.size() == 0) {
            message = language.getString("noApt");
        } else {
            for (Appointment apt : upcomingApt) {
                message = message + "\n" + language.getString("aptID") + ":" + apt.getAppointment_id() + ",    "
                        + language.getString("start") +":" + apt.getStart();
            }
        }
        return message;
    }

    /**
     * This methods find a list of appointment by a specific contact ID.
     *
     * @param contactId the contact ID that a appointment associate to
     * @return A list of appointment
     */
    public List<Appointment> findByContactId(long contactId) {
        List<Appointment> scheduleListByContactID = new ArrayList<>();
        try {
            ResultSet rs = findRawDataFromDB("Select Appointment_ID, Start, End, Contact_ID FROM client_schedule.appointments WHERE Contact_ID = " + contactId);
            while (rs.next()) {
                long aptId = rs.getLong("appointment_id");
                Timestamp start = DateTimeConverter.convertUTCToEST(rs.getTimestamp("start"));
                Timestamp end = DateTimeConverter.convertUTCToEST(rs.getTimestamp("end"));
                Appointment appointmentSchedule = new Appointment(aptId, start, end);
                appointmentSchedule.setContact_id(rs.getLong("contact_id"));
                scheduleListByContactID.add(appointmentSchedule);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scheduleListByContactID;
    }

    /**
     * This methods find a list of appointment by a specific customer ID.
     *
     * @param customerID the customer ID that a appointment associate to
     * @return A list of appointment
     */
    public List<Appointment> findByCustomerID(long customerID) {
        List<Appointment> scheduleListByContactID = new ArrayList<>();
        try {
            ResultSet rs = findRawDataFromDB("Select Appointment_ID, Start, End, Contact_ID FROM client_schedule.appointments WHERE Customer_ID = " + customerID);
            while (rs.next()) {
                long aptId = rs.getLong("appointment_id");
                if(AppointmentRecordController.selectApt != null && AppointmentRecordController.selectApt.getAppointment_id() == aptId){
                    continue;
                }
                Timestamp start = rs.getTimestamp("start");
                Timestamp end = rs.getTimestamp("end");
                Appointment appointmentSchedule = new Appointment(aptId, start, end);
                appointmentSchedule.setContact_id(rs.getLong("contact_id"));
                appointmentSchedule.setCustomer_id(customerID);
                scheduleListByContactID.add(appointmentSchedule);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scheduleListByContactID;
    }

    /**
     * This method checks whether a new appointment time is already been booked.
     *
     * @param customerID the customer id user has selected
     * @param startApt appointment start time
     * @param  endApt appointment end time
     * @return returns true when the time is available, otherwise, returns false.
     */
    public boolean isDoubleBooking(long customerID,
                                   LocalDateTime startApt, LocalDateTime endApt) {
        List<Appointment> scheduleList = findByCustomerID(customerID);
        if (AppointmentRecordController.selectApt != null) {
            if (isAppointmentTimeUpdated(startApt, endApt))
            {
                return false;
            }
        }
//        Timestamp aptStartTime = DateTimeConverter.convertAptTimeToEST(start, startH, startM);
//        Timestamp aptEndTime = DateTimeConverter.convertAptTimeToEST(end, endH, endM);
        List<Appointment> sameDateScheduleList = filterByDate(scheduleList, startApt);
        return BookingAvailability.checkBookingStatus(sameDateScheduleList, startApt, endApt);
    }

    /**
     * This method checks whether the appointment schedule is changes or if it is a new appointment
     *
     * @param aptStartTime an appointment starting time
     * @param aptEndTime   an appointment ending time
     * @return false when the appointment time is not changed, otherwise returns true.
     */
    private boolean isAppointmentTimeUpdated(LocalDateTime aptStartTime, LocalDateTime aptEndTime) {
        return aptStartTime.equals(AppointmentRecordController.selectApt.getStart())
                && aptEndTime.equals(AppointmentRecordController.selectApt.getEnd());
    }

    /**
     * This methods filters appointment by dates. returns the same date in EST timezone
     *
     * @param scheduleList a list of time that is already scheduled and saved in database
     * @param aptStartTime a new appointment starting time
     * @return returns a list of appointment that is in the same date.
     */
    public List<Appointment> filterByDate(List<Appointment> scheduleList, LocalDateTime aptStartTime) {
        LocalDate date = aptStartTime.toLocalDate();
        return scheduleList.stream()
                .filter(apt -> apt.getStart().toLocalDateTime().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }
}
