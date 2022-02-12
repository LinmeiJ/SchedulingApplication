package dao;

import controller.CustomerRecordController;
import dbConnection.JDBCConnection;
import entity.FirstLevelDivision;
import enums.CountryId;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * This class contains logics for the data flow between the controller and the first level divisions table in the database.
 *
 * @Linmei M.
 */
public class FirstLevelDivisionDaoImpl extends JDBCConnection {

    /**
     * This method finds all the columns from the first level division based on a division ID
     *
     * @param id a division ID
     * @return a list of first level division
     */
    public FirstLevelDivision findById(long id) {
        FirstLevelDivision firstLevelDivision = null;
        ResultSet rs = findRawDataFromDB("SELECT * FROM first_level_divisions WHERE Division_ID = '" + id + "'");
        firstLevelDivision = mapObjMembers(rs);
        return firstLevelDivision;
    }

    /**
     * This method finds ID by a division name
     *
     * @param divisionName a division name
     * @return a division ID
     */
    public long findIdByDivisionName(String divisionName) {
        long id = 0;
        String countryName = (CustomerRecordController.ctryId == CountryId.US) ? String.valueOf(CustomerRecordController.ctryId).substring(0, 1)
                + "." + String.valueOf(CustomerRecordController.ctryId).substring(1) : String.valueOf(CustomerRecordController.ctryId);

        ResultSet rs = findRawDataFromDB("SELECT Division_Id from first_level_divisions where Country_Id = (SELECT Country_Id FROM client_schedule.countries where Country = '"
                + countryName + "') and Division = '" + divisionName + "'");

        try {
            while (rs.next()) {
                id = rs.getLong("division_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * This method gets all the divisions based on a country ID that no duplicate allowed
     *
     * @return a list of divisions
     */
    public ObservableList<String> getAllDivisions() {
        ObservableList<String> divisions = FXCollections.observableArrayList();
        CountryId id = CustomerRecordController.ctryId;
        String specificCtyDivisionQuery = "SELECT DISTINCT Division FROM first_level_divisions WHERE Country_ID = ";
        String allCtyDivisionQuery = "SELECT DISTINCT Division FROM first_level_divisions";

        ResultSet rs = (CustomerRecordController.ctryId != null) ? findRawDataFromDB(specificCtyDivisionQuery + CustomerRecordController.ctryId.getId()) : findRawDataFromDB(allCtyDivisionQuery);

        try {
            while (rs.next()) {
                divisions.add(rs.getString("Division"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return divisions;
    }

    /**
     * This method gets all the divisions based on a country ID that no duplicate allowed
     *
     * @return a list of divisions
     */
//    public ObservableList<String> getAllDivisions() {
//        ObservableList<String> divisions = FXCollections.observableArrayList();
//        CountryId id = CustomerRecordController.ctryId;
//        String specificCtyDivisionQuery = "SELECT DISTINCT Division FROM first_level_divisions WHERE Country_ID = ";
//        String allCtyDivisionQuery = "SELECT DISTINCT Division FROM first_level_divisions";
//
//        ResultSet rs = (CustomerRecordController.ctryId != null) ? findRawDataFromDB(specificCtyDivisionQuery + CustomerRecordController.ctryId.getId()) : findRawDataFromDB(allCtyDivisionQuery);
//
//        try {
//            while (rs.next()) {
//                divisions.add(rs.getString("Division"));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return divisions;
//    }

    /** This method maps table columns from the database and save it as a FirstLevelDivision object
     * @param rs a raw data that returned from the firstLevelDivision table
     * @return a FirstLevelDivision object
     */
    private FirstLevelDivision mapObjMembers(ResultSet rs) {
        FirstLevelDivision firstLevelDivisionDivision = null;
        try {
            while (rs.next()) {
                long id = rs.getLong("Division_ID");
                String division = rs.getString("Division");
                Timestamp createdDate = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdateBy = rs.getString("Last_updated_By");
                long countryId = rs.getLong("Country_ID");

                firstLevelDivisionDivision = new FirstLevelDivision(id, division, createdDate, createdBy, lastUpdate, lastUpdateBy, countryId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return firstLevelDivisionDivision;
    }
}
