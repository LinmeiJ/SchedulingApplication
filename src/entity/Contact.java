package entity;

/**
 * An entity class that maps the columns of the contacts table
 *
 * @author Linmei M.
 */
public class Contact {
    /**
     * A contact ID that is getting from the DB
     */
    private int contact_id;
    /**
     * An contact name associated to the contact ID
     */
    private String contact_name;
    /**
     * An email that associated to the contact ID
     */
    private String email;

    /**
     * A constructor that receives 3 params
     * @param contact_id contact ID
     * @param contact_name contact name
     * @param email  contact email address
     */
    public Contact(int contact_id, String contact_name, String email) {
        this.contact_id = contact_id;
        this.contact_name = contact_name;
        this.email = email;
    }

    /**
     * A getter of contact ID
     *
     * @return A contact ID of the contact
     */
    public int getContact_id() {
        return contact_id;
    }

    /**
     * A getter of contact name
     *
     * @return A contact name of the contact
     */
    public String getContact_name() {
        return contact_name;
    }

    /**
     * A setter of the contact name
     *
     * @param contact_name name of the contact
     */
    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    /**
     * A getter of email
     *
     * @return A email of the contact
     */
    public String getEmail() {
        return email;
    }

    /**
     * A setter of the email
     *
     * @param email email address
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
