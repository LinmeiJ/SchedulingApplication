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
     * @param id the customer ID
     * @return a list of appointment
     */
    public ObservableList<Appointment> findAllByCustId(long id) {
        ResultSet rs = findRawDataFromDB("SELECT Appointment_ID, Title, a.Description, Location, a.Type, a.Start, a.End, User_ID, Contact_ID FROM appointments as a WHERE Customer_ID = " + id);
        convertToObj(id, rs);
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
     * @param customerId the customer ID
     * @param rs         the raw data returned from database
     */
    private void convertToObj(long customerId, ResultSet rs) {
        try {
            while (rs.next()) {
                long aptId = rs.getLong("appointment_id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String location = rs.getString("location");
                String type = rs.getString("type");
                Timestamp startDateTime = DateTimeConverter.convertUTCToLocal(String.valueOf(rs.getTimestamp("start")));
                Timestamp endDateTime = DateTimeConverter.convertUTCToLocal(String.valueOf(rs.getTimestamp("end")));

                long contactId = rs.getLong("contact_id");
                long userId = rs.getLong("user_id");

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
            preparedStatement.setTimestamp(7, DateTimeConverter.convertLocalTimeToUTC(LocalDateTime.now()));
            preparedStatement.setString(8, UserDaoImpl.userName);
            preparedStatement.setTimestamp(9, DateTimeConverter.convertLocalTimeToUTC(LocalDateTime.now()));
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
            Validator.displayDeleteConfirmation();
            Validator.displaySuccess("The appointment ID " + appointment.getAppointment_id() + " and type as " + appointment.getType() + " is deleted");
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
        String sql = "INSERT INTO appointments VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = JDBCConnection.connection.prepareStatement(sql);

            preparedStatement.setString(1, appointment.getTitle());
            preparedStatement.setString(2, appointment.getDescription());
            preparedStatement.setString(3, appointment.getLocation());
            preparedStatement.setString(4, appointment.getType());
            preparedStatement.setTimestamp(5, appointment.getStart());
            preparedStatement.setTimestamp(6, appointment.getEnd());
            preparedStatement.setTimestamp(7, appointment.getCreated_date());
            preparedStatement.setString(8, appointment.getCreated_by());
            preparedStatement.setTimestamp(9, appointment.getLast_update());
            preparedStatement.setString(10, appointment.getLast_updated_by());
            preparedStatement.setLong(11, appointment.getCustomer_id());
            preparedStatement.setLong(12, appointment.getUser_id());
            preparedStatement.setLong(13, appointment.getContact_id());

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
    public String getAllUpcomingApts() {
        String message = "";
        List<Appointment> upcomingApt;
        upcomingApt = findAll()
                .stream()
                .filter(apt -> DateTimeConverter.isWithin15mins(apt.getStart()))
                .collect(Collectors.toList());
        if (upcomingApt.size() == 0) {
            message = "There is no upcoming appointment.";
        } else {
            for (Appointment apt : upcomingApt) {
                message = message + "\n" + "Appointment ID: " + apt.getAppointment_id() + ",    Start: " + apt.getStart();
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
     * This method checks whether a new appointment time is already been booked.
     *
     * @param contactId the contact id user has selected
     * @param start     the start date user has selected
     * @param startH    the start hour user has selected
     * @param startM    the start minute user has selected
     * @param end       the end date user has selected
     * @param endH      the end hour user has selected
     * @param endM      the end minute user has selected
     * @return returns true when the time is available, otherwise, returns false.
     */
    public boolean isDoubleBooking(long contactId, LocalDate start, String startH, String startM, LocalDate end, String endH, String endM) {
        List<Appointment> scheduleList = findByContactId(contactId);
        if(AppointmentRecordController.selectApt != null){
            return isAppointmentTimeUpdated(DateTimeConverter.convertAptTimeToUTC(start, startH, startM), DateTimeConverter.convertAptTimeToUTC(end, endH, endM));
        }
        Timestamp aptStartTime = DateTimeConverter.convertAptTimeToEST(start, startH, startM);
        Timestamp aptEndTime = DateTimeConverter.convertAptTimeToEST(end, endH, endM);
        List<Appointment> sameDateScheduleList = filterByDate(scheduleList, aptStartTime);
        return BookingAvailability.checkBookingStatus(sameDateScheduleList, aptStartTime, aptEndTime);
    }

    /**
     * This method checks whether the appointment schedule is changes or if it is a new appointment
     *
     * @param aptStartTime an appointment starting time
     * @param aptEndTime   an appointment ending time
     * @return false when the appointment time is not changed, otherwise returns true.
     */
    private boolean isAppointmentTimeUpdated(Timestamp aptStartTime, Timestamp aptEndTime) {
        return !(aptStartTime.equals(AppointmentRecordController.selectApt.getStart())
                && aptEndTime.equals(AppointmentRecordController.selectApt.getEnd()));
    }

    /**
     * This methods filters appointment by dates. returns the same date in EST timezone
     *
     * @param scheduleList a list of time that is already scheduled and saved in database
     * @param aptStartTime a new appointment starting time
     * @return returns a list of appointment that is in the same date.
     */
    public List<Appointment> filterByDate(List<Appointment> scheduleList, Timestamp aptStartTime) {
        LocalDate date = aptStartTime.toLocalDateTime().toLocalDate();
        return scheduleList.stream()
                .filter(apt -> apt.getStart().toLocalDateTime().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }
}
