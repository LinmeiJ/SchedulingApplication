package dateTimeUtil;

import enums.TimeZoneOption;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;
import static java.time.temporal.ChronoUnit.MINUTES;

public class DateTimeConverter {
    public static  LocalDateTime TODAY = LocalDateTime.now();
    public static String FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static  DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(FORMAT);


    /**
     * This method gets a timezone based on user local setting
     * @return a timezone id
     */
    public static String getTimeZoneID() {
        TimeZone timeZone = TimeZone.getDefault();
        return timeZone.getID();
    }

    /**
     * This method converts local time to UTC time
     * @param localDateTime a time based on user local setting
     * @return a UTC time
     */
    public static Timestamp convertLocalTimeToUTC(LocalDateTime localDateTime) {
        return Timestamp.valueOf(FORMATTER.format(localDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of(TimeZoneOption.UTC.name()))));
    }

    /**
     * This method converts user wished appointment time to EST time
     * @param dateValue  the date entered by the user
     * @param hrValue the hour entered by the user
     * @param minuteValue the minute entered by the user
     * @return an EST time
     */
    public static Timestamp convertAptTimeToEST(LocalDate dateValue, String hrValue, String minuteValue) {
        String str = dateValue.toString() + " " + hrValue + ":" + minuteValue + ":00";
        LocalDateTime dateTime = LocalDateTime.parse(str, FORMATTER);
        return Timestamp.valueOf(FORMATTER.format(dateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of(String.valueOf(TimeZoneOption.EST), ZoneId.SHORT_IDS))));
    }

    /**
     * This method converts a user entered appointment time to UTC time
     * @param dateValue  the date entered by the user
     * @param hrValue the hour entered by the user
     * @param minuteValue the minute entered by the user
     * @return an UTC time
     */
    public static Timestamp convertAptTimeToUTC(LocalDate dateValue, String hrValue, String minuteValue) {
        String str = dateValue.toString() + " " + hrValue + ":" + minuteValue + ":00";

        LocalDateTime dateTime = LocalDateTime.parse(str, FORMATTER);
        return Timestamp.valueOf(FORMATTER.format(dateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of(TimeZoneOption.UTC.name()))));
    }

    /**
     * This method converts a UTC time to user local time
     * @param timestamp a UTC time
     * @return a user local time
     */
    public static Timestamp convertUTCToLocal(String timestamp) {
        Timestamp localDate = null;
        try {
            DateFormat currentTFormat = new SimpleDateFormat(FORMAT);
            currentTFormat.setTimeZone(TimeZone.getTimeZone(TimeZoneOption.UTC.name()));
            Date date = currentTFormat.parse(timestamp);
            currentTFormat.setTimeZone(TimeZone.getTimeZone(getTimeZoneID()));
            localDate = Timestamp.valueOf(currentTFormat.format(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return localDate;
    }

//    public static Timestamp convertESTToLocal(String timestamp) {
//        Timestamp localDate = null;
//        try {
//            DateFormat currentTFormat = new SimpleDateFormat(FORMAT);
//            currentTFormat.setTimeZone(TimeZone.getTimeZone(ZoneId.of(String.valueOf(TimeZoneOption.EST), ZoneId.SHORT_IDS)));
//            Date date = currentTFormat.parse(timestamp);
//            currentTFormat.setTimeZone(TimeZone.getTimeZone(getTimeZoneID()));
//            localDate = Timestamp.valueOf(currentTFormat.format(date));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return localDate;
//    }

    /**
     * This method converts a UTC time to an EST time
     * @param timestamp a UTC time
     * @return an EST time
     */
    public static Timestamp convertUTCToEST(Timestamp timestamp){
        Timestamp localDate = null;
        try {
            DateFormat timeFormat = new SimpleDateFormat(FORMAT);
            timeFormat.setTimeZone(TimeZone.getTimeZone(TimeZoneOption.UTC.name()));
            Date date = timeFormat.parse(String.valueOf(timestamp));
            timeFormat.setTimeZone(TimeZone.getTimeZone(ZoneId.of(String.valueOf(TimeZoneOption.EST), ZoneId.SHORT_IDS)));
            localDate = Timestamp.valueOf(timeFormat.format(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return localDate;
    }

    /**
     * This method finds the date of monday for the current week
     * @return the date of monday of the current week
     */
    public static LocalDate getMondayDate() {
        LocalDate monday = TODAY.toLocalDate();
        while (monday.getDayOfWeek() != DayOfWeek.MONDAY) {
            monday = monday.minusDays(1);
        }
        return monday;
    }

    /**
     * This method finds the date of sunday for the current week
     * @return the date of sunday of the current week
     */
    public static LocalDate getSundayDate() {
        LocalDate sunday = TODAY.toLocalDate();
        while (sunday.getDayOfWeek() != DayOfWeek.SUNDAY) {
            sunday = sunday.plusDays(1);
        }
        return sunday;
    }

    /**
     * This method convert hour to a string
     * @param hr an int type hour
     * @return a string type hour
     */
    public static String getHr(int hr){
        String hour = String.valueOf(hr);
        return hour.length() == 1 ? "0" + hour : hour;
    }

    /**
     * This method converts a minute to a string
     * @param m an int type minute
     * @return an string type minute
     */
    public static String getMint(int m){
        String minute = String.valueOf(m);
        return minute.length() == 1 ? "0" + minute : minute;
    }

    /**
     * This method find times that is 15 minutes far away from current local time to a booked appointment time.
     * Give 16 minutes check point instead of 15 minutes to avoid a difference in just few seconds between 15 and 16 minutes
     * @param timestamp a booked time
     * @return true if it is within 15 minutes, otherwise returns false.
     */
    public static boolean isWithin15mins(Timestamp timestamp){
        return (MINUTES.between(TODAY, timestamp.toLocalDateTime()) >= 0 && MINUTES.between(TODAY, timestamp.toLocalDateTime()) <= 16);
    }
}
