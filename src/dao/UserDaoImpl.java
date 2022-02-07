package dao;

import dbConnection.JDBCConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDaoImpl extends JDBCConnection {

    public static String userName;
    public static long userId;

    public static boolean findByUserName(String name, String userPassword) {
        boolean isUserFind = false;
        try {
            String sql = "SELECT User_ID, Password FROM users WHERE User_Name = '" + name + "'";
            PreparedStatement preparedStatement = JDBCConnection.connection.prepareStatement(sql);
            preparedStatement.execute();
            result = statement.executeQuery(sql);
            while(result.next()) {
                userId = result.getLong("user_id");
                isUserFind = Validator.validateUserLogin(result.getString("password"), userPassword);
            }
            userName = isUserFind ? name : null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isUserFind;
    }
}
