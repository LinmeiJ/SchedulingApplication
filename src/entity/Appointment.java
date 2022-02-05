package entity;

import java.sql.Date;
import java.sql.Timestamp;

public class Appointment {
    private long appointment_id;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp start;
    private Timestamp end;
    private Timestamp Created_date;
    private String Created_by;
    private Timestamp last_update;
    private String last_updated_by;
    private long customer_id;
    private long contact_id;
    private long user_id;

    public Appointment(){};

    public Appointment(long appointment_id, String title, String description, String location, String type, Timestamp start, Timestamp end, long customer_id, long contact_id, long user_id) {
        this.appointment_id = appointment_id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customer_id = customer_id;
        this.contact_id = contact_id;
        this.user_id = user_id;
    }

    public Appointment(String title, String description, String location, String type, Timestamp start, Timestamp end, Timestamp created_date, String created_by, Timestamp last_update, String last_updated_by, long customer_id, long contact_id, long user_id) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        Created_date = created_date;
        Created_by = created_by;
        this.last_update = last_update;
        this.last_updated_by = last_updated_by;
        this.customer_id = customer_id;
        this.contact_id = contact_id;
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }
    public long getAppointment_id() {
        return appointment_id;
    }

    /**
     * @param title the title for the appointment
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public Timestamp getCreated_date() {
        return Created_date;
    }

    public void setCreated_date(Timestamp created_date) {
        Created_date = created_date;
    }

    public String getCreated_by() {
        return Created_by;
    }

    public void setCreated_by(String created_by) {
        Created_by = created_by;
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

    public long getCustomer_id() {
        return customer_id;
    }

    public long getUser_id() {
        return user_id;
    }
    public long getContact_id() {
        return contact_id;
    }

    public void setContact_id(long contact_id) {
        this.contact_id = contact_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public void setCustomer_id(long customer_id) {
        this.customer_id = customer_id;
    }

}
