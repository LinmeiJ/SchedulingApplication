package dao;

import dbConnection.JDBCConnection;
import entity.Country;
import entity.Report;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

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

    public ObservableList<Report> getCountByMonth() throws SQLException {
        ResultSet rs = findRawDataFromDB("SELECT MONTHNAME(Start) as 'Month', COUNT(*) as 'Count' FROM appointments GROUP BY Month");
        while (rs.next()) {
            String month = rs.getString("month");
            int count = rs.getInt("count");
            Report report = new Report();
            report.setMonth(month);
            report.setCount(count);
            reports.add(report);
        }
        return reports;
    }

    public ObservableList<Country> getCustCountByCountry() throws SQLException {
        ObservableList<Country> ctry = FXCollections.observableArrayList();

        String sql = "SELECT COUNT(Customer_ID) AS 'Count', Country FROM client_schedule.customers as c JOIN client_schedule.first_level_divisions as f ON c.Division_ID = f.Division_ID JOIN client_schedule.countries as cty ON f.Country_ID = cty.Country_ID Group by Country";
        ResultSet rs = findRawDataFromDB(sql);
        while (rs.next()) {
            String country = rs.getString("country");
            long count = rs.getInt("count");
            Country ctr = new Country();
            ctr.setCountry(country);
            ctr.setCount(count);
            ctry.add(ctr);
        }
        return ctry;
    }
}
