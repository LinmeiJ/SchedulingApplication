package dao;

import dateUtil.BookingAvailability;
import dateUtil.DateTimeConverter;
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

public class AppointmentDaoImpl extends JDBCConnection implements ServiceIfc<Appointment>{
    private Appointment appointment;
    private ObservableList<Appointment> allAppointment = FXCollections.observableArrayList();

    @Override
    public ObservableList<Appointment> findAll() throws SQLException {
        ResultSet rs = findRawDataFromDB("SELECT Appointment_ID, Start, End FROM appointments");
        while (rs.next()) {
            long aptId = rs.getLong("appointment_id");
            Timestamp startDateTime = DateTimeConverter.convertUTCToEST(rs.getTimestamp("start"));
            Timestamp endDateTime = DateTimeConverter.convertUTCToEST(rs.getTimestamp("end"));

            appointment = new Appointment(aptId, startDateTime, endDateTime);
            allAppointment.add(appointment);
        }
        return allAppointment;
    }

    @Override
    public Appointment findById(long id) {
        return null;
    }

    public ObservableList<Appointment> findAllByCustId(long customerId) throws SQLException {
        ResultSet rs = findRawDataFromDB("SELECT Appointment_ID, Title, a.Description, Location, a.Type, a.Start, a.End, User_ID, Contact_ID FROM appointments as a WHERE Customer_ID = " + customerId);
        convertToObj(customerId, rs);
        return allAppointment;
    }


    private void convertToObj(long customerId, ResultSet rs) throws SQLException {
        while(rs.next()){
            long aptId = rs.getLong("appointment_id");
            String title = rs.getString("title");
            String description =  rs.getString("description");
            String location =  rs.getString("location");
            String type =  rs.getString("type");
            Timestamp startDateTime = DateTimeConverter.convertUTCToLocal(String.valueOf(rs.getTimestamp("start")));
            Timestamp endDateTime = DateTimeConverter.convertUTCToLocal(String.valueOf(rs.getTimestamp("end")));

            long contactId = rs.getLong("contact_id");
            long userId = rs.getLong("user_id");

            appointment  = new Appointment(aptId, title, description, location, type, startDateTime, endDateTime, customerId, contactId, userId);
            allAppointment.add(appointment);
        }
    }

    @Override
    public void update(Appointment appointment) {
        String sql = "UPDATE appointments SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Create_Date=?,Created_By=?, Last_Update=?, Last_Updated_By=?, Customer_ID=?, User_ID=?, Contact_ID=? WHERE Appointment_ID = " + appointment.getAppointment_id();
        try {
            PreparedStatement preparedStatement = getPreparedStatement(appointment, sql);
            preparedStatement.setTimestamp(6, appointment.getEnd());
            preparedStatement.setTimestamp(7, DateTimeConverter.convertLocalTimeToUTC(LocalDateTime.now()));
            preparedStatement.setString(8, UserDaoImpl.userName);
            preparedStatement.setTimestamp(9, DateTimeConverter.convertLocalTimeToUTC(LocalDateTime.now()));
            preparedStatement.setString(10, UserDaoImpl.userName);

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Validator.displaySuccess("Appointment is updated");
    }

    private PreparedStatement getPreparedStatement(Appointment appointment, String sql) throws SQLException {
        PreparedStatement preparedStatement = JDBCConnection.connection.prepareStatement(sql);
        preparedStatement.setString(1, appointment.getTitle());
        preparedStatement.setString(2, appointment.getDescription());
        preparedStatement.setString(3, appointment.getLocation());
        preparedStatement.setString(4, appointment.getType());
        preparedStatement.setTimestamp(5, appointment.getStart());
        preparedStatement.setTimestamp(6, appointment.getEnd());
        preparedStatement.setLong(11, appointment.getCustomer_id());
        preparedStatement.setLong(12, appointment.getUser_id());
        preparedStatement.setLong(13, appointment.getContact_id());
        return preparedStatement;
    }

    @Override
    public void delete(Appointment appointment) {
        try {
            statement = connection.createStatement();
            String sql = "DELETE FROM appointments WHERE Appointment_ID = "+ appointment.getAppointment_id();

            statement.executeUpdate(sql);
            Validator.displayDeleteConfirmation();
            Validator.displaySuccess("Delete");
        } catch (SQLException e) {
            System.out.println("something wrong with executing delete sql");
            e.printStackTrace();
        }
    }

    public void deleteByCustID(long id) throws SQLException {
        statement = connection.createStatement();
            String sql = "DELETE FROM appointments WHERE Customer_ID = "+ id;
    }

    @Override
    public void save(Appointment appointment) throws SQLException {
        String sql = "INSERT INTO appointments VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = getPreparedStatement(appointment, sql);
        preparedStatement.setTimestamp(7, appointment.getCreated_date());
        preparedStatement.setString(8, appointment.getCreated_by());
        preparedStatement.setTimestamp(9, appointment.getLast_update());
        preparedStatement.setString(10, appointment.getLast_updated_by());

        preparedStatement.execute();
        Validator.displaySuccess("Appointment is saved");
    }

    public String getAllUpcomingApts(){
        String message = "";
        List<Appointment> upcomingApt = null;
        try {
            upcomingApt = findAll()
                    .stream()
                    .filter(apt -> DateTimeConverter.isWithin15mins(apt.getStart()))
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(upcomingApt.size() == 0){
            message = "There is no upcoming appointment.";
        }else{
            for(Appointment apt : upcomingApt) {
                message = message + "\n" + "Upcoming Appointment ID: " + apt.getAppointment_id() + "/Start: " + apt.getStart();
            }
        }
        return message;
    }

    public List<Appointment> findByContactId(long contactId){
        List<Appointment> scheduleListByContactID = new ArrayList<>();
        try{
            ResultSet rs = findRawDataFromDB("Select Appointment_ID, Start, End FROM client_schedule.appointments WHERE Contact_ID = " + contactId);
            while(rs.next()){
                long aptId = rs.getLong("appointment_id");
                Timestamp start = DateTimeConverter.convertUTCToEST(rs.getTimestamp("start"));
                Timestamp end = DateTimeConverter.convertUTCToEST(rs.getTimestamp("end"));
                Appointment appointmentSchedule = new Appointment(aptId, start, end);
                scheduleListByContactID.add(appointmentSchedule);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
      return scheduleListByContactID;
    }

    public boolean isDoubleBooking(long contactId, LocalDate start, String startH, String startM, LocalDate end, String endH, String endM){
        List<Appointment> scheduleList = findByContactId(contactId);
        Timestamp aptStartTime = DateTimeConverter.convertAptTimeToEST(start, startH, startM);
        Timestamp aptEndTime = DateTimeConverter.convertAptTimeToEST(end, endH, endM);

        List<Appointment> sameDateScheduleList = filterByDate(scheduleList, aptStartTime);
        BookingAvailability.isDoubleBooking(sameDateScheduleList, start, end);
        return false;
    }

    public List<Appointment> filterByDate(List<Appointment> scheduleList, Timestamp aptStartTime){
        LocalDate date = aptStartTime.toLocalDateTime().toLocalDate();
        List<Appointment> sameDateScheduledList = scheduleList.stream()
                .filter(apt -> apt.getStart().toLocalDateTime().toLocalDate().equals(date))
                .collect(Collectors.toList());
        return sameDateScheduledList;
    }
}
