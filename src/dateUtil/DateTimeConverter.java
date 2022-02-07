package dateUtil;

import javafx.collections.ObservableList;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

import static java.time.temporal.ChronoUnit.MINUTES;

public class DateTimeConverter {
    public static  LocalDateTime today = LocalDateTime.now();

    public static Timestamp getUserLocalDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss zzz");
        Timestamp dateTime = new Timestamp(System.currentTimeMillis());
        return dateTime;
    }

    public static String getTimeZoneID() {
        TimeZone timeZone = TimeZone.getDefault();
        return timeZone.getID();
    }

    public static Timestamp convertUTCToLocal(String timestamp) {
        Timestamp localDate = null;
        try {
            DateFormat localTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            localTimeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = localTimeFormat.parse(timestamp);
            DateFormat currentTFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            currentTFormat.setTimeZone(TimeZone.getTimeZone(getTimeZoneID()));
            localDate = Timestamp.valueOf(currentTFormat.format(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return localDate;
    }

    public static Timestamp convertAptTimeToUTC(LocalDate dateValue, String hrValue, String minuteValue) {
        String str = dateValue.toString() + " " + hrValue + ":" + minuteValue + ":00";
        LocalDateTime dateTime = LocalDateTime.parse(str, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return Timestamp.valueOf(formatter.format(dateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"))));

    }

    public static Timestamp convertLocalTimeToUTC(LocalDateTime localTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return Timestamp.valueOf(formatter.format(localTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"))));
    }

    public static LocalDate getMondayDate() {
        LocalDate monday = today.toLocalDate();
        while (monday.getDayOfWeek() != DayOfWeek.MONDAY) {
            monday = monday.minusDays(1);
        }
        return monday;
    }

    public static LocalDate getSundayDate() {
        LocalDate sunday = today.toLocalDate();
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
        return (MINUTES.between(today, timestamp.toLocalDateTime()) >= 0 && MINUTES.between(today, timestamp.toLocalDateTime()) <= 16);
    }

//    public static ObservableList<String> convertEstHrToLocalHr(ObservableList<String> estHr){
//        DateFormat localTimeFormat = new SimpleDateFormat("HH");
//        localTimeFormat.setTimeZone(TimeZone.getTimeZone("EST"));
//        Date date = localTimeFormat.parse(timestamp);
//        DateFormat currentTFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        currentTFormat.setTimeZone(TimeZone.getTimeZone(getTimeZoneID()));
//        localDate = Timestamp.valueOf(currentTFormat.format(date));
//    }


}
