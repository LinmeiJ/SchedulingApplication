package Dao;

import entity.Country;
import entity.Customer;
import entity.FirstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CustomerDaoImpl extends JDBCConnection{
    FirstLevelDivisionDaoImpl firstLevelDivisionDao = new FirstLevelDivisionDaoImpl();
    CountryDaoImpl countryDao = new CountryDaoImpl();

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

    public  Customer findById(Long id) {
        return null;
    }

    public  void update(Customer customer) {

    }

    public  Long save(Customer customer) {
        return null;
    }

    public  void delete(Customer customer) {

    }
}
