package dbConnection;

import java.sql.*;

/**
 * This method opens and closes the connection between the application and Mysql database
 *
 * @author Linmei M.
 */
public abstract class JDBCConnection {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL

    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static String password = "Passw0rd!"; // Password
    public static Connection connection;  // Connection Interface
    public static Statement statement;
    public static ResultSet result;

    /**
     * This method opens the connection between the application and Mysql database
     */
    public static void openConnection() {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            statement = connection.createStatement(); //create a statement
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /**
     * This method closes the connection between the application and the Mysql databse
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /**
     * A common used method that is used for classes in dao to query the tables from Database.
     *
     * @param sql a query
     * @return a raw result data
     */
    public ResultSet findRawDataFromDB(String sql) {
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

}
