package dao;

import java.sql.SQLException;

/**
 * A contact that most of the dao class needs to implement them
 *
 * @param <T> any entity types
 * @author Linmei M.
 */
public interface ServiceIfc<T> {
    /**
     * an abstract method of update
     * @param obj any entity object
     */
    void update(T obj);

    /**
     * an abstract method of delete
     * @param obj any entity object
     */
    void delete(T obj);

    /**
     * an abstract method of save
     * @param obj any entity object
     */
    void save(T obj);
}
