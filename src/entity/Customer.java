package entity;

import Dao.CustomerDaoImpl;
import javafx.scene.control.Button;

import java.sql.Timestamp;

public class Customer {
    private long customer_id;
    private String customer_name;
    private String address;
    private String postal_code;
    private String phone;
    private Timestamp create_date;
    private String created_by;
    private Timestamp last_update;
    private String last_updated_by;
    private long division_id;
    private Button delete;
    private Button update;

    private FirstLevelDivision firstLevelDivision;//imperfect way to display the division on the record table

    public Customer(){}
    public Customer(long customer_id, String customer_name, String address, String postal_code, String phone,FirstLevelDivision firstLevelDivision) {
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.address = address;
        this.postal_code = postal_code;
        this.phone = phone;
        this.firstLevelDivision = firstLevelDivision;
    }


    public Customer(long customer_id, String customer_name, String address, String postal_code, String phone, FirstLevelDivision firstLevelDivision, Button delete, Button update) {
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.address = address;
        this.postal_code = postal_code;
        this.phone = phone;
        this.firstLevelDivision = firstLevelDivision;
        this.delete = delete;
        this.update = update;
        delete.setOnAction(e -> {
            CustomerDaoImpl customerDao = new CustomerDaoImpl();
            customerDao.delete(this.customer_id);
        });
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

    public Timestamp getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Timestamp create_date) {
        this.create_date = create_date;
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
    public long getDivision_id() {
        return division_id;
    }

    public void setDivision_id(long division_id) {
        this.division_id = division_id;
    }

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }

    public Button getUpdate() {
        return update;
    }
    public void setUpdate(Button update) {
        this.update = update;
    }


}
