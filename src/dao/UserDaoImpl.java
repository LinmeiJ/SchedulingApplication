package dao;

import controller.Validator;
import dbConnection.JDBCConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class contains logics for the data flow between the controller and the Users table in the database.
 *
 * @author Linmei M.
 */
public class UserDaoImpl extends JDBCConnection {

    public static String userName;
    public static long userId;

    /**
     * This method finds a username by user input name and user input password
     * <p>
     * Lambda expression 3
     * </p>
     *
     * @param name         user input name
     * @param userPassword user input password
     * @return true if the user is find, otherwise return false.
     */
    public static boolean findByUserName(String name, String userPassword) {
        boolean isUserFind = false;
        try {
            String sql = "SELECT User_ID, Password FROM users WHERE User_Name = '" + name + "'";
            PreparedStatement preparedStatement = JDBCConnection.connection.prepareStatement(sql);
            preparedStatement.execute();
            result = statement.executeQuery(sql);
            while (result.next()) {
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
