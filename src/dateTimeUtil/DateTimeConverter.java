package dateTimeUtil;

import enums.TimeZoneOption;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * This class converts different timezones or data time related logics.
 *
 * @author Linmei M.
 */
public class DateTimeConverter {
    /**
     * today's date time
     */
    private static final LocalDateTime TODAY = LocalDateTime.now();
    /**
     * a format sample
     */
    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * format a date time
     */
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(FORMAT);
    /**
     * initialize the office open time
     */
    private static final LocalTime officeOpenTime = LocalTime.of(8, 00);
    /**
     * initialize the office end time
     */
    private static final LocalTime officeCloseTime = LocalTime.of(22, 00);
    /**
     * initialize a hour list from 1 to 12
     */
    public static ObservableList<String> hrList = FXCollections.observableList(Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"));
    /**
     * initialize a minutes list from 00 to 45
     */
    public static ObservableList<String> minuteList = FXCollections.observableList(Arrays.asList("00", "15", "30", "45"));
    /**
     * initialize a meridiem list which is AM and PM
     */
    public static ObservableList<String> meridiemList = FXCollections.observableList(Arrays.asList("AM", "PM"));


    /**
     * This method gets a timezone based on user local setting
     *
     * @return a timezone id
     */
    public static String getTimeZoneID() {
        TimeZone timeZone = TimeZone.getDefault();
        return timeZone.getID();
    }

    /**
     * This method converts local time to UTC time
     *
     * @param localDateTime a time based on user local setting
     * @return a UTC time
     */
    public static Timestamp convertLocalTimeToUTC(LocalDateTime localDateTime) {
        return Timestamp.valueOf(FORMATTER.format(localDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of(TimeZoneOption.UTC.name()))));
    }

    /**
     * This method converts user wished appointment time to EST time
     *
     * @param dateValue   the date entered by the user
     * @param hrValue     the hour entered by the user
     * @param minuteValue the minute entered by the user
     * @return an EST time
     */
    public static Timestamp convertAptTimeToEST(LocalDate dateValue, String hrValue, String minuteValue) {
        if (hrValue.length() < 2) {
            hrValue = "0" + hrValue;
        }
        String str = dateValue.toString() + " " + hrValue + ":" + minuteValue + ":00";
        LocalDateTime dateTime = LocalDateTime.parse(str, FORMATTER);
        return Timestamp.valueOf(FORMATTER.format(dateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of(String.valueOf(TimeZoneOption.EST), ZoneId.SHORT_IDS))));
    }

    // I am keeping those here for my future enhancement for this program
//    /**
//     * This method converts a user entered appointment time to UTC time
//     *
//     * @param dateValue   the date entered by the user
//     * @param hrValue     the hour entered by the user
//     * @param minuteValue the minute entered by the user
//     * @return an UTC time
//     */
//    public static Timestamp convertAptTimeToUTC(LocalDate dateValue, String hrValue, String minuteValue) {
//        if (hrValue.length() == 1) {
//            hrValue = "0" + hrValue;
//        }
//        String str = dateValue.toString() + " " + hrValue + ":" + minuteValue + ":00";
//        LocalDateTime dateTime = LocalDateTime.parse(str, FORMATTER);
//        return Timestamp.valueOf(FORMATTER.format(dateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of(TimeZoneOption.UTC.name()))));
//    }

    /**
     * This method converts a UTC time to user local time
     *
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

    /**
     * This method converts a EST data time to user local date time
     *
     * @param timestamp an EST date time
     * @return a local date time
     */
    public static Timestamp convertESTToLocal(String timestamp) {
        if (timestamp.contains("T")) {
            timestamp = timestamp.replace("T", " ") + ":00";
        }
        Timestamp localDateTime = null;
        try {
            DateFormat currentTFormat = new SimpleDateFormat(FORMAT);
            currentTFormat.setTimeZone(TimeZone.getTimeZone(TimeZoneOption.EST.name()));
            Date date = currentTFormat.parse(timestamp);
            currentTFormat.setTimeZone(TimeZone.getTimeZone(getTimeZoneID()));
            localDateTime = Timestamp.valueOf(currentTFormat.format(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return localDateTime;
    }

    // I am keeping those here for my future enhancement for this program
//    /**
//     * This method converts a UTC time to an EST time
//     *
//     * @param timestamp a UTC time
//     * @return an EST time
//     */
//    public static Timestamp convertUTCToEST(Timestamp timestamp) {
//        Timestamp localDate = null;
//        try {
//            DateFormat timeFormat = new SimpleDateFormat(FORMAT);
//            timeFormat.setTimeZone(TimeZone.getTimeZone(TimeZoneOption.UTC.name()));
//            Date date = timeFormat.parse(String.valueOf(timestamp));
//            timeFormat.setTimeZone(TimeZone.getTimeZone(ZoneId.of(String.valueOf(TimeZoneOption.EST), ZoneId.SHORT_IDS)));
//            localDate = Timestamp.valueOf(timeFormat.format(date));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return localDate;
//    }

    /**
     * This method finds the date of last day, which is Saturday, of the current week
     *
     * @return the date of Saturday of the current week
     */
    public static LocalDate getSaturdayDate() {
        LocalDate today = TODAY.toLocalDate();
        while (today.getDayOfWeek() != DayOfWeek.SATURDAY) {
            today = today.plusDays(1);
        }
        return today;

    }

    /**
     * This method finds the date of first day, which is Sunday, of the current week
     *
     * @return the date of sunday of the current week
     */
    public static LocalDate getSundayDate() {
        LocalDate today = TODAY.toLocalDate();
        while (today.getDayOfWeek() != DayOfWeek.SUNDAY) {
            today = today.minusDays(1);
        }
        return today;
    }

    /**
     * This method convert hour to a string
     *
     * @param hr an int type hour
     * @return a string type hour
     */
    public static String getHr(int hr) {
        String hour = String.valueOf(hr);
        return hour.length() == 1 ? "0" + hour : hour;
    }

    /**
     * This method converts a minute to a string
     *
     * @param m an int type minute
     * @return an string type minute
     */
    public static String getMint(int m) {
        String minute = String.valueOf(m);
        return minute.length() == 1 ? "0" + minute : minute;
    }

    /**
     * This method find times that is 15 minutes far away from current local time to a booked appointment time.
     * Give 16 minutes check point instead of 15 minutes to avoid a difference in just few seconds between 15 and 16 minutes
     *
     * @param timestamp a booked time
     * @return true if it is within 15 minutes, otherwise returns false.
     */
    public static boolean isWithin15mins(Timestamp timestamp) {
        return (MINUTES.between(TODAY, timestamp.toLocalDateTime()) >= 0 && MINUTES.between(TODAY, timestamp.toLocalDateTime()) <= 16);
    }

    /**
     * This method checks whether user wished booking time is within office hour( 8am -22pm, Monday - Friday)
     * @param dateValue a appointment start hour
     * @param hrValue an appointment start hour
     * @param minuteValue an appointment start minute
     * @param endDate an appointment end date
     * @param endH an appointment end hour
     * @param endM an appointment end minute
     * @return
     */
    public static boolean isWithinOfficeHour(LocalDate dateValue, String hrValue, String minuteValue, LocalDate endDate, String endH, String endM) {
        Timestamp aptStartDateTime = convertAptTimeToEST(dateValue, hrValue, minuteValue);
        Timestamp endDateTime = convertAptTimeToEST(endDate, endH, endM);
        LocalDateTime officeOpenDateTime = LocalDateTime.of(dateValue, officeOpenTime);
        LocalDateTime officeCloseDateTime = LocalDateTime.of(dateValue, officeCloseTime);
        return checkOfficeDateTime(aptStartDateTime.toLocalDateTime(), officeOpenDateTime, officeCloseDateTime, endDateTime.toLocalDateTime());
    }

    /**
     * This method checks whether the appointment is during a weekend day.
     * @param aptStartDateTime an appointment start time
     * @param endDateTime an appointment end time
     * @return true if it is in the weekend, otherwise false.
     */
    private static boolean isWeekend(LocalDateTime aptStartDateTime, LocalDateTime endDateTime) {
        return ((aptStartDateTime.toLocalDate().getDayOfWeek() == DayOfWeek.SUNDAY || aptStartDateTime.toLocalDate().getDayOfWeek() == DayOfWeek.SATURDAY)
                &&
                (endDateTime.toLocalDate().getDayOfWeek() == DayOfWeek.SUNDAY || endDateTime.toLocalDate().getDayOfWeek() == DayOfWeek.SATURDAY));
    }

    /**
     * This method checks whether user appointment is within EST office hour.
     *
     * @param aptDateTime         the appointment start time
     * @param officeOpenDateTime  the time when office opens
     * @param officeCloseDateTime the time when office closes
     * @param endDateTime the appointment end time
     * @return true if the appointment is within EST office hour, false when out of office hour.
     */
    private static boolean checkOfficeDateTime(LocalDateTime aptDateTime, LocalDateTime officeOpenDateTime, LocalDateTime officeCloseDateTime, LocalDateTime endDateTime) {
        return !aptDateTime.isBefore(officeOpenDateTime) && !aptDateTime.isAfter(officeCloseDateTime)
                && !endDateTime.isBefore(officeOpenDateTime) && !endDateTime.isAfter(officeCloseDateTime)
                && !isWeekend(aptDateTime, endDateTime);
    }

    // I am keeping those here for my future enhancement for this program
//    /**
//     * This method generates a local time for user to identify the EST starting office hour based on the local date
//     *
//     * @param dateValue the date user wish to book
//     * @return a date time of the EST office start hour
//     */
//    public static String getOfficeStartHr(LocalDate dateValue) {
//        LocalDateTime estOfficeHrOfTheDay = LocalDateTime.of(dateValue, LocalTime.of(8, 0));
//        return String.valueOf(convertESTToLocal(String.valueOf(estOfficeHrOfTheDay)).toLocalDateTime().toLocalTime());
//    }

//    /**
//     * This method generates a local time for user to identify the EST end office hour based on the local date
//     *
//     * @param dateValue the date user wish to book
//     * @return a date time of the EST office end hour
//     */
//    public static String getOfficeEndHr(LocalDate dateValue) {
//        LocalDateTime estOfficeHrOfTheDay = LocalDateTime.of(dateValue, LocalTime.of(22, 0));
//        return String.valueOf(convertESTToLocal(String.valueOf(estOfficeHrOfTheDay)).toLocalDateTime().toLocalTime());
//    }

    /**
     * convert 12 hour time to 24 hour time
     *
     * @param hr       the hour
     * @param meridiem the meridiem - am/pm
     * @return converted hr time
     */
    public static int get24HrTime(int hr, String meridiem) {
        hr += meridiem.equals("AM") ? 0 : 12;
        return hr;
    }

    /**
     * This method convers times between AM and PM
     * @param hr an hour time
     * @return a meridiem
     */
    public static String getMeridiem(int hr) {
        return hr > 12 ? "PM" : "AM";
    }

    /**
     * This method converts 24 hour time to 12 hour time digit
     * @param hr an hour time
     * @return a formatted hour time
     */
    public static int convertHrTo12HrTime(int hr) {
        if (hr > 12) {
            hr -= 12;
        }
        return hr;
    }

    /**
     * This method converts EST office start hour to local start office hour
     * @return a local starting office hour
     */
    public static LocalTime convertESTOfficeStartHrToLocal() {
        Timestamp startTime = convertESTToLocal(String.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 0))));
        return startTime.toLocalDateTime().toLocalTime();
    }

    /**
     * The method converts EST office end hour to local end office hour
     * @return a local ending office hour
     */
    public static LocalTime convertESTOfficeEndHrToLocal() {
        Timestamp endTime = convertESTToLocal(String.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(22, 0))));
        return endTime.toLocalDateTime().toLocalTime();
    }
}
