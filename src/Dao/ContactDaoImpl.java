package Dao;

import dbConnection.JDBCConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactDaoImpl extends JDBCConnection {

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
}
