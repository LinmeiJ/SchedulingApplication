package Dao;

import dbConnection.JDBCConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactDaoImpl extends JDBCConnection {

    public long getContactId(String name) throws SQLException {
        ResultSet rs = findRawDataFromDB("SELECT Contact_ID from contacts WHERE Contact_Name = '" + name + "'");
        while(rs.next())
            return rs.getLong("contact_id");
        return 0;
    }
}
