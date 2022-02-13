package entity;

import java.sql.Timestamp;

/**
 * An entity class tht maps the fist level division table from the database
 *
 * @author  Linmei M.
 */
public class FirstLevelDivision {
    private long division_id;
    private String division;
    private Timestamp create_date;
    private String created_by;
    private Timestamp last_update;
    private String last_updated_by;
    private long country_id;

    /**
     * A constructor that accepts 6 instance variables
     * @param division_id a division id
     * @param division a division name
     * @param create_date a created date time
     * @param created_by the name of who created the info
     * @param last_update the time last gets updated
     * @param last_updated_by the name of who updated the info
     * @param country a country name
     */
    public FirstLevelDivision(long division_id, String division, Timestamp create_date, String created_by, Timestamp last_update, String last_updated_by, long country) {
        this.division_id = division_id;
        this.division = division;
        this.create_date = create_date;
        this.created_by = created_by;
        this.last_update = last_update;
        this.last_updated_by = last_updated_by;
        this.country_id = country;
    }

    /**
     * A getter that gets the country ID
     * @return a country ID
     */
    public long getCountry_id() {
        return country_id;
    }

    /**
     * A setter that sets a country ID
     * @param country_id a country ID
     */
    public void setCountry_id(long country_id) {
        this.country_id = country_id;
    }

    /**
     * A setter that set the division ID
     * @param division_id a division ID
     */
    public void setDivision_id(long division_id) {
        this.division_id = division_id;
    }

    /**
     * A getter that get a division ID
     * @return  a division ID
     */
    public long getDivision_id() {
        return division_id;
    }

    /**
     * A getter that gets a division name
     * @return a division name
     */
    public String getDivision() {
        return division;
    }

    /**
     * A setter that sets a division name
     * @param division a division name
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * A getter that gets a date of creation
     * @return a data of creation
     */
    public Timestamp getCreate_date() {
        return create_date;
    }

    /**
     * A setter that sets he created date
     * @param create_date a create date
     */
    public void setCreate_date(Timestamp create_date) {
        this.create_date = create_date;
    }

    /**
     * A getter that gets the name of who created by
     * @return a name
     */
    public String getCreated_by() {
        return created_by;
    }

    /**
     * A setter that sets the name of who created by
     * @param created_by a name
     */
    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    /**
     * A getter that gets the last updated date time
     * @return a date time
     */
    public Timestamp getLast_update() {
        return last_update;
    }

    /**
     * A setter that sets the last updated date time
     * @param last_update a date time
     */
    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }

    /**
     * A getter that gets the name of who updated last
     * @return a name
     */
    public String getLast_updated_by() {
        return last_updated_by;
    }

    /**
     * A setter that sets the name of who updated last
     * @param last_updated_by a name
     */
    public void setLast_updated_by(String last_updated_by) {
        this.last_updated_by = last_updated_by;
    }
}
