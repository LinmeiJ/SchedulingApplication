package enums;

public enum CountryId {

    US(1),
    UK(2),
    CANADA(3);

    private final int id;

    CountryId(final int id) { this.id = id; }

    public int getId() { return id; }

}
