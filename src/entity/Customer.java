package entity;

import java.sql.Timestamp;

/**
 * An entity class that maps the columns of the customers table
 *
 * @author Linmei M.
 */
public class Customer {
    /**
     * An unique customer ID that is generated in database
     */
    private int customer_id;
    /**
     * An customer name that is associated to the customer ID
     */
    private String customer_name;
    /**
     * An customer address
     */
    private String address;
    /**
     * An customer's postal code
     */
    private String postal_code;
    /**
     * An customer's phone number
     */
    private String phone;
    /**
     * The date of creating this customer info
     */
    private Timestamp create_date;
    /**
     * The person who created this customer info
     */
    private String created_by;
    /**
     * The date time of when the customer was last updated
     */
    private Timestamp last_update;
    /**
     * The person who did the last update
     */
    private String last_updated_by;
    /**
     * A division ID that represents the country in which the customer from
     */
    private long division_id;
    /**
     * The FirstLevelDivision table that is associated to the customer
     */
    private FirstLevelDivision firstLevelDivision;//imperfect way to display the division on the record table

    /**
     * Default customer constructor
     */
    public Customer() {
    }

    /**
     * A constructor that accepts 6 instance variables
     *
     * @param customer_id        a customer ID
     * @param customer_name      a customer name
     * @param address            the address of the customer
     * @param postal_code        the postal code of the customer
     * @param phone              the phone od the customer
     * @param firstLevelDivision the firstLevelDivision object
     */
    public Customer(int customer_id, String customer_name, String address, String postal_code, String phone, FirstLevelDivision firstLevelDivision) {
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.address = address;
        this.postal_code = postal_code;
        this.phone = phone;
        this.firstLevelDivision = firstLevelDivision;
    }

    /**
     * A getter that gets the customer ID
     *
     * @return a customer ID
     */
    public int getCustomer_id() {
        return customer_id;
    }

    /**
     * A getter that gets the customer name
     *
     * @return a customer name
     */
    public String getCustomer_name() {
        return customer_name;
    }

    /**
     * A setting that sets the customer name
     *
     * @param customer_name a customer name
     */
    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    /**
     * A getter that gets an address
     *
     * @return an address
     */
    public String getAddress() {
        return address;
    }

    /**
     * A setter that sets an address
     *
     * @param address an address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * A getter that gets a postal code
     *
     * @return a postal code
     */
    public String getPostal_code() {
        return postal_code;
    }

    /**
     * A setter that sets the postal code
     *
     * @param postal_code postal_code
     */
    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    /**
     * A getter that gets a phone number
     *
     * @return a phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * A setter that sets a phone number
     *
     * @param phone a phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * A getter that gets a created date
     *
     * @return a created date
     */
    public Timestamp getCreate_date() {
        return create_date;
    }

    /**
     * A setter that sets a created date
     *
     * @param create_date a created date
     */
    public void setCreate_date(Timestamp create_date) {
        this.create_date = create_date;
    }

    /**
     * A getter that gets a name of who created this customer info
     *
     * @return a name
     */
    public String getCreated_by() {
        return created_by;
    }

    /**
     * A setter that sets a name that who created this customer info
     *
     * @param created_by a name
     */
    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    /**
     * A getter that gets the last updated time
     *
     * @return a date time
     */
    public Timestamp getLast_update() {
        return last_update;
    }

    /**
     * A setter that sets the last updated date time
     *
     * @param last_update a date time
     */
    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }

    /**
     * A getter that gets the name of who last updated the customer info
     *
     * @return a name
     */
    public String getLast_updated_by() {
        return last_updated_by;
    }

    /**
     * A setter that sets the name of who last updated by
     *
     * @param last_updated_by a name
     */
    public void setLast_updated_by(String last_updated_by) {
        this.last_updated_by = last_updated_by;
    }

    /**
     * A getter that gets the firstLevelDivision object
     *
     * @return a FirstLevelDivision object
     */
    public FirstLevelDivision getFirstLevelDivision() {
        return firstLevelDivision;
    }

//    /**
//     * A setter that sets the FirstLevelDivision object
//     * @param firstLevelDivision a FirstLevelDivision object
//     */
//    public void setFirstLevelDivision(FirstLevelDivision firstLevelDivision) {
//        this.firstLevelDivision = firstLevelDivision;
//    }

    /**
     * A getter that gets the Division ID
     *
     * @return A division ID
     */
    public long getDivision_id() {
        return division_id;
    }

    /**
     * A setter that sets the division ID
     *
     * @param division_id a division ID
     */
    public void setDivision_id(long division_id) {
        this.division_id = division_id;
    }
}
