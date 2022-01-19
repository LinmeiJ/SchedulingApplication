package Dao;

import java.sql.ResultSet;

public interface ServiceIfc<T> {
    ResultSet findAll();
    T findById(long id);
    void update(T obj);
    void delete(T obj);
    void save(T obj);
}
