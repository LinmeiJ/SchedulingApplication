package Dao;

import controller.CustomerRecordController;
import dbConnection.JDBCConnection;
import entity.FirstLevelDivision;
import enums.CountryId;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class FirstLevelDivisionDaoImpl extends JDBCConnection implements ServiceIfc<FirstLevelDivision> {
    @Override
    public ObservableList<FirstLevelDivision> findAll() throws SQLException {
        return null;
    }

    @Override
    public FirstLevelDivision findById(long id){
        FirstLevelDivision firstLevelDivision = null;
        try{
            ResultSet rs = findRawDataFromDB("SELECT * FROM first_level_divisions WHERE Division_ID = '"+ id + "'");
           firstLevelDivision = mapObjMembers(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return firstLevelDivision;
    }

    @Override
    public void update(FirstLevelDivision obj) {

    }

    @Override
    public void delete(FirstLevelDivision firstLevelDivision) {

    }

    @Override
    public void save(FirstLevelDivision obj) {

    }

    public long findIdByDivisionName(String divisionName) throws SQLException {
        long id = 0;
        String countryName = (CustomerRecordController.ctryId == CountryId.US) ? String.valueOf(CustomerRecordController.ctryId).substring(0,1) + "." +  String.valueOf(CustomerRecordController.ctryId).substring(1) : String.valueOf(CustomerRecordController.ctryId);

        ResultSet rs = findRawDataFromDB("SELECT Division_Id from first_level_divisions where Country_Id = (SELECT Country_Id FROM client_schedule.countries where Country = '" + countryName + "') and Division = '"+ divisionName + "'");
        while(rs.next()){
           id = rs.getLong("division_id");
        }
        return id;
    }

    public ObservableList<String> getAllDivisions(){
        ObservableList<String> divisions = FXCollections.observableArrayList();
        CountryId id = CustomerRecordController.ctryId ;
        String specificCtyDivisionQuery = "SELECT DISTINCT Division FROM first_level_divisions WHERE Country_ID = ";
        String allCtyDivisionQuery = "SELECT DISTINCT Division FROM first_level_divisions";

        ResultSet rs = (CustomerRecordController.ctryId  != null) ? findRawDataFromDB(specificCtyDivisionQuery + CustomerRecordController.ctryId.getId()) : findRawDataFromDB(allCtyDivisionQuery);

        try {
            while (rs.next()) {
                divisions.add(rs.getString("Division"));
            }
        }catch(Exception e){

        }
        return  divisions;
    }

    private FirstLevelDivision mapObjMembers(ResultSet rs) throws SQLException {
        FirstLevelDivision firstLevelDivisionDivision = null;
        while(rs.next()){
            long id = rs.getLong("Division_ID");
            String division = rs.getString("Division");
            Timestamp createdDate =  rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdateBy = rs.getString("Last_updated_By");
            long countryId = rs.getLong("Country_ID");

            firstLevelDivisionDivision = new FirstLevelDivision(id, division, createdDate, createdBy, lastUpdate, lastUpdateBy, countryId);
        }
        return firstLevelDivisionDivision;
    }
}
