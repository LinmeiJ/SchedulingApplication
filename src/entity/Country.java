package entity;

import java.sql.Timestamp;

/**
 * An entity class that maps the columns of the countries table
 *
 * @author Linmei M.
 */
public class Country {
    /**
     * A country ID that is generated in database
     */
    private long country_id;
    /**
     * A country name that is associated to the country ID
     */
    private String countryName;
    /**
     * The data time of this country ID that is created
     */
    private Timestamp create_date;
    /**
     * The person who created this country ID
     */
    private String created_by;
    /**
     * The date time of when the country was last updated
     */
    private Timestamp last_update;
    /**
     * The person who last updated this country
     */
    private String last_updated_by;
    /**
     * The count of total customer by country ID
     */
    private Long count;

    /**
     * A constructor for the country that takes in 6 instance variables
     *
     * @param country_id      the country ID
     * @param country         the country name
     * @param create_date     the created date
     * @param created_by      the one who created the country row
     * @param last_update     the last update date
     * @param last_updated_by the one who updated the country row
     */
    public Country(long country_id, String country, Timestamp create_date, String created_by, Timestamp last_update, String last_updated_by) {
        this.country_id = country_id;
        this.countryName = country;
        this.create_date = create_date;
        this.created_by = created_by;
        this.last_update = last_update;
        this.last_updated_by = last_updated_by;
    }

    /**
     * A default constructor for the country
     */
    public Country() {

    }

    /**
     * A setter for the country instance member
     *
     * @param countryName country name
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * A setter for the country instance member
     *
     * @return country name
     */
    public String getCountryName() {
        return this.countryName;
    }

    /**
     * A getter that gets the total count grouped by country name
     *
     * @return total count
     */
    public Long getCount() {
        return count;
    }

    /**
     * A setter for the count customers by the country
     *
     * @param count the total count
     */
    public void setCount(Long count) {
        this.count = count;
    }
}
