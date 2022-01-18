package Dao;

import entity.FirstLevelDivision;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public class FirstLevelDivisionDaoImpl extends JDBCConnection {
    List<FirstLevelDivision> listOfFirstLevelDivisions;

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
