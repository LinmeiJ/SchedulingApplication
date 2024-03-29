package dao;

import controller.CustomerRecordController;
import dbConnection.JDBCConnection;
import entity.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class contains logics for the data flow between the controller and the customers table in the database.
 *
 * @author Linmei M.
 */
public class CustomerDaoImpl extends JDBCConnection implements ServiceIfc<Customer> {
    /**
     * Initialize a first level division dao object
     */
    private final FirstLevelDivisionDaoImpl firstLevelDivisionDao = new FirstLevelDivisionDaoImpl();
    /**
     * Initialize an appointment dao object
     */
    private final AppointmentDaoImpl aptDao = new AppointmentDaoImpl();
    /**
     * initialize a list that can contain Customer objects
     */
    private final ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    /**
     * This method finds all customers from the customers table in database.
     *
     * @return a list of all customers
     */
    public ObservableList<Customer> findAll() {
        ResultSet rs = findRawDataFromDB("SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, d.Division_ID, d.Country_ID FROM customers c JOIN first_level_divisions d ON d.Division_ID = c.Division_ID");

        try {
            while (rs.next()) {
                int id = rs.getInt("customer_id");
                String name = rs.getString("customer_name");
                String address = rs.getString("address");
                String postalCode = rs.getString("Postal_Code");
                String phoneNum = rs.getString("Phone");
                long div_id = rs.getLong("Division_ID");
                Customer customer = new Customer(id, name, address, postalCode, phoneNum, firstLevelDivisionDao.findById(div_id));
                allCustomers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCustomers;
    }

    /**
     * This method deletes a customer record from the customers table
     *
     * @param customer a customer that wishes to be deleted
     */
    public void delete(Customer customer) {
        try {
            aptDao.deleteByCustID(customer.getCustomer_id());
            statement = connection.createStatement();
            String sql = "DELETE FROM customers WHERE Customer_ID = " + customer.getCustomer_id();
            statement.executeUpdate(sql);
//            allCustomers.remove(customer);
//            Validator.displayDeleteConfirmation( "the Customer ID " + customer.getCustomer_id() + "and name is " + customer.getCustomer_name());
//            Validator.displaySuccess("Delete");
        } catch (SQLException e) {
            System.out.println("something wrong with executing delete sql");
            e.printStackTrace();
        }
    }

    /**
     * This method saves a new customer to the database
     *
     * @param customer a new customer info
     */
    public void save(Customer customer) {
        String sql = "INSERT INTO customers VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?,?)";
        try {
            PreparedStatement preparedStatement = JDBCConnection.connection.prepareStatement(sql);
            preparedStatement.setString(1, customer.getCustomer_name());
            preparedStatement.setString(2, customer.getAddress());
            preparedStatement.setString(3, customer.getPostal_code());
            preparedStatement.setString(4, customer.getPhone());
            preparedStatement.setTimestamp(5, customer.getCreate_date());
            preparedStatement.setString(6, customer.getCreated_by());
            preparedStatement.setTimestamp(7, customer.getLast_update());
            preparedStatement.setString(8, customer.getLast_updated_by());
            preparedStatement.setLong(9, customer.getDivision_id());

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method updates an existing customer and save it to the database
     *
     * @param customer an existing customer
     */
    public void update(Customer customer) {
        String sql = "UPDATE customers SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Last_Update=?, Last_Updated_By=?, Create_Date=?, Created_By=?, Division_ID=? WHERE Customer_ID = " + CustomerRecordController.selectedCust.getCustomer_id();
        try {
            PreparedStatement preparedStatement = JDBCConnection.connection.prepareStatement(sql);
            preparedStatement.setString(1, customer.getCustomer_name());
            preparedStatement.setString(2, customer.getAddress());
            preparedStatement.setString(3, customer.getPostal_code());
            preparedStatement.setString(4, customer.getPhone());
            preparedStatement.setTimestamp(5, customer.getLast_update());
            preparedStatement.setString(6, customer.getLast_updated_by());
            preparedStatement.setTimestamp(7, customer.getCreate_date());
            preparedStatement.setString(8, customer.getCreated_by());
            preparedStatement.setString(9, String.valueOf(customer.getDivision_id()));

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
//
//    /**
//     * This method finds an customer ID by customer name and a division ID
//     *
//     * @param customerName a customer name
//     * @param divisionId a division ID
//     * @return a customer ID
//     */
//    public long findIdByNameAndDivisionId(String customerName, long divisionId) {
//        ResultSet rs = findRawDataFromDB("SELECT Customer_ID FROM customers WHERE customer_name = '" + customerName + "' and division_id = " + divisionId);
//        long id = 0;
//        try {
//            while (rs.next()) {
//                id = rs.getLong("customer_id");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return id;
//    }
}




