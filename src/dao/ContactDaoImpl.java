package dao;

import dbConnection.JDBCConnection;
import entity.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class contains logics for the data flow between the controller and the contacts table in the database.
 *
 * @author Linmei M.
 */
public class ContactDaoImpl extends JDBCConnection {
    /**
     * Initialize a list that can contain Contact objects
     */
    ObservableList<Contact> allContacts = FXCollections.observableArrayList();

    /**
     * This method gets the contact ID based on a contact name
     *
     * @param name a contact name
     * @return a contact ID
     */
    public long getContactId(String name) {
        long id = 0;
        ResultSet rs = findRawDataFromDB("SELECT Contact_ID FROM contacts WHERE Contact_Name = '" + name + "'");
        try {
            while (rs.next()) {
                id = rs.getLong("contact_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * This method finds a contact name by giving a contact id
     *
     * @param contact_id a contact id
     * @return a contact name
     */
    public String findNameByID(long contact_id) {
        ResultSet rs = findRawDataFromDB("SELECT Contact_Name FROM contacts WHERE Contact_ID = " + contact_id);
        try {
            while(rs.next()) {
                return rs.getString("contact_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * This method finds all contact names
     *
     * @return contact names
     */
    public ObservableList<String> findName() {
        ObservableList<String> contactName = FXCollections.observableArrayList();
        ResultSet rs = findRawDataFromDB("SELECT Contact_Name FROM contacts");
        try {
            while(rs.next()) {
                contactName.add(rs.getString("contact_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactName;
    }

    /**
     * This method finds all contacts from the contacts table in the database
     *
     * @return a list of contact names
     */
    public ObservableList<Contact> findAll() {
        ResultSet rs = findRawDataFromDB("SELECT contact_Name, contact_ID, email FROM contacts");
        try {
            while (rs.next()) {
                String name = rs.getString("contact_name");
                int contactId = rs.getInt("contact_ID");
                String email = rs.getString("email");
                Contact contact = new Contact(contactId, name, email);
                allContacts.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allContacts;
    }
}
