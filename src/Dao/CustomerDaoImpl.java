package Dao;

import dbConnection.JDBCConnection;
import entity.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDaoImpl extends JDBCConnection implements ServiceIfc<Customer> {
    FirstLevelDivisionDaoImpl firstLevelDivisionDao = new FirstLevelDivisionDaoImpl();
    CountryDaoImpl countryDao = new CountryDaoImpl();

    @Override
    public ResultSet findAll() {
        try {
            statement = connection.createStatement();
            String sql = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, d.Division_ID, d.Country_ID FROM customers c JOIN first_level_divisions d ON d.Division_ID = c.Division_ID";
            result = statement.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public ObservableList<Customer> getAllCustomers() throws SQLException {
        ObservableList<Customer> allCustomers= FXCollections.observableArrayList();
        ResultSet rs = findAll();
        while(rs.next()){
            long id = rs.getLong("customer_id");
            String name = rs.getString("customer_name");
            String address =  rs.getString("address");
            String postalCode =  rs.getString("Postal_Code");
            String phoneNum = rs.getString("Phone");
            long div_id = rs.getLong("Division_ID");

            Customer customer = new Customer(id, name, address, postalCode, phoneNum, firstLevelDivisionDao.findById(div_id));
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

    }

    @Override
    public void save(Customer customer) {

    }

    @Override
    public void update(Customer customer) {

    }

}
