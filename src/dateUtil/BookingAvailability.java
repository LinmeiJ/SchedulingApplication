package dateUtil;

import entity.Appointment;

import java.sql.Timestamp;
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
public static void isDoubleBooking(){

//        List<Player> playersOfTeam = players.stream()
//                .filter(player -> player.getTeam().equals(teamName))
//                .collect(Collectors.toList());

//        for(Appointment apt : bookedApts){
    Appointment apt = new Appointment();
            apt.setStart(Timestamp.valueOf(LocalDateTime.of(2022, 02, 07, 01, 30)));
            apt.setEnd(Timestamp.valueOf(LocalDateTime.of(2022, 02, 07, 02, 30)));

            long aptDuration = MINUTES.between(apt.getStart().toLocalDateTime(), apt.getEnd().toLocalDateTime());
            System.out.println(aptDuration);
//        }
    }

    public static void findAvailability(){

    }



}
