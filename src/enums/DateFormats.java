package enums;

public enum DateFormats {
    SQL_TIMESTAMP("yyyy-MM-dd HH:mm:ss"),
//    TRE_TRANSACTION_HEADER_TIMESTAMP("yyyy-MM-dd'T'HH:mm:ss.SSSZ"),
//    MICROSERVICE_RESPONSE_TRANSACTION_TIMESTAMP("yyyy-MM-dd HH:mm:ss.S z"),
//    RECEIPT_DATE_TIMESTAMP("yyyy-MM-dd"),
    LOCAL_TIMESTAMP("yyyy-MM-dd'T'HH:mm:ss");

    private String dateFormat;

    DateFormats(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getDateFormat() {
        return dateFormat;
    }
}
