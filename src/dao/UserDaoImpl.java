package dao;

import controller.Validator;
import dbConnection.JDBCConnection;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class contains logics for the data flow between the controller and the Users table in the database.
 *
 * @author Linmei M.
 */
public class UserDaoImpl extends JDBCConnection {
    /**
     * a user name
     */
    public static String userName;
    /**
     * a user ID
     */
    public static long userId;
    /**
     * a user list
     */
    public ObservableList<User> userList;

    /**
     * This method finds a username by user input name and user input password
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

    public ObservableList<User> findAll() {
        userList = FXCollections.observableArrayList();
        ResultSet rs = findRawDataFromDB("SELECT User_ID, User_Name, Password, Create_Date, Created_By, Last_Update, Last_Updated_By FROM users");
        try {
            while (rs.next()) {
                String name = rs.getString("user_name");
                int userId = rs.getInt("user_id");
                String password = rs.getString("password");
                User user = new User(userId, name, password);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

}
