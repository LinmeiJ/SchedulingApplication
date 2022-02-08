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

}
