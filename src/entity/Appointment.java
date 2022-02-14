package entity;

import java.sql.Timestamp;

/**
 * An entity class that maps the columns of the appointments table
 *
 * @author Linmei M.
 */
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
    private String contact_name;

    /**
     * A default contractor
     */
    public Appointment() {
    }

    /**
     * A constructor accepts 10 different instance variables
     *
     * @param appointment_id an appointment ID
     * @param title          an appointment title
     * @param description    an appointment description
     * @param location       an appointment location
     * @param type           an appointment type
     * @param start          an appointment start time
     * @param end            an appointment end time
     * @param customer_id    an customer id for this appointment
     * @param contact_id     an contact id for this appointment
     * @param user_id        a user id who created this appointment
     */
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

    /**
     * A contactor that accepts all instance variables besides the appointment ID
     *
     * @param title           an appointment title
     * @param description     an appointment description
     * @param location        an appointment location
     * @param type            an appointment type
     * @param start           an appointment start time
     * @param end             an appointment end time
     * @param customer_id     an customer id for this appointment
     * @param contact_id      an contact id for this appointment
     * @param user_id         a user id who created this appointment
     * @param created_date    an appointment created date
     * @param created_by      the user who created the appointment
     * @param last_update     mark the last update date time
     * @param last_updated_by mark who updated last time
     */
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

    /**
     * A constructor that accepts 3 instance variables
     *
     * @param aptId         an appointment ID
     * @param startDateTime an appointment start date time
     * @param endDateTime   an appointment end data time
     */
    public Appointment(long aptId, Timestamp startDateTime, Timestamp endDateTime) {
        this.appointment_id = aptId;
        this.start = startDateTime;
        this.end = endDateTime;
    }

    /**
     * A constructor that accepts 7 instance variables
     *
     * @param title      an appointment title
     * @param location   an appointment location
     * @param type       an appointment type
     * @param start      an appointment start time
     * @param end        an appointment end time
     * @param customerId a customer who made this appointment
     * @param aptId an appointment ID
     * @param description an appointment description
     */
    public Appointment(long aptId, String title, String location, String description, String type, Timestamp start, Timestamp end, long customerId) {
        this.appointment_id = aptId;
        this.title = title;
        this.location = location;
        this.type = type;
        this.description = description;
        this.start = start;
        this.end = end;
        this.customer_id = customerId;
    }

    /**
     * A constructor that accepts 15 instance variables
     *
     * @param appointment_id an appointment ID
     * @param title an appointment title
     * @param description an appointment description
     * @param location an appointment location
     * @param type an appointment type
     * @param start an appointment start date time
     * @param end an appointment end date time
     * @param created_date an appointment created date
     * @param created_by the user who created the appointment
     * @param last_update the date of this appointment last updated
     * @param last_updated_by the user who last updated this appointment
     * @param customer_id a customer ID
     * @param contact_id a contact ID
     * @param user_id a user ID
     */
    public Appointment(long appointment_id, String title, String description, String location, String type, Timestamp start, Timestamp end, Timestamp created_date, String created_by, Timestamp last_update, String last_updated_by, long customer_id, long contact_id, long user_id) {
        this.appointment_id = appointment_id;
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

    public Appointment(long aptId, String title, String description, String location, String type, Timestamp startDateTime, Timestamp endDateTime, long customerId, String nameByID, long userId) {
        this.appointment_id = aptId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = startDateTime;
        this.end = endDateTime;
        this.customer_id = customerId;
        this.contact_name = nameByID;
        this.user_id = userId;
    }

    /**
     * A getter for the title
     *
     * @return a title of the appointment
     */
    public String getTitle() {
        return title;
    }

    /**
     * A setter for the title
     *
     * @param title title of the appointment
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * A getter for the description
     *
     * @return a description of the appointment
     */
    public String getDescription() {
        return description;
    }

    /**
     * A setter for the description
     *
     * @param description the description of the appointment
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * A getter for the location
     *
     * @return a location of the appointment
     */
    public String getLocation() {
        return location;
    }

    /**
     * A setter for the location
     *
     * @param location the location of the appointment
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * A getter for the type
     *
     * @return a type of the appointment
     */
    public String getType() {
        return type;
    }

    /**
     * A setter for the type
     *
     * @param type the type of the appointment
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * A getter for the start date time
     *
     * @return a start date time of the appointment
     */
    public Timestamp getStart() {
        return start;
    }

    /**
     * A setter for the start time
     *
     * @param start the start time of the appointment
     */
    public void setStart(Timestamp start) {
        this.start = start;
    }

    /**
     * A getter for the end date time
     *
     * @return a end date time of the appointment
     */
    public Timestamp getEnd() {
        return end;
    }

    /**
     * A setter for the end time
     *
     * @param end end time of the appointment
     */
    public void setEnd(Timestamp end) {
        this.end = end;
    }

    /**
     * A getter for the created date
     *
     * @return a created date of the appointment
     */
    public Timestamp getCreated_date() {
        return Created_date;
    }

    /**
     * A setter for the created date
     *
     * @param created_date the created date of the appointment
     */
    public void setCreated_date(Timestamp created_date) {
        Created_date = created_date;
    }

    /**
     * A getter for the created by
     *
     * @return a created by of the appointment
     */
    public String getCreated_by() {
        return Created_by;
    }

    /**
     * A setter for the created by
     *
     * @param created_by the user who created the appointment
     */
    public void setCreated_by(String created_by) {
        Created_by = created_by;
    }

    /**
     * A getter for the last update
     *
     * @return a last update of the appointment
     */
    public Timestamp getLast_update() {
        return last_update;
    }

    /**
     * A setter for the last update
     *
     * @param last_update last update of the appointment
     */
    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }

    /**
     * A getter for the last updated by
     *
     * @return a last updated by of the appointment
     */
    public String getLast_updated_by() {
        return last_updated_by;
    }

    /**
     * A setter for the last updated by
     *
     * @param last_updated_by last updated by of the appointment
     */
    public void setLast_updated_by(String last_updated_by) {
        this.last_updated_by = last_updated_by;
    }

    /**
     * A getter for the customer ID
     *
     * @return a customer ID of the appointment
     */
    public long getCustomer_id() {
        return customer_id;
    }

    /**
     * A setter for the customer ID
     *
     * @param customer_id customer ID of the appointment
     */
    public void setCustomer_id(long customer_id) {
        this.customer_id = customer_id;
    }

    /**
     * A getter for the contact ID
     *
     * @return a contact ID of the appointment
     */
    public long getContact_id() {
        return contact_id;
    }

    /**
     * A setter for the contact ID
     *
     * @param contact_id contact ID of the appointment
     */
    public void setContact_id(long contact_id) {
        this.contact_id = contact_id;
    }

    /**
     * A getter for the user ID
     *
     * @return a user ID of the appointment
     */
    public long getUser_id() {
        return user_id;
    }

    /**
     * A setter for the user ID
     *
     * @param user_id user ID  of the appointment
     */
    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    /**
     * A getter for the appointment ID
     *
     * @return a appointment ID of the appointment
     */
    public long getAppointment_id() {
        return appointment_id;
    }

    /**
     * A getter for getting the contact name
     * @return a contact name
     */
    public String getContact_name() {
        return contact_name;
    }

    /**
     * A setter that sets a contact name
     * @param contact a contact name
     */
    public void setContact(String contact) {
        this.contact_name = contact;
    }
}
