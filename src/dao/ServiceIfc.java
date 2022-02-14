package dao;

import java.sql.SQLException;

/**
 * A contact that most of the dao class needs to implement them
 *
 * @param <T> any entity types
 * @author Linmei M.
 */
public interface ServiceIfc<T> {
    void update(T obj) throws SQLException;

    void delete(T obj);

    void save(T obj) throws SQLException;
}
