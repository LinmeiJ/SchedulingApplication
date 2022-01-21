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

    @Override
    public ResultSet findAll() {
        try {
            statement = connection.createStatement();
            String sql = "SELECT DISTINCT Division FROM first_level_divisions WHERE Country_ID = " + AddNewCustomerController.ctryID.getId();
            result = statement.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public ObservableList<String> getAllDivisions(){
        ObservableList<String> divisions = FXCollections.observableArrayList();
        ResultSet rs = findAll();
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
