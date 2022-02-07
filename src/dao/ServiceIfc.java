package dao;

import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface ServiceIfc<T> {
    ObservableList<T> findAll() throws SQLException;
    T findById(long id);
    void update(T obj);
    void delete(T obj) throws SQLException;
    void save(T obj) throws SQLException;
}
