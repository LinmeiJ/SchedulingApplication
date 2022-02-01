package Dao;

import dbConnection.JDBCConnection;
import entity.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDaoImpl extends JDBCConnection implements ServiceIfc<Customer> {
    FirstLevelDivisionDaoImpl firstLevelDivisionDao = new FirstLevelDivisionDaoImpl();
    public   Customer customer;
    private  ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    public ObservableList<Customer> findAll() throws SQLException {
        ResultSet rs = findRawDataFromDB("SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, d.Division_ID, d.Country_ID FROM customers c JOIN first_level_divisions d ON d.Division_ID = c.Division_ID");
        while(rs.next()){
            long id = rs.getLong("customer_id");
            String name = rs.getString("customer_name");
            String address =  rs.getString("address");
            String postalCode =  rs.getString("Postal_Code");
            String phoneNum = rs.getString("Phone");
            long div_id = rs.getLong("Division_ID");
            customer = new Customer(id, name, address, postalCode, phoneNum, firstLevelDivisionDao.findById(div_id));
            allCustomers.add(customer);
        }
        return allCustomers;
    }

    @Override
    public Customer findById(long id) {
        return null;
    }

    @Override
    public void delete(Customer customer) {
        try {
            statement = connection.createStatement();
            String sql = "DELETE FROM customers WHERE Customer_ID = "+ customer.getCustomer_id();
            statement.executeUpdate(sql);
            allCustomers.remove(customer);
            Validator.displayDeleteConfirmation();
            Validator.displaySuccess("Delete");
        } catch (SQLException e) {
            System.out.println("something wrong with executing delete sql");
            e.printStackTrace();
        }
    }

    @Override
    public void save(Customer customer) throws SQLException {
        String sql = "INSERT INTO customers VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?,?)";
        PreparedStatement preparedStatement = JDBCConnection.connection.prepareStatement(sql);
        preparedStatement.setString(1, customer.getCustomer_name());
        preparedStatement.setString(2, customer.getAddress());
        preparedStatement.setString(3, customer.getPostal_code());
        preparedStatement.setString(4, customer.getPhone());
        preparedStatement.setTimestamp(5, customer.getCreate_date());
        preparedStatement.setString(6,customer.getCreated_by());
        preparedStatement.setTimestamp(7, customer.getLast_update());
        preparedStatement.setString(8, customer.getLast_updated_by());
        preparedStatement.setLong(9, customer.getDivision_id());

        preparedStatement.execute();
    }

    @Override
    public void update(Customer customer) {
        String sql = "UPDATE customers SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Last_Update=?, Last_Updated_By=?, Division_ID=? WHERE Customer_ID = " + customer.getCustomer_id();
        try {
            PreparedStatement preparedStatement = JDBCConnection.connection.prepareStatement(sql);
            preparedStatement.setString(1, customer.getCustomer_name());
            preparedStatement.setString(2, customer.getAddress());
            preparedStatement.setString(3, customer.getPostal_code());
            preparedStatement.setString(4, customer.getPhone());
            preparedStatement.setString(5, String.valueOf(customer.getLast_update()));
            preparedStatement.setString(6, customer.getLast_updated_by());
            preparedStatement.setString(7, String.valueOf(customer.getDivision_id()));

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void findIdByNameAndDivisionId(String customer_name, long division_id) {
        ResultSet rs = findRawDataFromDB("SELECT Customer_ID FROM customer_name = '" + );
    }
}




