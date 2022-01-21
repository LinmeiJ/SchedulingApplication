package Dao;

import controller.AddNewCustomerController;
import dbConnection.JDBCConnection;
import entity.FirstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class FirstLevelDivisionDaoImpl extends JDBCConnection implements ServiceIfc<FirstLevelDivision> {
    List<FirstLevelDivision> listOfFirstLevelDivisions;

    @Override
    public ObservableList<FirstLevelDivision> findAll() throws SQLException {
        return null;
    }

    @Override
    public FirstLevelDivision findById(long id){
        FirstLevelDivision firstLevelDivision = null;
        try{
            ResultSet rs = getData(id);
           firstLevelDivision = assignObject(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return firstLevelDivision;
    }

    @Override
    public void update(FirstLevelDivision obj) {

    }

    @Override
    public void delete(FirstLevelDivision obj) {

    }

    @Override
    public void save(FirstLevelDivision obj) {

    }

    public long findIdByDivisionName(String divisionName) throws SQLException {
        long id = 0;
        String countryName = String.valueOf(AddNewCustomerController.ctryID).substring(0,1) + "." +  String.valueOf(AddNewCustomerController.ctryID).substring(1);
        ResultSet rs = findRawDataFromDB("SELECT division_id from first_level_divisions where country_id = (SELECT country_id FROM countries where Country = '" + countryName + "') and division = '"+ divisionName + "'");
        while(rs.next()){
           id = rs.getLong("division_id");
        }
        return id;
    }

    public ObservableList<String> getAllDivisions(){
        ObservableList<String> divisions = FXCollections.observableArrayList();
        ResultSet rs = findRawDataFromDB("SELECT DISTINCT Division FROM first_level_divisions WHERE Country_ID = " + AddNewCustomerController.ctryID.getId());
        try {
            while (rs.next()) {
                divisions.add(rs.getString("Division"));
            }
        }catch(Exception e){}
        return  divisions;
    }

    private FirstLevelDivision assignObject(ResultSet rs) throws SQLException {
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

    private ResultSet getData(long id) throws SQLException {
        statement = connection.createStatement();
        String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = '"+ id + "'";
        return statement.executeQuery(sql);
    }
}
