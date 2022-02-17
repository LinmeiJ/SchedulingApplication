package entity;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * An entity class that maps the columns of the users table
 *
 * @author Linmei M.
 */
public class User {
    private int user_id;
    private String user_name;
    private String password;
    private Date created_date;
    private String created_by;
    private Timestamp last_update;
    private String last_updated_by;

    public User() {
    }

    public User(int user_id, String user_name, String password) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.password = password;
    }

    /**
     * A getter that get a user ID
     *
     * @return a user ID
     */
    public int getUser_id() {
        return user_id;
    }

    /**
     * A getter that gets a user name
     *
     * @return a user name
     */
    public String getUser_name() {
        return user_name;
    }

    /**
     * A setter that sets a user name
     *
     * @param user_name a user name
     */
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    /**
     * A getter that gets a password
     *
     * @return a password
     */
    public String getPassword() {
        return password;
    }

    /**
     * A setter that sets a password
     *
     * @param password a password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * A getter that gets the data of when the user is created.
     *
     * @return a date of when the user is created
     */
    public Date getCreated_date() {
        return created_date;
    }

    /**
     * A setter that sets the created date
     *
     * @param created_date a created date
     */
    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    /**
     * A getter that gets the name of who created the user
     *
     * @return a name
     */
    public String getCreated_by() {
        return created_by;
    }

    /**
     * A setter that sets the name of who created the user
     *
     * @param created_by a name
     */
    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    /**
     * A getter that gets the data time of last time update
     *
     * @return a date time
     */
    public Timestamp getLast_update() {
        return last_update;
    }

    /**
     * A setter that sets the time of last time update
     *
     * @param last_update a date time
     */
    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }

    /**
     * A getter that gets the name of last updated by
     *
     * @return a name
     */
    public String getLast_updated_by() {
        return last_updated_by;
    }

    /**
     * A setter that sets the name of who last updated the user
     *
     * @param last_updated_by the name of last updated by
     */
    public void setLast_updated_by(String last_updated_by) {
        this.last_updated_by = last_updated_by;
    }
}