package converter;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeConverter {
    public static Timestamp convertedTimeTOUTC(){

        return null;
    }

    public static Timestamp getUserLocalDateTime(){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss zzz");
        Timestamp dateTime = new Timestamp(System.currentTimeMillis());
        System.out.println(formatter.format(dateTime));
        return dateTime;
    }

    public static String getTimeZoneID(){
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
        return Timestamp.valueOf(formatter.format(dateTime.atZone(ZoneId.of("America/New_York")).withZoneSameInstant(ZoneId.of("UTC"))));

    }

    public static Timestamp convertLocalTimeToUTC(LocalDateTime localTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return Timestamp.valueOf(formatter.format(localTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"))));
    }

}
