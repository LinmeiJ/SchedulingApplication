package entities;

import java.sql.Date;
import java.sql.Timestamp;

public class Appointments {
   private int appointment_id;
   private String title;
   private String description;
   private String location;
   private String type;
   private Date start;
   private Date end;
   private Date Created_date;
   private String Created_by;
   private Timestamp last_update;
   private String last_updated_by;
   private int customer_id; // this is a fk


    public int getAppointment_id() {
        return appointment_id;
    }

    public String getTitle() {
        return title;
    }

    /**
     * f
     *
     * @param title the title for the appointment
     *
     * */
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

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Date getCreated_date() {
        return Created_date;
    }

    public void setCreated_date(Date created_date) {
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

    public int getCustomer_id() {
        return customer_id;
    }
}
