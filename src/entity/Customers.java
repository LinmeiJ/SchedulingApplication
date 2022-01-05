package entity;

import java.sql.Date;
import java.sql.Timestamp;

public class Customers {
    int customer_id;
    String customer_name;
    String address;
    String postal_code;
    String phone;
    Date created_date;
    String created_by;
    Timestamp last_update;
    String last_updated_by;
    int division_id;

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }
}
