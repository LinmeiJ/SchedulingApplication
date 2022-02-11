package dao;

import dbConnection.JDBCConnection;
import entity.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains logics for the data flow between the controller and the contacts table in the database.
 *
 * @Linmei M.
 */
public class ContactDaoImpl extends JDBCConnection{
    ObservableList<String> allContacts = FXCollections.observableArrayList();

    /**
     * This method gets the contact ID based on a contact name
     * @param name a contact name
     * @return a contact ID
     */
    public long getContactId(String name) {
        long id = 0;
        ResultSet rs = findRawDataFromDB("SELECT Contact_ID FROM contacts WHERE Contact_Name = '" + name + "'");
        try {
            while (true) {

                if (!rs.next()) break;

                id = rs.getLong("contact_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * This method finds a contact name by giving a contact id
     * @param contact_id a contact id
     * @return a contact name
     */
    public String findNameByID(long contact_id){
        ResultSet rs = findRawDataFromDB("SELECT Contact_Name FROM contacts WHERE Contact_ID = " + contact_id);
        try {
            while (rs.next()) {
                return rs.getString("contact_name");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return "";
    }

    /**
     * This method finds all contacts from the contacts table in the database
     * @return a list of contact names
     */
    public ObservableList<String> findAll() {
        ResultSet rs = findRawDataFromDB("SELECT contact_Name FROM contacts");
        try {
            while (true) {
                if (!rs.next()) break;
                String name = rs.getString("contact_name");
                allContacts.add(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allContacts;
    }

//    public List<Integer> findAllContactId() throws SQLException {
//        List<Integer> contactIds = new ArrayList<>();
//        ResultSet rs = findRawDataFromDB("SELECT contact_ID FROM contacts");
//        while(rs.next()){
//            Integer id = rs.getInt("contact_id");
//            contactIds.add(id);
//        }
//        return contactIds;
//    }

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
