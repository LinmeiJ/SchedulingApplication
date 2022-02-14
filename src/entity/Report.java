package entity;

/**
 * A class that created just for generating reports.
 *
 * @author Linmei M.
 */
public class Report {
    private String month;
    private String type;
    private String country;
    private int count;
    private int ctrCount;

    /**
     * A default constructor
     */
    public Report() {
    }

    /**
     * A constructor that accepts 2 instance variables
     *
     * @param type  a type of the appointment
     * @param count a total count of the types of appointments
     */
    public Report(String type, int count) {
        this.type = type;
        this.count = count;
    }

    /**
     * A getter that gets the month
     *
     * @return a name of the month
     */
    public String getMonth() {
        return month;
    }

    /**
     * A setter that sets the month
     *
     * @param month a name of the month
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * A getter that gets the type
     *
     * @return a type of appointment
     */
    public String getType() {
        return type;
    }

    /**
     * A setter that sets the type
     *
     * @param type a type of appointment
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * A getter to get the total a count
     *
     * @return a total count
     */
    public int getCount() {
        return count;
    }

    /**
     * A setter that sets the total count
     *
     * @param count the total count
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * A getter that gets the country name
     *
     * @return a country name
     */
    public String getCountry() {
        return country;
    }

    /**
     * A setter that sets the country name
     *
     * @param country a country name
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * A getter that gets the country count
     *
     * @return total count
     */
    public int getCtrCount() {
        return ctrCount;
    }

    /**
     * A setter that sets the count by country
     *
     * @param ctrCount a total count by country
     */
    public void setCtrCount(int ctrCount) {
        this.ctrCount = ctrCount;
    }

}
