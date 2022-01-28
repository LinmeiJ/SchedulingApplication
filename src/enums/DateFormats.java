package enums;

public enum DateFormats {
    SQL_TIMESTAMP("yyyy-MM-dd HH:mm aa"),
//    HEADER_TIMESTAMP("yyyy-MM-dd'T'HH:mm:ss.SSSZ"),
//    TIMESTAMP("yyyy-MM-dd HH:mm:ss.S z"),
//    DATE_TIMESTAMP("yyyy-MM-dd"),
//    LOCAL_TIMESTAMP("yyyy-MM-dd'T'HH:mm:ss");
    SIMPLE_DATE_FORMAT("MM/dd/yyy HH:mm:ss a");

    private String dateFormat;

    DateFormats(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getDateFormat() {
        return dateFormat;
    }
}
