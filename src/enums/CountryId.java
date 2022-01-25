package enums;

public enum CountryId {

    US(1),
    UK(2),
    CANADA(3);

    private final long id;

    CountryId(final long id) { this.id = id; }

    public long getId() { return id; }

}
