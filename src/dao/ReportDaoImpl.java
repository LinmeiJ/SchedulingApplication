package dao;

import dbConnection.JDBCConnection;
import entity.Customer;
import entity.Report;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportDaoImpl extends JDBCConnection {
    ObservableList<Report> reports = FXCollections.observableArrayList();


    public ObservableList<Report>  getCountByType() throws SQLException {
        ResultSet rs = findRawDataFromDB("SELECT Type, COUNT(*) as 'Count' FROM client_schedule.appointments GROUP BY type");
        while (rs.next()) {
            String type = rs.getString("Type");
            int count = rs.getInt("Count");
            Report report = new Report(type, count);
            reports.add(report);
        }
        return reports;
    }

    public ObservableList<Report>  getCountByMonth() throws SQLException {
        ResultSet rs = findRawDataFromDB("SELECT Type, COUNT(*) as 'Count' FROM client_schedule.appointments GROUP BY type");
        while (rs.next()) {
            String type = rs.getString("Type");
            int count = rs.getInt("Count");
            Report report = new Report(type, count);
            reports.add(report);
        }
        return reports;
    }

    public ObservableList<Report> getCustCountByCountry() throws SQLException {
        String sql = "SELECT COUNT(Customer_ID) AS 'Count', Country\n" +
                "FROM client_schedule.customers as c\n" +
                "JOIN client_schedule.first_level_divisions as f ON c.Division_ID = f.Division_ID\n" +
                "JOIN client_schedule.countries as cty ON f.Country_ID = cty.Country_ID Group by Country";
        ResultSet rs = findRawDataFromDB("SELECT Type, COUNT(*) as 'Count' FROM client_schedule.appointments GROUP BY type");
        while (rs.next()) {
            String type = rs.getString("Count");
            int count = rs.getInt("Country");
            Report report = new Report(type, count);
            reports.add(report);
        }
        return reports;
    }

}
