package DBService;

import controllers.Validator;

import java.sql.SQLException;

public class DBService extends JDBCConnection {

    public boolean findUser(String userName, String userPassword) throws SQLException {
        try {
            statement = connection.createStatement();
            String sql = "SELECT User_ID, Password FROM users WHERE User_Name = '"+ userName +"'";
            result = statement.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Validator.validateUserLogin(result, userPassword);
    }
}
