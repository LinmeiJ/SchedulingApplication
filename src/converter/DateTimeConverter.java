package converter;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
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


    public static Timestamp formatTime(LocalDate dateValue, String hrValue, String minuteValue) {
        String str = dateValue.toString() + " " + hrValue + ":" + minuteValue + ":00";
        Timestamp timestamp = Timestamp.valueOf(str);
        return timestamp ;
    }



}
