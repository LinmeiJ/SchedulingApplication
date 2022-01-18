package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

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

    public static void openConnection()
    {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
//            System.out.println(connection + "Connection successful!");
            statement = connection.createStatement(); //create a statement
//            String sql = "select * from users";
//           ResultSet rs =  statement.executeQuery(sql);
//            while(rs.next()){
//                System.out.println(rs.getInt("User_id") + " "
//                        +rs.getString("User_Name") + " "
//                        + rs.getString("Password") + " "
//                        + rs.getString("Create_Date") + " "
//                        + rs.getString("Created_By") + " "
//                        + rs.getString("Last_Update") + " "
//                        + rs.getString("Last_Updated_By")+" ");
//
//            }
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }

    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }
}
