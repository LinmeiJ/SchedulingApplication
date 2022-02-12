package entity;

/**
 * An entity that maps the contacts table columns
 */
public class Contact {
    private int contact_id;
    private String contact_name;
    private String email;

    /**
     *  A getter of contact ID
     * @return A contact ID of the contact
     */
    public int getContact_id() {
        return contact_id;
    }

    /**
     *  A getter of contact name
     * @return A contact name of the contact
     */
    public String getContact_name() {
        return contact_name;
    }

    /**
     *  A setter of the contact name
     * @param contact_name name of the contact
     */
    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    /**
     *  A getter of email
     * @return A email of the contact
     */
    public String getEmail() {
        return email;
    }

    /**
     *  A setter of the email
     * @param email email address
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
