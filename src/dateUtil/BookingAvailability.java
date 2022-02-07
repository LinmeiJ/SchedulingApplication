package dateUtil;

import entity.Appointment;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.time.temporal.ChronoUnit.MINUTES;

public class BookingAvailability {
   public static Map<String, LocalTime> treemap = new TreeMap<>();

    public static void initTimeSlots(){
        int hr = 8, minute = 00, j = 0;
        String keyStr = "";

        for(int key = 8; key < 23;){
            if(minute == 60){
                minute = 00;
                hr++;
                key++;
                j = 0;
            }
            keyStr = String.valueOf(key) + j++;
            treemap.put(keyStr, LocalTime.of(hr, minute));
            if(hr == 22){
                break;
            }
            minute = minute + 15;
        }
        treemap.forEach((key, value) -> System.out.println(key + " " + value));
    }

//    public static boolean isDoubleBooking(List<Appointment> bookedApts, Appointment newApt){
    public static void isDoubleBooking(List<Appointment> scheduleList, LocalDate start, LocalDate end) {
        initTimeSlots();
        Map<String, LocalTime> availableTimeSlots = new TreeMap<>();
        Appointment apt = new Appointment();
        apt.setStart(Timestamp.valueOf(LocalDateTime.of(2022, 02, 07, 01, 30)));
        apt.setEnd(Timestamp.valueOf(LocalDateTime.of(2022, 02, 07, 02, 30)));
        LocalTime startTime = apt.getStart().toLocalDateTime().toLocalTime();
        LocalTime endTime = apt.getEnd().toLocalDateTime().toLocalTime();
        long aptDuration = MINUTES.between(apt.getStart().toLocalDateTime(), apt.getEnd().toLocalDateTime());
        long timeSlotsTaken = aptDuration / 15;
        System.out.println(aptDuration);
        // 1. find if the startTime in the map
        // 2. if found, increment the index by timeSlotsTaken
        // 3. save those timeslots to the new map

        // 4. continue loop and mark the timeslots until end

        // 5. loop through the new map
        // 6. list the keys that the value is null
        // 7. print the timeslot based on the key value
        // 8. convert to local time, then display on the screen.

//        for (String treeKey : treemap.keySet()){
//            if(){
//
//            }
//        }

    //        }
    }

    public static void findAvailability(){

    }



}
