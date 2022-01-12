package DBService;

import controllers.Validator;

import java.sql.SQLException;

public class DBService extends JDBCConnection {

    public boolean findUser(String userName, String userPassword) throws SQLException {
        try {
            statement = connection.createStatement();
            String sql = "SELECT User_ID, Password FROM users WHERE User_Name = '" + userName + "'";
            result = statement.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Validator.validateUserLogin(result, userPassword);
    }

    public void getCustomerRecord(){
        try {
            statement = connection.createStatement();
            String sql = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, d.Division_ID, d.Country_ID FROM customers c JOIN first_level_divisions d ON d.Division_ID = c.Division_ID";
            result = statement.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
