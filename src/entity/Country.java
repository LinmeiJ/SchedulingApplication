package entity;

import java.sql.Timestamp;

/**
 * An entity class for the country table
 *
 * @Author Linmei M.
 */
public class Country {
    private long country_id;
    private String country;
    private Timestamp create_date;
    private String created_by;
    private Timestamp last_update;
    private String last_updated_by;
    private Long count;

//    /**
//     * A constructor for the country that takes in 6 instance variables
//     * @param country_id the country ID
//     * @param country the country name
//     * @param create_date the created date
//     * @param created_by the one who created the country row
//     * @param last_update the last update date
//     * @param last_updated_by the one who updated the country row
//     */
//    public Country(long country_id, String country, Timestamp create_date, String created_by, Timestamp last_update, String last_updated_by) {
//        this.country_id = country_id;
//        this.country = country;
//        this.create_date = create_date;
//        this.created_by = created_by;
//        this.last_update = last_update;
//        this.last_updated_by = last_updated_by;
//    }

    /**
     * A default constructor for the country
     */
    public Country() {

    }
//
//    public long getCountry_id() {
//        return country_id;
//    }
//
//    public String getCountry() {
//        return country;
//    }

    /**
     * A setter for the country instance member
     * @param country country name
     */
    public void setCountry(String country) {
        this.country = country;
    }

//    public Timestamp getCreate_date() {
//        return create_date;
//    }
//
//    public void setCreate_date(Timestamp create_date) {
//        this.create_date = create_date;
//    }
//
//    public String getCreated_by() {
//        return created_by;
//    }
//
//    public void setCreated_by(String created_by) {
//        this.created_by = created_by;
//    }
//
//    public Timestamp getLast_update() {
//        return last_update;
//    }
//
//    public void setLast_update(Timestamp last_update) {
//        this.last_update = last_update;
//    }
//
//    public String getLast_updated_by() {
//        return last_updated_by;
//    }
//
//    public void setLast_updated_by(String last_updated_by) {
//        this.last_updated_by = last_updated_by;
//    }
//
//    public void setCountry_id(long country_id) {
//        this.country_id = country_id;
//    }
//
//    public Long getCount() {
//        return count;
//    }

    /**
     * A setter for the count customers by the country
     * @param count
     */
    public void setCount(Long count) {
        this.count = count;
    }
}
