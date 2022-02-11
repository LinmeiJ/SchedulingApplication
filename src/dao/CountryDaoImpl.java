package dao;

import dbConnection.JDBCConnection;
import entity.Country;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * This class contains logics for the data flow between the controller and the countries table in the database.
 *
 * @Linmei M.
 */
public class CountryDaoImpl extends JDBCConnection {
//
//    public Country findById(long id){
//        Country country = null;
//        try{
////            ResultSet rs = getData(id);
////            country = assignObject(rs);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return country;
//    }
//
//    private Country assignObject(ResultSet rs) throws SQLException {
//        Country country = null;
//        while(rs.next()){
//            long id = rs.getLong("Country_ID");
//            String countryName = rs.getString("Country");
//            Timestamp createdDate =  rs.getTimestamp("Create_Date");
//            String createdBy = rs.getString("Created_By");
//            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
//            String lastUpdateBy = rs.getString("Last_updated_By");
//
//            country = new Country(id, countryName, createdDate, createdBy, lastUpdate, lastUpdateBy);
//        }
//        return country;
//    }
//
//    private ResultSet getData(long id) throws SQLException {
//        statement = connection.createStatement();
//        String sql = "SELECT * FROM countries WHERE Country_ID = '"+ id + "'";
//        return statement.executeQuery(sql);
//    }
}
