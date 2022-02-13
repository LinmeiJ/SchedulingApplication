package dao;

import dbConnection.JDBCConnection;
import entity.Country;
import entity.Report;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class contains logics for getting data from multiple tables in the database for reports
 *
 * @author Linmei M.
 */
public class ReportDaoImpl extends JDBCConnection {
    ObservableList<Report> reports = FXCollections.observableArrayList();

    /**
     * THis method gets all the types with each type total counts from the appointment table
     *
     * @return a list of total count grouped by type
     */
    public ObservableList<Report> getCountByType() {
        ResultSet rs = findRawDataFromDB("SELECT Type, COUNT(*) as 'Count' FROM client_schedule.appointments GROUP BY type");
        try {
            while (rs.next()) {
                String type = rs.getString("Type");
                int count = rs.getInt("Count");
                Report report = new Report(type, count);
                reports.add(report);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reports;
    }

    /**
     * This method finds total count of appointments by month
     *
     * @return a list of appointments and grouped by month
     */
    public ObservableList<Report> getCountByMonth() {
        ResultSet rs = findRawDataFromDB("SELECT MONTHNAME(Start) as 'Month', COUNT(*) as 'Count' FROM appointments GROUP BY Month");
        try {
            while (rs.next()) {
                String month = rs.getString("month");
                int count = rs.getInt("count");
                Report report = new Report();
                report.setMonth(month);
                report.setCount(count);
                reports.add(report);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reports;
    }

    /**
     * This method find total count of customers by country
     *
     * @return a list of total count of customers and grouped by country
     */
    public ObservableList<Country> getCustCountByCountry() {
        ObservableList<Country> ctry = FXCollections.observableArrayList();

        String sql = "SELECT COUNT(Customer_ID) AS 'Count', Country FROM client_schedule.customers as c JOIN client_schedule.first_level_divisions as f ON c.Division_ID = f.Division_ID JOIN client_schedule.countries as cty ON f.Country_ID = cty.Country_ID Group by Country";
        ResultSet rs = findRawDataFromDB(sql);
        try {
            while (rs.next()) {
                String country = rs.getString("country");
                long count = rs.getInt("count");
                Country ctr = new Country();
                ctr.setCountryName(country);
                ctr.setCount(count);
                ctry.add(ctr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ctry;
    }
}
