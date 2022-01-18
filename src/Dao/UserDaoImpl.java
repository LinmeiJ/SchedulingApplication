package Dao;

import java.sql.SQLException;

public class UserDaoImpl extends JDBCConnection {

    public static boolean findByUserName(String userName, String userPassword) {
        boolean isUserFind = false;
        try {
            statement = connection.createStatement();
            String sql = "SELECT User_ID, Password FROM users WHERE User_Name = '" + userName + "'";
            result = statement.executeQuery(sql);
            isUserFind = Validator.validateUserLogin(result, userPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isUserFind;
    }
}
