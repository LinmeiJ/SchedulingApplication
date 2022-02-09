package dao;

import dbConnection.JDBCConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactDaoImpl extends JDBCConnection {
    AppointmentDaoImpl appointmentDao = new AppointmentDaoImpl();
    ObservableList<String> allContacts = FXCollections.observableArrayList();
    public long getContactId(String name) throws SQLException {
        long id = 0;
        ResultSet rs = findRawDataFromDB("SELECT Contact_ID FROM contacts WHERE Contact_Name = '" + name + "'");
        while(rs.next()){
            id = rs.getLong("contact_id");
        }
        return id;
    }

    public String findNameByID(long contact_id) throws SQLException {
        ResultSet rs = findRawDataFromDB("SELECT Contact_Name FROM contacts WHERE Contact_ID = " + contact_id);
        while(rs.next()){
            return rs.getString("contact_name");
        }
        return "";
    }

    public ObservableList<String> findAll() throws SQLException {
        ResultSet rs = findRawDataFromDB("SELECT contact_Name FROM contacts");
        while(rs.next()){
            String name = rs.getString("contact_name");
            allContacts.add(name);
        }
        return allContacts;
    }

    public List<Integer> findAllContactId() throws SQLException {
        List<Integer> contactIds = new ArrayList<>();
        ResultSet rs = findRawDataFromDB("SELECT contact_ID FROM contacts");
        while(rs.next()){
            Integer id = rs.getInt("contact_id");
            contactIds.add(id);
        }
        return contactIds;
    }

//    public ObservableList<String> findAllContactReport(){
//        try {
//            List<Integer> allContactId = findAllContactId();
//            for(Integer id : allContactId){
//               appointmentDao.findAllById(id);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
