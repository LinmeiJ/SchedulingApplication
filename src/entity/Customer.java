package entity;

import java.sql.Date;
import java.sql.Timestamp;

public class Customer {
    private long customer_id;
    private String customer_name;
    private String address;
    private String postal_code;
    private String phone;
    private Date created_date;
    private String created_by;
    private Timestamp last_update;
    private String last_updated_by;
    private FirstLevelDivision firstLevelDivision;

    public Customer(){}
    public Customer(long customer_id, String customer_name, String address, String postal_code, String phone,FirstLevelDivision firstLevelDivision) {
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.address = address;
        this.postal_code = postal_code;
        this.phone = phone;
        this.firstLevelDivision = firstLevelDivision;
    }

    public long getCustomer_id() {
        return customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public Timestamp getLast_update() {
        return last_update;
    }

    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }

    public String getLast_updated_by() {
        return last_updated_by;
    }

    public void setLast_updated_by(String last_updated_by) {
        this.last_updated_by = last_updated_by;
    }

    public FirstLevelDivision getFirstLevelDivision() {
        return firstLevelDivision;
    }

    public void setFirstLevelDivision(FirstLevelDivision firstLevelDivision) {
        this.firstLevelDivision = firstLevelDivision;
    }

}
