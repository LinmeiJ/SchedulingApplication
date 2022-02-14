package dao;

import dbConnection.JDBCConnection;
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
    ObservableList<String> allContacts = FXCollections.observableArrayList();

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
     * This method finds all contacts from the contacts table in the database
     *
     * @return a list of contact names
     */
    public ObservableList<String> findAll() {
        ResultSet rs = findRawDataFromDB("SELECT contact_Name FROM contacts");
        try {
            while (rs.next()) {
                String name = rs.getString("contact_name");
                allContacts.add(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allContacts;
    }
}
