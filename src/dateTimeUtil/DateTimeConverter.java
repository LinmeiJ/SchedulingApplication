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


    public static String getTimeZoneID() {
        TimeZone timeZone = TimeZone.getDefault();
        return timeZone.getID();
    }

    public static Timestamp convertLocalTimeToUTC(LocalDateTime localDateTime) {
        return Timestamp.valueOf(FORMATTER.format(localDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of(TimeZoneOption.UTC.name()))));
    }

    public static Timestamp convertAptTimeToEST(LocalDate dateValue, String hrValue, String minuteValue) {
        String str = dateValue.toString() + " " + hrValue + ":" + minuteValue + ":00";
        LocalDateTime dateTime = LocalDateTime.parse(str, FORMATTER);
        return Timestamp.valueOf(FORMATTER.format(dateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of(String.valueOf(TimeZoneOption.EST), ZoneId.SHORT_IDS))));
    }

    public static Timestamp convertAptTimeToUTC(LocalDate dateValue, String hrValue, String minuteValue) {
        String str = dateValue.toString() + " " + hrValue + ":" + minuteValue + ":00";

        LocalDateTime dateTime = LocalDateTime.parse(str, FORMATTER);
        return Timestamp.valueOf(FORMATTER.format(dateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of(TimeZoneOption.UTC.name()))));
    }

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

    public static Timestamp convertESTToLocal(String timestamp) {
        Timestamp localDate = null;
        try {
            DateFormat currentTFormat = new SimpleDateFormat(FORMAT);
            currentTFormat.setTimeZone(TimeZone.getTimeZone(ZoneId.of(String.valueOf(TimeZoneOption.EST), ZoneId.SHORT_IDS)));
            Date date = currentTFormat.parse(timestamp);
            currentTFormat.setTimeZone(TimeZone.getTimeZone(getTimeZoneID()));
            localDate = Timestamp.valueOf(currentTFormat.format(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return localDate;
    }

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

    public static LocalDate getMondayDate() {
        LocalDate monday = TODAY.toLocalDate();
        while (monday.getDayOfWeek() != DayOfWeek.MONDAY) {
            monday = monday.minusDays(1);
        }
        return monday;
    }

    public static LocalDate getSundayDate() {
        LocalDate sunday = TODAY.toLocalDate();
        while (sunday.getDayOfWeek() != DayOfWeek.SUNDAY) {
            sunday = sunday.plusDays(1);
        }
        return sunday;
    }

    public static String getHr(int hr){
        String hour = String.valueOf(hr);
        return hour.length() == 1 ? "0" + hour : hour;
    }

    public static String getMint(int m){
        String minute = String.valueOf(m);
        return minute.length() == 1 ? "0" + minute : minute;
    }

    // give 16 minutes instead of 15 to avoid a difference in just few seconds
    public static boolean isWithin15mins(Timestamp timestamp){
        return (MINUTES.between(TODAY, timestamp.toLocalDateTime()) >= 0 && MINUTES.between(TODAY, timestamp.toLocalDateTime()) <= 16);
    }
}
