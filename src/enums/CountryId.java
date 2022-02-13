package enums;

/**
 * There are 3 locations for this business owner - the U.S, the Canada, and the U.K.
 *
 * @author Linmei M.
 */
public enum CountryId {
    US(1),
    UK(2),
    CANADA(3);

    private final long id;

    /**
     * a constructor for this enum class
     *
     * @param id the country ID
     */
    CountryId(final long id) {
        this.id = id;
    }

    /**
     * a getter for retrieving the country ID.
     *
     * @return a country ID
     */
    public long getId() {
        return id;
    }

}
