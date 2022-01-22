package Dao;

import dbConnection.JDBCConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDaoImpl extends JDBCConnection {

    public static String userName;

    public static boolean findByUserName(String name, String userPassword) {
        boolean isUserFind = false;
        try {
//            statement = connection.createStatement();
            String sql = "SELECT User_ID, Password FROM users WHERE User_Name = '" + name + "'";
            PreparedStatement preparedStatement = JDBCConnection.connection.prepareStatement(sql);
            preparedStatement.execute();
            result = statement.executeQuery(sql);
            isUserFind = Validator.validateUserLogin(result, userPassword);
            userName = isUserFind ? name : null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isUserFind;
    }
}
