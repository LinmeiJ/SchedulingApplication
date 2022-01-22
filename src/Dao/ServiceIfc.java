package Dao;

import entity.Customer;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ServiceIfc<T> {
    ObservableList<T> findAll() throws SQLException;
    T findById(long id);
    void update(T obj);
    void delete(long id) throws SQLException;
    void save(T obj) throws SQLException;
}
