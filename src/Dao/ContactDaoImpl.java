package Dao;

import dbConnection.JDBCConnection;
import entity.Contact;
import entity.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactDaoImpl extends JDBCConnection {
    ObservableList<Contact> allContacts = FXCollections.observableArrayList();
    public long getContactId(String name) throws SQLException {
        ResultSet rs = findRawDataFromDB("SELECT Contact_ID FROM contacts WHERE Contact_Name = '" + name + "'");
        while(rs.next())
            return rs.getLong("contact_id");
        return 0;
    }

    public String findNameByID(long contact_id) throws SQLException {
        ResultSet rs = findRawDataFromDB("SELECT Contact_Name FROM contacts WHERE Contact_ID = " + contact_id);
        while(rs.next()){
            return rs.getString("contact_name");
        }
        return "";
    }

    public ObservableList<Contact> findAll() throws SQLException {
        ResultSet rs = findRawDataFromDB("SELECT contact_Name FROM contacts");
        while(rs.next()){
            String name = rs.getString("contact_name");
            Contact contact = new Contact();
            contact.setContract_name(name);
            allContacts.add(contact);
        }
        return allContacts;
    }
}
